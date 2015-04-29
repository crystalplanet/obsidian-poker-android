package com.crystalplanet.obsidianpoker.view;

import android.graphics.Canvas;
import com.crystalplanet.obsidianpoker.util.Pair;
import com.crystalplanet.obsidianpoker.view.util.Offset;
import com.crystalplanet.obsidianpoker.view.util.Scale;

import java.util.*;

public class Layout {

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

    public String getAttr(String attribute) {
        return attr == null || attr.get(attribute) == null ? null : attr.get(attribute);
    }

    public Pair<Float, Float> position() {
        return new Pair<Float, Float>(
            Float.parseFloat(getAttr("left") == null ? "0" : getAttr("left")),
            Float.parseFloat(getAttr("top") == null ? "0" : getAttr("top"))
        );
    }

    public boolean isRelative() {
        return parent == null || (getAttr("relative") != null && Boolean.parseBoolean(getAttr("relative")));
    }

    public void draw(Canvas canvas, Offset offset, Scale scale) {
        for (Layout layout : children())
            layout.draw(canvas, isRelative() ? relativeOffset(offset, scale) : offset, scale);
    }

    private Offset relativeOffset(Offset offset, Scale scale) {
        return new Offset(
            offset.offsetLeft(0) + (int) scale.scale(Float.parseFloat(getAttr("left"))),
            offset.offsetTop(0) + (int) scale.scale(Float.parseFloat(getAttr("top")))
        );
    }
}
