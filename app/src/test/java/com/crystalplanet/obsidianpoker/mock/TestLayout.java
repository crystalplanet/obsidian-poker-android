package com.crystalplanet.obsidianpoker.mock;

import android.graphics.Canvas;
import com.crystalplanet.obsidianpoker.view.Layout;
import com.crystalplanet.obsidianpoker.view.util.Offset;
import com.crystalplanet.obsidianpoker.view.util.Scale;

import java.util.Map;

public class TestLayout extends Layout {

    public Map<String, String> attr;

    public TestLayout(Map<String, String> attr, Layout parent) {
        super(attr, parent);

        this.attr = attr;
    }

    @Override
    public void draw(Canvas canvas, Offset offset, Scale scale) {

    }
}
