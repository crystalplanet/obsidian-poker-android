package com.crystalplanet.obsidianpoker.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Turn implements Iterator<Player> {

    private ArrayList<Player> players;

    private Iterator<Player> iterator;

    private Player current;

    public Turn(Collection<Player> players) {
        this.players = new ArrayList<Player>(players);
    }

    @Override
    public boolean hasNext() {
        int count = 0;
        for (Player player : players)
            if (hasTurn(player)) ++count;
        return count > 1;
    }

    @Override
    public Player next() {
        if (!(current == null || hasTurn(current))) iterator.remove();
        if (!hasNext()) throw new IndexOutOfBoundsException();
        if (iterator == null || !iterator.hasNext()) iterator = players.iterator();

        return hasTurn(current = iterator.next()) ? current : next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private boolean hasTurn(Player player) {
        return !(player.isFolded() || player.isAllIn());
    }
}
