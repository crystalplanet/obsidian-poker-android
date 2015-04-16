package com.crystalplanet.obsidianpoker.game.card;

public class CardSuit implements Comparable<CardSuit> {

    public static final CardSuit[] SUITS = new CardSuit[] {
        new CardSuit(0),
        new CardSuit(1),
        new CardSuit(2),
        new CardSuit(3)
    };

    public static final CardSuit DIAMONDS = SUITS[0];
    public static final CardSuit CUBS = SUITS[1];
    public static final CardSuit HEARTS = SUITS[2];
    public static final CardSuit SPADES = SUITS[3];

    private int suit;

    private CardSuit(int suit) {
        this.suit = suit;
    }

    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof CardSuit && hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return suit;
    }

    @Override
    public int compareTo(CardSuit other) {
        return other == null ? 1 : suit - other.suit;
    }
}
