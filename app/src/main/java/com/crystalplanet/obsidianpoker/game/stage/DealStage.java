package com.crystalplanet.obsidianpoker.game.stage;

import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.game.PokerRound;

public class DealStage extends RoundStage {

    private boolean option = true;

    public DealStage(PokerRound round) {
        super(round);
    }

    @Override
    public RoundStage next() {
        return playersLeft(round.players().iterator()) < 2 ? null : new FlopStage(round);
    }

    @Override
    protected boolean hasToPlay(Player player) {
        // Account for big-blind's option in the first betting round
        return super.hasToPlay(player) || (
            !player.isFolded() &&
            (isBigBlind(player) && option && (option = player != round.currentPlayer()))
        );
    }

    private boolean isBigBlind(Player player) {
        return round.players().indexOf(player) == 1;
    }
}
