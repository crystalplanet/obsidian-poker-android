package com.crystalplanet.obsidianpoker.app.model;

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
    public int compareTo(Card other) {
        return COMPARE_RANK.compare(this, other);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Card)) return false;
        Card otherCard = (Card)other;
        return suit == otherCard.suit && rank == otherCard.rank;
    }
}
