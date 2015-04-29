package com.crystalplanet.obsidianpoker.view.util;

public class Offset {

    private int offsetTop;
    private int offsetLeft;

    public Offset(int offsetLeft, int offsetTop) {
        this.offsetLeft = offsetLeft;
        this.offsetTop = offsetTop;
    }

    public Offset(int width, int height, int defaultWidth, int defaultHeight) {
        Scale scale = new Scale(width, height, defaultWidth, defaultHeight);

        offsetTop = Math.round(scale.scale(((float) height - scale.scale(defaultHeight)) / 2));
        offsetLeft = Math.round(scale.scale(((float) width - scale.scale(defaultWidth)) / 2));
    }

    public int offsetTop(int number) {
        return number + offsetTop;
    }

    public int offsetLeft(int number) {
        return number + offsetLeft;
    }
}
