package com.crystalplanet.obsidianpoker.app.model.stages;

import com.crystalplanet.obsidianpoker.app.model.GameStage;
import com.crystalplanet.obsidianpoker.app.model.Player;
import com.crystalplanet.obsidianpoker.app.model.PokerGame;

public class DealStage extends GameStage {

    private int bigBlind = 0;

    public DealStage(PokerGame game) {
        super(game);
    }

    @Override
    public GameStage next() {
        return playersLeft(game.players().iterator()) < 2 ? null : new FlopStage(game);
    }

    @Override
    protected boolean hasToPlay(Player player) {
        // Account for big-blind's option in the first betting round
        return super.hasToPlay(player) || (
            !player.isFolded() &&
            (isBigBlind(player) &&  game.currentPlayer() == player && ++bigBlind == 2)
        );
    }

    private boolean isBigBlind(Player player) {
        return game.players().indexOf(player) == 1;
    }
}
