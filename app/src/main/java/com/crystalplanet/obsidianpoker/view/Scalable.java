package com.crystalplanet.obsidianpoker.view;

import com.crystalplanet.obsidianpoker.view.util.Scale;

public interface Scalable {

    void setScale(Scale scale);

    int scale(float n);

    int getOffsetLeft();

    int getOffsetTop();

    int scaledWidth();

    int scaledHeight();

    void layout(int l, int t, int r, int b);
}
