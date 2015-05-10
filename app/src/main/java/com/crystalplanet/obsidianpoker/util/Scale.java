package com.crystalplanet.obsidianpoker.util;

public class Scale {

    private double multiplier;

    public Scale(double multiplier) {
        this.multiplier = multiplier;
    }

    public Scale(int width, int height, int defaultWidth, int defaultHeight) {
        multiplier = Math.min(
            (double)width / (double)defaultWidth,
            (double)height / (double)defaultHeight
        );
    }

    public int scale(double number) {
        return (int) Math.round(number * multiplier);
    }

    public Scale invert() {
        return new Scale(1/multiplier);
    }
}
