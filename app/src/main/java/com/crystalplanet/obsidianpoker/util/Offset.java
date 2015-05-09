package com.crystalplanet.obsidianpoker.util;

public class Offset {

    private int offsetTop;
    private int offsetLeft;

    public static Offset createCenteredOffset(int parentWidth, int parentHeight, int childWidth, int childHeight) {
        Scale scale = new Scale(parentWidth, parentHeight, childWidth, childHeight).inverse();

        return new Offset(
            Math.round((scale.scale(parentWidth) - childWidth)/2),
            Math.round((scale.scale(parentHeight) - childHeight)/2)
        );
    }

    public Offset(int offsetLeft, int offsetTop) {
        this.offsetLeft = offsetLeft;
        this.offsetTop = offsetTop;
    }

    public int offsetTop(int number) {
        return number + offsetTop;
    }

    public int offsetLeft(int number) {
        return number + offsetLeft;
    }
}
