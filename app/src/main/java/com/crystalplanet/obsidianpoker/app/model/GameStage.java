package com.crystalplanet.obsidianpoker.app.model;

import java.util.Iterator;

public abstract class GameStage {

    protected PokerGame game;

    public GameStage(PokerGame game) {
        this.game = game;
    }

    public abstract GameStage next();

    public boolean isOver() {
        if (playersLeft(game.players().iterator()) < 2) return true;

        for (Player player : game.players())
            if (hasToPlay(player)) return false;

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other != null && getClass().equals(other.getClass());
    }

    protected boolean hasToPlay(Player player) {
        return !player.isFolded() &&
            ((game.playersBet(player).compareTo(game.currentBet()) < 0 || !player.hasChecked()) &&
            !player.isAllIn());
    };

    protected int playersLeft(Iterator<Player> it) {
        return it.hasNext() ? (isLeft(it.next()) ? 1 : 0) + playersLeft(it) : 0;
    }

    protected boolean isLeft(Player player) {
        return !(player.isAllIn() || player.isFolded());
    }
}
