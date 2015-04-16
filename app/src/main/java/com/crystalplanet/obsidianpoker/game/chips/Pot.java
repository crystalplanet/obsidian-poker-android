package com.crystalplanet.obsidianpoker.game.chips;

import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.util.BiLambda;

import java.util.*;

public class Pot {

    private HashMap<Player, Chips> bets = new HashMap<Player, Chips>();

    public Pot() {
    }

    public Pot(Pot... pots) {
        for (Pot pot : pots)
            for (Player player : pot.bets.keySet())
                takeBet(player, pot.bets.get(player));
    }

    public Chips size() {
        return count(bets.values().iterator(), TOTAL_CHIPS);
    }

    public boolean isEmpty() {
        return size().compareTo(new Chips(0)) <= 0;
    }

    public void takeBet(Player player, Chips bet) {
        if (bets.get(player) == null) bets.put(player, new Chips(0));

        bets.put(player, bets.get(player).add(bet));
    }

    public Chips currentBet() {
        return count(bets.values().iterator(), BET_SIZE);
    }

    public Chips playersBet(Player player) {
        return bets.containsKey(player) ? bets.get(player) : new Chips(0);
    }

    public Chips payOut(Player winner) {
        return removeFromAll(playersBet(winner));
    }

    private Chips removeFromAll(Chips chips) {
        Chips sum = new Chips(0);

        for (Player player : bets.keySet())
            sum = sum.add(remove(player, chips));

        return sum;
    }

    private Chips remove(Player player, Chips chips) {
        Chips toRemove = Collections.min(new ArrayList<Chips>(Arrays.asList(chips, playersBet(player))));

        bets.put(player, bets.get(player).substract(toRemove));

        return toRemove;
    }

    private Chips count(Iterator<Chips> it, BiLambda<Chips, Chips, Chips> countLambda) {
        return it.hasNext() ? countLambda.apply(it.next(), count(it, countLambda)) : new Chips(0);
    }

    private static final BiLambda<Chips, Chips, Chips> TOTAL_CHIPS = new BiLambda<Chips, Chips, Chips>() {
        @Override
        public Chips apply(Chips l, Chips r) {
            return l.add(r);
        }
    };

    private static final BiLambda<Chips, Chips, Chips> BET_SIZE = new BiLambda<Chips, Chips, Chips>() {
        @Override
        public Chips apply(Chips l, Chips r) {
            return l.compareTo(r) > 0 ? l : r;
        }
    };
}
