package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.Player;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerManagerTest {

    @Test
    public void testPlayerManager() {
        ArrayList<Player> players = new ArrayList<Player>() {{
            add(new Player(null, null, null));
            add(new Player(null, null, null));
            add(new Player(null, null, null));
        }};

        PlayerManager pm = new PlayerManager();

        Assert.assertEquals(new ArrayList<Player>(), pm.getPlayers());

        for (Player player : players)
            pm.addPlayer(player);

        Assert.assertEquals(players, pm.getPlayers());

        pm.addPlayer(players.get(1));

        Assert.assertEquals(players, pm.getPlayers());
    }
}
