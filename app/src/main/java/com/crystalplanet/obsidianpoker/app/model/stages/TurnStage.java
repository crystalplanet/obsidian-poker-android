package com.crystalplanet.obsidianpoker.app.model.stages;

import com.crystalplanet.obsidianpoker.app.model.GameStage;
import com.crystalplanet.obsidianpoker.app.model.PokerGame;

public class TurnStage extends GameStage {

    public TurnStage(PokerGame game) {
        super(game);
    }

    @Override
    public GameStage next() {
        return playersLeft(game.players().iterator()) < 2 ? null : new RiverStage(game);
    }
}
