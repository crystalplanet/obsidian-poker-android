package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.chips.Chips;
import junit.framework.Assert;
import org.junit.Test;

public class GameConfigurationTest {

    @Test
    public void testSmallBlind() {
        GameConfiguration config = new GameConfiguration();

        Assert.assertEquals(null, config.getSmallBlind());

        config.setSmallBlind(new Chips(100));

        Assert.assertEquals(new Chips(100), config.getSmallBlind());
    }
}
