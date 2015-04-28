package com.crystalplanet.obsidianpoker.view;

import android.graphics.Canvas;
import com.crystalplanet.obsidianpoker.util.Pair;
import com.crystalplanet.obsidianpoker.view.util.Offset;
import com.crystalplanet.obsidianpoker.view.util.Scale;

import java.util.*;

abstract public class Layout {

    private List<Layout> children = new ArrayList<Layout>();

    private Map<String, String> attr;

    private Layout parent;

    public Layout(Map<String, String> attr, Layout parent) {

        this.attr = attr;
        this.parent = parent;
    }

    public String id() {
        return attr == null ? null : attr.get("id");
    }

    public Layout parent() {
        return parent;
    }

    public void addChild(Layout layout) {
        children.add(layout);
    }

    public List<Layout> children() {
        return children;
    }

    public List<Layout> childrenOfType(String type) {
        ArrayList<Layout> childrenOfType = new ArrayList<Layout>();

        for (Layout layout : children())
            if (type.equals(layout.getClass().getSimpleName())) childrenOfType.add(layout);

        return childrenOfType;
    }

    public Layout childById(String id) {
        for (Layout layout : children())
            if (id.equals(layout.id())) return layout;
        return null;
    }

    public Pair<Float, Float> position() {
        return attr == null ? null : new Pair<Float, Float>(
            attr.get("left") == null ? new Float(0) : Float.parseFloat(attr.get("left")),
            attr.get("top") == null ? new Float(0) : Float.parseFloat(attr.get("top"))
        );
    }

    public boolean isRelative() {
        return attr != null && attr.get("relative") != null && Boolean.parseBoolean(attr.get("relative"));
    }

    abstract public void draw(Canvas canvas, Offset offset, Scale scale);
}
