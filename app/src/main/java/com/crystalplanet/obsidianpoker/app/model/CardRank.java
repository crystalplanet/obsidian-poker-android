package com.crystalplanet.obsidianpoker.app.model;

public class CardRank implements Comparable<CardRank> {

    public static final CardRank[] RANKS = new CardRank[] {
        new CardRank(0),
        new CardRank(1),
        new CardRank(2),
        new CardRank(3),
        new CardRank(4),
        new CardRank(5),
        new CardRank(6),
        new CardRank(7),
        new CardRank(8),
        new CardRank(9),
        new CardRank(10),
        new CardRank(11),
        new CardRank(12)
    };

    public static final CardRank TWO = RANKS[0];
    public static final CardRank THREE = RANKS[1];
    public static final CardRank FOUR = RANKS[2];
    public static final CardRank FIVE = RANKS[3];
    public static final CardRank SIX = RANKS[4];
    public static final CardRank SEVEN = RANKS[5];
    public static final CardRank EIGHT = RANKS[6];
    public static final CardRank NINE = RANKS[7];
    public static final CardRank TEN = RANKS[8];
    public static final CardRank JACK = RANKS[9];
    public static final CardRank QUEEN = RANKS[10];
    public static final CardRank KING = RANKS[11];
    public static final CardRank ACE = RANKS[12];

    private int rank;

    private CardRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int compareTo(CardRank other) {
        return other == null ? 1 : rank - other.rank;
    }
}
