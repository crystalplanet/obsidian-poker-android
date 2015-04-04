package com.crystalplanet.obsidianpoker.app.model.stages;

import com.crystalplanet.obsidianpoker.app.model.GameStage;
import com.crystalplanet.obsidianpoker.app.model.PokerGame;

public class RiverStage extends GameStage {

    public RiverStage(PokerGame game) {
        super(game);
    }

    @Override
    public GameStage next() {
        return null;
    }
}
