package com.crystalplanet.obsidianpoker.app.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Turns implements Iterable<Player> {

    private ArrayList<Player> players;

    public Turns(Collection<Player> players) {
        this.players = new ArrayList<Player>(players);
    }

    @Override
    public Iterator<Player> iterator() {
        return new Iterator<Player>() {

            private Iterator<Player> playersIterator;
            private Player current;

            @Override
            public boolean hasNext() {
                int count = 0;
                for (Player player : players)
                    if (hasTurn(player)) ++count;
                return count > 1;
            }

            @Override
            public Player next() {
                if (!(current == null || hasTurn(current))) playersIterator.remove();
                if (!hasNext()) throw new IndexOutOfBoundsException();
                if (playersIterator == null || !playersIterator.hasNext()) playersIterator = players.iterator();

                return hasTurn(current = playersIterator.next()) ? current : next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private boolean hasTurn(Player player) {
                return !(player.isFolded() || player.isAllIn());
            }
        };
    }
}
