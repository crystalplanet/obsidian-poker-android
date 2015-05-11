package com.crystalplanet.obsidianpoker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
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

    private boolean invertTop;

    private int width;

    private int widthMeasure;

    private int height;

    private int heightMeasure;

    private int position;

    public ScaledLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ScaledLayout, 0, 0);

        try {
            offset = new Offset(
                a.getInt(R.styleable.ScaledLayout_offset_layout_left, 0),
                a.getInt(R.styleable.ScaledLayout_offset_layout_top, 0)
            );
            invertTop = a.getBoolean(R.styleable.ScaledLayout_offset_layout_top_invert, false);

            width = a.getInt(R.styleable.ScaledLayout_layout_width, 0);
            widthMeasure = a.getInt(R.styleable.ScaledLayout_layout_width_measure, Measure.FIXED);
            height = a.getInt(R.styleable.ScaledLayout_layout_height, 0);
            heightMeasure = a.getInt(R.styleable.ScaledLayout_layout_height_measure, Measure.FIXED);

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
        return widthMeasure == Measure.RELATIVE
            ? (int)((float) scale.invert().scale(((View) getParent()).getWidth()) * width / 100)
            : width;
    }

    @Override
    public int scaledHeight() {
        return heightMeasure == Measure.RELATIVE
            ? (int)((float) scale.invert().scale(((View) getParent()).getHeight()) * height / 100)
            : height;
    }

    public boolean isRelative() {
        return position == RELATIVE;
    }

    public boolean isAbsolute() {
        return position == ABSOLUTE;
    }

    public boolean invertTop() {
        return invertTop;
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
            getChildTopOffset(child, b) - (isRelative() ? 0 : t),
            scale(child.getOffsetLeft() + child.scaledWidth()) - (isRelative() ? 0 : l),
            getChildTopOffset(child, b) + scale(child.scaledHeight()) - (isRelative() ? 0 : t)
        );
    }

    private int getChildTopOffset(Scalable child, int b) {
        if (!(child instanceof ScaledLayout)) return scale(child.getOffsetTop());

        ScaledLayout layout = (ScaledLayout) child;

        return layout.invertTop
            ? b - scale(child.getOffsetTop() + child.scaledHeight())
            : scale(child.getOffsetTop());
    }
}
