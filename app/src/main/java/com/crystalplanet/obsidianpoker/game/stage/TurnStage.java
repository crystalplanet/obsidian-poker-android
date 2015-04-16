package com.crystalplanet.obsidianpoker.game.stage;

import com.crystalplanet.obsidianpoker.game.PokerRound;

public class TurnStage extends RoundStage {

    public TurnStage(PokerRound round) {
        super(round);
    }

    @Override
    public RoundStage next() {
        return playersLeft(round.players().iterator()) < 2 ? null : new RiverStage(round);
    }
}
