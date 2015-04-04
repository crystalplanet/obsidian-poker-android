package com.crystalplanet.obsidianpoker.app.model.stages;

import com.crystalplanet.obsidianpoker.app.model.GameStage;
import com.crystalplanet.obsidianpoker.app.model.PokerGame;

public class FlopStage extends GameStage {

    public FlopStage(PokerGame game) {
        super(game);
    }

    @Override
    public GameStage next() {
        return playersLeft(game.players().iterator()) < 2 ? null : new TurnStage(game);
    }
}
