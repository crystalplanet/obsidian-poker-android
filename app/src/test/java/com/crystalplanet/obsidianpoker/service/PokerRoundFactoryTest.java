package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.GameObserver;
import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.game.PokerRound;
import com.crystalplanet.obsidianpoker.game.card.Deck;
import com.crystalplanet.obsidianpoker.game.chips.Chips;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class PokerRoundFactoryTest {

    @Test
    public void testNewRound() {
        ArrayList<GameObserver> observers = new ArrayList<GameObserver>();
        ArrayList<Player> players = new ArrayList<Player>();

        PokerRoundFactory roundFactory = new PokerRoundFactory();

        PokerRound round = roundFactory.newRound(observers, players, new Deck(), new Chips(100));

        Assert.assertEquals(players, round.players());
        Assert.assertEquals(new Chips(100), round.smallBlind());
        // Assert observers & players
    }
}
