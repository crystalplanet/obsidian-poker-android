package com.crystalplanet.obsidianpoker.game.stage;

import com.crystalplanet.obsidianpoker.game.PokerRound;

public class FlopStage extends RoundStage {

    public FlopStage(PokerRound round) {
        super(round);
    }

    @Override
    public RoundStage next() {
        return playersLeft(round.players().iterator()) < 2 ? null : new TurnStage(round);
    }
}
