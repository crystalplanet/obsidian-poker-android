package com.crystalplanet.obsidianpoker.game.card;

import com.crystalplanet.obsidianpoker.util.Pair;

import java.util.*;

public class Hand implements Comparable<Hand> {

    private static final int HIGH_CARD = 1;
    private static final int PAIR = 2;
    private static final int TWO_PAIRS = 3;
    private static final int THREE_OF_A_KIND = 4;
    private static final int STRAIGHT_LOW = 5;
    private static final int STRAIGHT = 6;
    private static final int FLUSH = 7;
    private static final int FULL_HOUSE = 8;
    private static final int FOUR_OF_A_KIND = 9;
    private static final int STRAIGHT_FLUSH_LOW = 10;
    private static final int STRAIGHT_FLUSH = 11;

    private ArrayList<Card> cards;

    private TreeMap<CardRank, Integer> ranks = new TreeMap();

    private Integer hand = 0;

    public Hand(Set<Card> cards) {
        this.cards = new ArrayList<Card>(cards);
        aggregateRanks();
        hand = straightFlush();
    }

    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Hand && hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (CardRank rank : ranks.keySet()) hash += Math.pow(5, rank.hashCode()) + ranks.get(rank);

        return hash;
    }

    @Override
    public int compareTo(Hand other) {
        if (other == null) return 1;
        if (!hand.equals(other.hand)) return hand.compareTo(other.hand);

        ArrayList<Pair<CardRank, Integer>> groups = grouppedRanks();
        ArrayList<Pair<CardRank, Integer>> otherGroups = other.grouppedRanks();

        int i = 0;
        while (i < groups.size() && groups.get(i).first.compareTo(otherGroups.get(i).first) == 0) ++i;
        return i < groups.size() ? groups.get(i).first.compareTo(otherGroups.get(i).first) : 0;
    }

    private ArrayList<Pair<CardRank, Integer>> grouppedRanks() {
        ArrayList<Pair<CardRank, Integer>> switched = new ArrayList();
        for (CardRank rank : ranks.keySet())
            switched.add(new Pair<CardRank, Integer>(rank, ranks.get(rank)));
        Collections.sort(switched, GROUP_CMP);
        return switched;
    }

    private void aggregateRanks() {
        for (CardRank rank : CardRank.RANKS)
            if (aggregateRank(cards.iterator(), rank) > 0) ranks.put(rank, aggregateRank(cards.iterator(), rank));
    }

    private int aggregateRank(Iterator<Card> it, CardRank rank) {
        return it.hasNext() ? (it.next().compareTo(new Card(null, rank)) == 0 ? 1 : 0) + aggregateRank(it, rank) : 0;
    }

    private boolean isStraight() {
        return ranks.size() == 5 && (ranks.lastKey().compareTo(ranks.firstKey()) == 4 || isLowStraight());
    }

    private boolean isLowStraight() {
        return ranks.lastKey().compareTo(ranks.lowerKey(ranks.lastKey())) == 9;
    }

    private boolean isFlush() {
        Collections.sort(cards, Card.COMPARE_SUIT);
        return Card.COMPARE_SUIT.compare(cards.get(0), cards.get(cards.size() - 1)) == 0;
    }

    private int straightFlush() {
        return isStraight() && isFlush() ? isLowStraight() ? STRAIGHT_FLUSH_LOW : STRAIGHT_FLUSH : fourOfAKind();
    }

    private int fourOfAKind() {
        return Collections.frequency(ranks.values(), 4) == 1 ? FOUR_OF_A_KIND : fullHouse();
    }

    private int fullHouse() {
        return ranks.size() == 2 && threeOfAKind() == THREE_OF_A_KIND ? FULL_HOUSE : flush();
    }

    private int flush() {
        return isFlush() ? FLUSH : straight();
    }

    private int straight() {
        return isStraight() ? isLowStraight() ? STRAIGHT_LOW : STRAIGHT : threeOfAKind();
    }

    private int threeOfAKind() {
        return Collections.frequency(ranks.values(), 3) == 1 ? THREE_OF_A_KIND : twoPairs();
    }

    private int twoPairs() {
        return Collections.frequency(ranks.values(), 2) == 2 ? TWO_PAIRS : pair();
    }

    private int pair() {
        return ranks.size() == 4 ? PAIR : HIGH_CARD;
    }

    private static final Comparator<Pair<CardRank, Integer>> GROUP_CMP = new Comparator<Pair<CardRank, Integer>>() {
        @Override
        public int compare(Pair<CardRank, Integer> l, Pair<CardRank, Integer> r) {
            return l.second.equals(r.second) ? l.first.compareTo(r.first) : l.second.compareTo(r.second);
        }
    };
}
