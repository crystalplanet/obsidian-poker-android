package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.GameObserver;
import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.game.PlayerHandler;
import com.crystalplanet.obsidianpoker.game.PokerRound;
import com.crystalplanet.obsidianpoker.game.chips.Chips;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerProviderTest {

    @Test
    public void testNewPlayer() {
        PokerRound round = new PokerRound(new ArrayList<GameObserver>(), new ArrayList<Player>(), null, null);
        Handler handler = new Handler();

        PlayerProvider pp = new PlayerProvider();

        Player player = pp.newPlayer("PlayerName", new Chips(30), handler);

        player.setCurrentRound(round);

        Assert.assertEquals("PlayerName", player.toString());
        Assert.assertEquals(new Chips(30), player.chips());

        player.play();

        Assert.assertEquals(player, handler.player);
    }

    private class Handler implements PlayerHandler {

        public Player player;

        @Override
        public void getNextAction(Player player) {
            this.player = player;
        }
    }
}
