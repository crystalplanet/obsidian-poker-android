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

    public List<Drawable> children(String type) {
        ArrayList<Drawable> childrenOfType = new ArrayList<Drawable>();
        for (Drawable child : children)
            if (type.equals(child.getClass().getSimpleName())) childrenOfType.add(child);
        return childrenOfType;
    }

    public Drawable child(String id) {
        for (Drawable child : children())
            if (id.equals(child.id())) return child;
        return null;
    }
}
