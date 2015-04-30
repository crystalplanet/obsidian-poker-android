package com.crystalplanet.obsidianpoker.view;

import android.content.Context;

import java.util.Map;

public class LayoutFactory {

    private Context context;

    public LayoutFactory(Context context) {
        this.context = context;
    }

    public Layout newLayout(String name, Map<String, String> attr, Layout parent) throws Exception {
        Layout layout = (Layout) Class.forName(name)
            .getConstructor(Context.class, Map.class, Layout.class)
            .newInstance(context, attr, parent);

        parent.addChild(layout);

        return layout;
    }
}
