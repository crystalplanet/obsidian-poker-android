package com.crystalplanet.obsidianpoker.app.model;

import com.crystalplanet.obsidianpoker.util.BiLambda;

import java.util.HashMap;
import java.util.Iterator;

public class Pot {

    private HashMap<Player, Chips> bets = new HashMap<Player, Chips>();

    public Chips size() {
        return count(bets.values().iterator(), TOTAL_CHIPS);
    }

    public boolean isEmpty() {
        return size().compareTo(new Chips(0)) <= 0;
    }

    public void placeBet(Chips bet, Player player) {
        if (bets.get(player) == null) bets.put(player, new Chips(0));
        bets.put(player, bets.get(player).add(bet));
    }

    public Chips currentBet() {
        return count(bets.values().iterator(), BET_SIZE);
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
