package com.crystalplanet.obsidianpoker.app.model.stages;

import com.crystalplanet.obsidianpoker.app.model.RoundStage;
import com.crystalplanet.obsidianpoker.app.model.PokerRound;

public class RiverStage extends RoundStage {

    public RiverStage(PokerRound round) {
        super(round);
    }

    @Override
    public RoundStage next() {
        return null;
    }
}
