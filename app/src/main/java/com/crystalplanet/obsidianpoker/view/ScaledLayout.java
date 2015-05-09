package com.crystalplanet.obsidianpoker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.crystalplanet.obsidianpoker.app.R;
import com.crystalplanet.obsidianpoker.util.Offset;
import com.crystalplanet.obsidianpoker.util.Scale;

public class ScaledLayout extends ViewGroup implements Scalable {

    public static final int ABSOLUTE = 2;

    public static final int RELATIVE = 1;

    public static final int BLOCK = 0;

    private Scale scale;

    private Offset offset;

    private int width;

    private int height;

    private int position;

    public ScaledLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ScaledLayout, 0, 0);

        try {
            offset = new Offset(
                a.getInt(R.styleable.ScaledLayout_offset_layout_left, 0),
                a.getInt(R.styleable.ScaledLayout_offset_layout_top, 0)
            );
            width = a.getInt(R.styleable.ScaledLayout_layout_width, 0);
            height = a.getInt(R.styleable.ScaledLayout_layout_height, 0);

            position = a.getInt(R.styleable.ScaledLayout_layout_position, BLOCK);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setScale(Scale scale) {
        this.scale = scale;
    }

    @Override
    public int scale(float n) {
        return scale.scale(n);
    }

    @Override
    public int getOffsetLeft() {
        return offset.offsetLeft(0);
    }

    @Override
    public int getOffsetTop() {
        return offset.offsetTop(0);
    }

    @Override
    public int scaledWidth() {
        return width;
    }

    @Override
    public int scaledHeight() {
        return height;
    }

    public boolean isRelative() {
        return position == RELATIVE;
    }

    public boolean isAbsolute() {
        return position == ABSOLUTE;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i=0; i<getChildCount(); ++i) {
            if (getChildAt(i) instanceof Scalable) {
                ((Scalable) getChildAt(i)).setScale(scale);
                setChildPosition((Scalable) getChildAt(i), l, t, r, b);
            }
        }
    }

    protected void setChildPosition(Scalable child, int l, int t, int r, int b) {
        child.layout(
            scale(child.getOffsetLeft()) - (isRelative() ? 0 : l),
            scale(child.getOffsetTop()) - (isRelative() ? 0 : t),
            scale(child.getOffsetLeft() + child.scaledWidth()) - (isRelative() ? 0 : l),
            scale(child.getOffsetTop() + child.scaledHeight()) - (isRelative() ? 0 : t)
        );
    }
}
