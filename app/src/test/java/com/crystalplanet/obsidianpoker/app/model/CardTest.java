package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CardTest extends TestCase {

    public void testCompareRanks() {
        CardSuit spades = CardSuit.SPADES;

        Assert.assertEquals(-1, new Card(spades, CardRank.TWO).compareTo(new Card(spades, CardRank.THREE)));
        Assert.assertEquals(-1, new Card(spades, CardRank.THREE).compareTo(new Card(spades, CardRank.FOUR)));
        Assert.assertEquals(-1, new Card(spades, CardRank.FOUR).compareTo(new Card(spades, CardRank.FIVE)));
        Assert.assertEquals(-1, new Card(spades, CardRank.FIVE).compareTo(new Card(spades, CardRank.SIX)));
        Assert.assertEquals(-1, new Card(spades, CardRank.SIX).compareTo(new Card(spades, CardRank.SEVEN)));
        Assert.assertEquals(-1, new Card(spades, CardRank.SEVEN).compareTo(new Card(spades, CardRank.EIGHT)));
        Assert.assertEquals(-1, new Card(spades, CardRank.EIGHT).compareTo(new Card(spades, CardRank.NINE)));
        Assert.assertEquals(-1, new Card(spades, CardRank.NINE).compareTo(new Card(spades, CardRank.TEN)));
        Assert.assertEquals(-1, new Card(spades, CardRank.TEN).compareTo(new Card(spades, CardRank.JACK)));
        Assert.assertEquals(-1, new Card(spades, CardRank.JACK).compareTo(new Card(spades, CardRank.QUEEN)));
        Assert.assertEquals(-1, new Card(spades, CardRank.QUEEN).compareTo(new Card(spades, CardRank.KING)));
        Assert.assertEquals(-1, new Card(spades, CardRank.KING).compareTo(new Card(spades, CardRank.ACE)));
    }

    public void testCompareSuits() {
        CardRank ace = CardRank.ACE;

        Assert.assertEquals(-1, new Card(CardSuit.DIAMONDS, ace).compareTo(new Card(CardSuit.CUBS, ace)));
        Assert.assertEquals(-1, new Card(CardSuit.CUBS, ace).compareTo(new Card(CardSuit.HEARTS, ace)));
        Assert.assertEquals(-1, new Card(CardSuit.HEARTS, ace).compareTo(new Card(CardSuit.SPADES, ace)));
    }

    public void testCompareRankOverSuit() {
        Assert.assertEquals(
            -1,
            new Card(CardSuit.SPADES, CardRank.FOUR).compareTo(new Card(CardSuit.DIAMONDS, CardRank.EIGHT))
        );
    }

    public void testCompareNull() {
        Assert.assertEquals(1, new Card(CardSuit.SPADES, CardRank.ACE).compareTo(null));
    }
}
