package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    List<Player> players = new ArrayList<Player>();

    public void addPlayer(Player player) {
        if (!players.contains(player)) players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
