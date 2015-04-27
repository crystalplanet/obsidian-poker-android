package com.crystalplanet.obsidianpoker.view;

import android.graphics.Canvas;

import java.util.Map;

public abstract class Drawable {

    private Layout parent;

    private String id;

    public Drawable(Layout parent, Map<String, String> attr) {
        this.id = attr.get("id");
        this.parent = parent;
    }

    public String id() {
        return id;
    }

    public Layout parent() {
        return parent;
    }

    public abstract void onDraw(Canvas canvas);
}
