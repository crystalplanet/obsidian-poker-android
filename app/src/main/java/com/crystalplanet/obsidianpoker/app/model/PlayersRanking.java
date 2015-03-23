package com.crystalplanet.obsidianpoker.app.model;

import java.util.*;

public class PlayersRanking implements Iterable<Set<Player>> {

    private TreeMap<Hand, Set<Player>> players = new TreeMap<Hand, Set<Player>>();

    private Collection<Card> commonCards;

    public PlayersRanking(Collection<Card> commonCards) {
        this.commonCards = commonCards;
    }

    public PlayersRanking(Collection<Card> commonCards, Collection<Player> players) {
        this(commonCards);
        for (Player player : players)
            add(player);
    }

    public void add(Player player) {
        getPlayers(player.hand(commonCards)).add(player);
    }

    public Set<Player> first() {
        return players.lastEntry().getValue();
    }

    @Override
    public Iterator<Set<Player>> iterator() {
        return players.descendingMap().values().iterator();
    }

    private Set<Player> getPlayers(Hand hand) {
        if (players.get(hand) == null) players.put(hand, new HashSet<Player>());

        return players.get(hand);
    }
}
