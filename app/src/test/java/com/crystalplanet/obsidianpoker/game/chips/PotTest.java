package com.crystalplanet.obsidianpoker.game.chips;

import com.crystalplanet.obsidianpoker.game.Player;
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

    public void testCurrentBet() {
        Pot pot = new Pot();

        pot.takeBet(players[0], new Chips(20));
        pot.takeBet(players[1], new Chips(30));
        pot.takeBet(players[2], new Chips(50));

        Assert.assertTrue(pot.currentBet().equals(new Chips(50)));
    }

    public void testPlayersBet() {
        Pot pot = new Pot();

        Assert.assertEquals(new Chips(0), pot.playersBet(players[0]));

        pot.takeBet(players[0], new Chips(20));
        pot.takeBet(players[1], new Chips(30));
        pot.takeBet(players[2], new Chips(40));

        Assert.assertEquals(new Chips(20), pot.playersBet(players[0]));
        Assert.assertEquals(new Chips(30), pot.playersBet(players[1]));
        Assert.assertEquals(new Chips(40), pot.playersBet(players[2]));

        pot.takeBet(players[0], new Chips(20));

        Assert.assertEquals(new Chips(40), pot.playersBet(players[0]));

        pot.takeBet(players[1], new Chips(40));

        Assert.assertEquals(new Chips(70), pot.playersBet(players[1]));
        Assert.assertEquals(new Chips(70), pot.currentBet());
        Assert.assertEquals(new Chips(150), pot.size());
    }

    public void testIsEmpty() {
        Pot pot = new Pot();

        Assert.assertTrue(pot.isEmpty());

        pot.takeBet(players[0], new Chips(0));

        Assert.assertTrue(pot.isEmpty());

        pot.takeBet(players[0], new Chips(10));

        Assert.assertFalse(pot.isEmpty());
    }

    public void testPayOut() {
        Pot pot = new Pot();

        pot.takeBet(players[0], new Chips(150));
        pot.takeBet(players[1], new Chips(50));
        pot.takeBet(players[2], new Chips(100));

        Assert.assertEquals(new Chips(300), pot.size());

        Assert.assertEquals(new Chips(150), pot.payOut(players[1]));
        Assert.assertEquals(new Chips(150), pot.size());

        Assert.assertEquals(new Chips(0), pot.payOut(players[1]));
        Assert.assertEquals(new Chips(150), pot.size());

        Assert.assertEquals(new Chips(0), pot.payOut(players[3]));
        Assert.assertEquals(new Chips(150), pot.size());

        Assert.assertEquals(new Chips(150), pot.payOut(players[0]));
        Assert.assertEquals(new Chips(0), pot.size());

        Assert.assertEquals(new Chips(0), pot.payOut(players[2]));
    }

    private Player[] players = new Player[] {
        new Player(null, null, null),
        new Player(null, null, null),
        new Player(null, null, null),
        new Player(null, null, null)
    };
}
