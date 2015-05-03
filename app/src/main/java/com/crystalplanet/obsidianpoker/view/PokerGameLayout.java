package com.crystalplanet.obsidianpoker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.crystalplanet.obsidianpoker.app.R;
import com.crystalplanet.obsidianpoker.view.util.Offset;
import com.crystalplanet.obsidianpoker.view.util.Scale;

public class PokerGameLayout extends ScaledLayout {

    private Offset offset;

    private int innerWidth;

    private int innerHeight;

    public PokerGameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PokerGameLayout, 0, 0);

        try {
            innerWidth = a.getInt(R.styleable.PokerGameLayout_inner_width, 1920);
            innerHeight = a.getInt(R.styleable.PokerGameLayout_inner_height, 1080);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        offset = new Offset(w, h, innerWidth, innerHeight);
        setScale(new Scale(w, h, innerWidth, innerHeight));
    }

    @Override
    protected void setChildPosition(Scalable child, int l, int t, int r, int b) {
        child.layout(
                scale(child.getOffsetLeft()) + offset.offsetLeft(0),
                scale(child.getOffsetTop()) + offset.offsetTop(0),
                scale(child.getOffsetLeft()) + offset.offsetLeft(0) + scale(child.scaledWidth()),
                scale(child.getOffsetTop()) + offset.offsetTop(0) + scale(child.scaledHeight())
        );
    }
}
