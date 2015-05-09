package com.crystalplanet.obsidianpoker.view;

import android.content.Context;
import android.util.AttributeSet;
import com.crystalplanet.obsidianpoker.util.Scale;

public class GenericScaledLayout extends ScaledLayout {

    public GenericScaledLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        setScale(new Scale(w, h, scaledWidth(), scaledHeight()));
    }
}
