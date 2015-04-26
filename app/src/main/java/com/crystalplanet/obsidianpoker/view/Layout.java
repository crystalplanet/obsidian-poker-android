package com.crystalplanet.obsidianpoker.view;

import java.util.List;

public interface Layout extends Drawable {

    void addChild(Drawable drawable);

    List<Drawable> children();
}
