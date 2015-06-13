package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.game.Turn;
import com.crystalplanet.obsidianpoker.game.card.Card;
import com.crystalplanet.obsidianpoker.game.card.CardRank;
import com.crystalplanet.obsidianpoker.game.card.CardSuit;
import com.crystalplanet.obsidianpoker.game.chips.Chips;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;

public class PlayerManagerTest extends TestCase {

    public void testPlayerManager() {
        ArrayList<Player> players = new ArrayList<Player>() {{
            add(new Player(null, null, null));
            add(new Player(null, null, null));
            add(new Player(null, null, null));
        }};

        PlayerManager pm = new PlayerManager();

        Assert.assertEquals(new ArrayList<Player>(), pm.getPlayers());

        for (Player player : players)
            pm.addPlayer(player, players.indexOf(player));

        Assert.assertEquals(players, pm.getPlayers());

        pm.addPlayer(players.get(1), 1);

        Assert.assertEquals(players, pm.getPlayers());
    }

    public void testGetPlayers() {
        ArrayList<Player> players = new ArrayList<Player>() {{
            add(new Player("player 1", new Chips(100), null));
            add(new Player("player 2", new Chips(100), null));
            add(new Player("player 3", new Chips(100), null));
            add(new Player("player 4", new Chips(100), null));
        }};

        PlayerManager pm = new PlayerManager();

        assertEquals(new ArrayList<Player>(), pm.getPlayers());

        pm.addPlayer(new Player("player 2", null, null), 4);
        pm.addPlayer(new Player("player 1", null, null), 3);
        pm.addPlayer(new Player("player 4", null, null), 9);
        pm.addPlayer(new Player("player 3", null, null), 6);

        for (int i=0; i<4; ++i) {
            players.get(i).drawCard(new Card(CardSuit.CUBS, CardRank.EIGHT));
            players.get(i).drawCard(new Card(CardSuit.SPADES, CardRank.EIGHT));
            assertEquals(players.get(i).toString(), pm.getPlayers().get(i).toString());
        }
    }
}
