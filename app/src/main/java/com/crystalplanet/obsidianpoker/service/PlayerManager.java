package com.crystalplanet.obsidianpoker.service;

import android.util.Log;
import com.crystalplanet.obsidianpoker.game.Player;

import java.util.*;

public class PlayerManager {

    private TreeMap<Integer, Player> players = new TreeMap<Integer, Player>();

    public void addPlayer(Player player, int seat) {
        if (players.get(seat) == null && !players.values().contains(player)) players.put(seat, player);
    }

    public List<Player> getPlayers() {
        return new ArrayList<Player>(players.values());
    }

    public Player getPlayer(int seat) {
        return players.get(seat);
    }

    public int getSeat(Player player) {
        for (Map.Entry<Integer, Player> entry : players.entrySet())
            if (entry.getValue() == player) return entry.getKey();

        return -1;
    }
}
