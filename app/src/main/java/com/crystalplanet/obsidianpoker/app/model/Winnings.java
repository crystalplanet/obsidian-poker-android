package com.crystalplanet.obsidianpoker.app.model;

import java.util.*;

public class Winnings {

    private HashMap<Player, Chips> winnings = new HashMap<Player, Chips>();

    private Pot pot;

    public Winnings(Collection<Player> players, Collection<Card> commonCards, Pot pot) {
        this.pot = pot;

        HashMap<Hand, List<Player>> ranking = new HashMap<Hand, List<Player>>();
        for (Player player : players)
            if (!player.isFolded() && player.isActive()) addPlayer(ranking, player, commonCards);

        distributePot(new TreeMap<Hand, List<Player>>(ranking));
    }

    public Chips playersShare(Player player) {
        return winnings != null && winnings.containsKey(player) ? winnings.get(player) : new Chips(0);
    }

    public void payOut() {
        for (Player player : winnings.keySet())
            player.addChips(playersShare(player));

        winnings = null;
    }

    private void addPlayer(HashMap<Hand, List<Player>> ranking, Player player, Collection<Card> commonCards) {
        List<Player> players = getPlayers(ranking, player.hand(commonCards));
        players.add(player);
        Collections.sort(players, LOWEST_BET);
    }

    private List<Player> getPlayers(HashMap<Hand, List<Player>> ranking, Hand hand) {
        if (!ranking.containsKey(hand)) ranking.put(hand, new ArrayList<Player>());

        return ranking.get(hand);
    }

    private void distributePot(TreeMap<Hand, List<Player>> ranking) {
        for (List<Player> playersset : ranking.descendingMap().values())
            for (Player player : playersset)
                divideChips(pot.payOut(player), playersset.subList(playersset.indexOf(player), playersset.size()));
    }

    private void divideChips(Chips chips, List<Player> players) {
        for (Player player : players)
            addWinnings(player, chips.split(players.size()));
    }

    private void addWinnings(Player player, Chips chips) {
        if (!winnings.containsKey(player)) winnings.put(player, new Chips(0));

        winnings.put(player, winnings.get(player).add(chips));
    }

    private Comparator<Player> LOWEST_BET = new Comparator<Player>() {
        @Override
        public int compare(Player l, Player r) {
            return pot.playersBet(l).compareTo(pot.playersBet(r));
        }
    };
}
