package com.crystalplanet.obsidianpoker.app.model;

public class Card implements Comparable<Card> {

    private CardSuit suit;
    private CardRank rank;

    public Card(CardSuit suit, CardRank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public int compareTo(Card other) {
        return other == null
                ? 1
                : rank.compareTo(other.rank) == 0 ? suit.compareTo(other.suit) : rank.compareTo(other.rank);
    }
}
