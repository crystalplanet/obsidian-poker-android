package com.crystalplanet.obsidianpoker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.crystalplanet.obsidianpoker.app.R;
import com.crystalplanet.obsidianpoker.view.util.Offset;
import com.crystalplanet.obsidianpoker.view.util.Scale;

class ScaledView extends View implements Scalable {

    private Offset offset;

    private Scale scale;

    private int width;

    private int height;

    private float rotation;

    public ScaledView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ScaledView, 0, 0);

        try {
            offset = new Offset(
                a.getInt(R.styleable.ScaledView_offset_left, 0),
                a.getInt(R.styleable.ScaledView_offset_top, 0)
            );

            width = a.getInt(R.styleable.ScaledView_scaled_width, 0);
            height = a.getInt(R.styleable.ScaledView_scaled_height, 0);
            rotation = a.getFloat(R.styleable.ScaledView_rotation, 0);
        } finally {
            a.recycle();
        }
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public void setScale(Scale scale) {
        this.scale = scale;
    }

    @Override
    public int scale(float n) {
        return scale.scale(n);
    };

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
}
