package com.crystalplanet.obsidianpoker.game.stage;

import com.crystalplanet.obsidianpoker.game.PokerRound;

public class RiverStage extends RoundStage {

    public RiverStage(PokerRound round) {
        super(round);
    }

    @Override
    public RoundStage next() {
        return null;
    }
}
