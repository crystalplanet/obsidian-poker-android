package com.crystalplanet.obsidianpoker.app.model.stages;

import com.crystalplanet.obsidianpoker.app.model.RoundStage;
import com.crystalplanet.obsidianpoker.app.model.PokerRound;

public class FlopStage extends RoundStage {

    public FlopStage(PokerRound round) {
        super(round);
    }

    @Override
    public RoundStage next() {
        return playersLeft(round.players().iterator()) < 2 ? null : new TurnStage(round);
    }
}
