package com.crystalplanet.obsidianpoker.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Layout extends Drawable {

    private List<Drawable> children = new ArrayList<Drawable>();

    public Layout(Layout parent, Map<String, String> attr) {
        super(parent, attr);
    }

    public void addChild(Drawable drawable) {
        children.add(drawable);
    }

    public List<Drawable> children() {
        return children;
    }
}
