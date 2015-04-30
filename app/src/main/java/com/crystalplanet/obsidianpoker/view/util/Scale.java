package com.crystalplanet.obsidianpoker.view.util;

public class Scale {

    private float multiplier;

    public Scale(int width, int height, int defaultWidth, int defaultHeight) {
        multiplier = Math.min(
            (float)width / (float)defaultWidth,
            (float)height / (float)defaultHeight
        );
    }

    public int scale(float number) {
        return Math.round(number * multiplier);
    }
}
