package com.crystalplanet.obsidianpoker.game.stage;

import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.game.PokerRound;

import java.util.Iterator;

public abstract class RoundStage {

    protected PokerRound round;

    public RoundStage(PokerRound round) {
        this.round = round;
    }

    public abstract RoundStage next();

    public boolean isOver() {
        if (playersLeft(round.players().iterator()) < 2) return true;

        for (Player player : round.players())
            if (hasToPlay(player)) return false;

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other != null && getClass().equals(other.getClass());
    }

    protected boolean hasToPlay(Player player) {
        return !player.isFolded() &&
            ((round.playersBet(player).compareTo(round.currentBet()) < 0 || !player.hasChecked()) &&
            !player.isAllIn());
    };

    protected int playersLeft(Iterator<Player> it) {
        return it.hasNext() ? (isLeft(it.next()) ? 1 : 0) + playersLeft(it) : 0;
    }

    protected boolean isLeft(Player player) {
        return !(player.isAllIn() || player.isFolded());
    }
}
