package com.crystalplanet.obsidianpoker.view;

import java.util.Map;

public class LayoutFactory {
    public Layout newLayout(String name, Map<String, String> attr, Layout parent) throws Exception {
        Layout layout = (Layout) Class.forName(name).getConstructor(Map.class, Layout.class).newInstance(attr, parent);

        parent.addChild(layout);

        return layout;
    }
}
