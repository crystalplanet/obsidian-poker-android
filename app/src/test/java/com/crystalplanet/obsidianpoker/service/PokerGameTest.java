package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.PokerRound;
import junit.framework.Assert;
import org.junit.Test;

public class PokerGameTest {

    @Test
    public void testPokerGame() {
        PokerGame game = new PokerGame(
            new PokerRoundFactory(),
            new GameConfiguration(),
            new DeckProvider(),
            new PlayerManager(),
            new GameObserverManager()
        );

        Assert.assertEquals(null, game.currentRound());

        PokerRound gameRound = game.nextRound();

        Assert.assertEquals(gameRound, game.currentRound());
        Assert.assertFalse(gameRound.equals(game.nextRound()));
    }
}
