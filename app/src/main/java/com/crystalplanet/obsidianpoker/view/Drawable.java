package com.crystalplanet.obsidianpoker.view;

import android.graphics.Canvas;

public interface Drawable {
    Layout parent();

    void onDraw(Canvas canvas);
}
