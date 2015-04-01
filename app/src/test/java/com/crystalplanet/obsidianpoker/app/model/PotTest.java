package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class PotTest extends TestCase {

    public void testCombinedPots() {
        Pot pots[] = new Pot[2];

        for (int i=0; i<2; ++i) {
            pots[i] = new Pot();

            pots[i].takeBet(players[0], new Chips((i+1)*10));
            pots[i].takeBet(players[1], new Chips((i+1)*20));
            pots[i].takeBet(players[2], new Chips((i+1)*30));
        }

        Pot combined = new Pot(pots[0], pots[1]);

        assertEquals(pots[0].size().add(pots[1].size()), combined.size());
        assertEquals(new Chips(30), combined.playersBet(players[0]));
        assertEquals(new Chips(60), combined.playersBet(players[1]));
        assertEquals(new Chips(90), combined.playersBet(players[2]));
    }

    public void testPlaceBet() {
        Pot pot = new Pot();

        pot.takeBet(players[0], new Chips(0));
        pot.takeBet(players[1], new Chips(30));
        pot.takeBet(players[2], new Chips(40));
        pot.takeBet(players[3], new Chips(50));

        Assert.assertTrue(pot.size().equals(new Chips(120)));
    }

    public void testMinBet() {
        Pot pot = new Pot();

        pot.takeBet(players[0], new Chips(20));
        pot.takeBet(players[1], new Chips(30));
        pot.takeBet(players[2], new Chips(50));

        Assert.assertTrue(pot.currentBet().equals(new Chips(50)));
    }

    public void testIsEmpty() {
        Pot pot = new Pot();

        Assert.assertTrue(pot.isEmpty());

        pot.takeBet(players[0], new Chips(0));

        Assert.assertTrue(pot.isEmpty());

        pot.takeBet(players[0], new Chips(10));

        Assert.assertFalse(pot.isEmpty());
    }

    private Player[] players = new Player[] {
        new Player(null, null, null),
        new Player(null, null, null),
        new Player(null, null, null),
        new Player(null, null, null)
    };
}
