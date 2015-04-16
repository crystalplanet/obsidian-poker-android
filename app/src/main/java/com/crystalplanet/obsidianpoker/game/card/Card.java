package com.crystalplanet.obsidianpoker.game.card;

import java.util.Comparator;

public class Card implements Comparable<Card> {
    public static final Comparator<Card> COMPARE_SUIT = new Comparator<Card>() {
        @Override
        public int compare(Card l, Card r) {
            return r == null ? 1 : l == null ? -1 : l.suit.compareTo(r.suit);
        }
    };

    public static final Comparator<Card> COMPARE_RANK = new Comparator<Card>() {
        @Override
        public int compare(Card l, Card r) {
            return r == null ? 1 : l == null ? -1 : l.rank.compareTo(r.rank);
        }
    };

    private CardSuit suit;
    private CardRank rank;

    public Card(CardSuit suit, CardRank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Card && hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return 16*suit.hashCode() + rank.hashCode();
    }

    @Override
    public int compareTo(Card other) {
        return COMPARE_RANK.compare(this, other);
    }
}
