package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class PotTest extends TestCase {

    public void testPlaceBet() {
        Pot pot = new Pot();

        pot.placeBet(new Chips(0), players[0]);
        pot.placeBet(new Chips(30), players[1]);
        pot.placeBet(new Chips(40), players[2]);
        pot.placeBet(new Chips(50), players[3]);

        Assert.assertTrue(pot.size().equals(new Chips(120)));
    }

    public void testMinBet() {
        Pot pot = new Pot();

        pot.placeBet(new Chips(20), players[0]);
        pot.placeBet(new Chips(30), players[1]);
        pot.placeBet(new Chips(50), players[2]);

        Assert.assertTrue(pot.currentBet().equals(new Chips(50)));
    }

    public void testIsEmpty() {
        Pot pot = new Pot();

        Assert.assertTrue(pot.isEmpty());

        pot.placeBet(new Chips(0), players[0]);

        Assert.assertTrue(pot.isEmpty());

        pot.placeBet(new Chips(10), players[0]);

        Assert.assertFalse(pot.isEmpty());
    }

    private Player[] players = new Player[] {
        new Player(),
        new Player(),
        new Player(),
        new Player()
    };
}
