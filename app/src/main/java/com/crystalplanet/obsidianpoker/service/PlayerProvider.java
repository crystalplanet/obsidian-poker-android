package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.chips.Chips;
import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.game.PlayerHandler;

public class PlayerProvider {
    public Player newPlayer(String name, Chips chips, PlayerHandler handler) {
        return new Player(name, chips, handler);
    }
}
