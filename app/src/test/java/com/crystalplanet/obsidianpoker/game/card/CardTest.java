package com.crystalplanet.obsidianpoker.game.card;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CardTest extends TestCase {

    public void testCompareToDifferentRanks() {
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

        Assert.assertEquals(-12, new Card(spades, CardRank.TWO).compareTo(new Card(spades, CardRank.ACE)));
        Assert.assertEquals(4, new Card(spades, CardRank.TEN).compareTo(new Card(spades, CardRank.SIX)));
    }

    public void testCompareToDifferentSuits() {
        CardRank ace = CardRank.ACE;

        Assert.assertEquals(0, new Card(CardSuit.DIAMONDS, ace).compareTo(new Card(CardSuit.CUBS, ace)));
        Assert.assertEquals(0, new Card(CardSuit.CUBS, ace).compareTo(new Card(CardSuit.HEARTS, ace)));
        Assert.assertEquals(0, new Card(CardSuit.HEARTS, ace).compareTo(new Card(CardSuit.SPADES, ace)));
    }

    public void testCompareNull() {
        Assert.assertEquals(1, new Card(CardSuit.SPADES, CardRank.ACE).compareTo(null));
    }

    public void testEquals() {
        Assert.assertTrue(new Card(CardSuit.HEARTS, CardRank.TWO).equals(new Card(CardSuit.HEARTS, CardRank.TWO)));
        Assert.assertFalse(new Card(CardSuit.HEARTS, CardRank.TWO).equals(new Card(CardSuit.HEARTS, CardRank.SIX)));
        Assert.assertFalse(new Card(CardSuit.HEARTS, CardRank.TWO).equals(new Card(CardSuit.SPADES, CardRank.TWO)));
        Assert.assertFalse(new Card(CardSuit.HEARTS, CardRank.FOUR).equals(null));
        Assert.assertFalse(new Card(CardSuit.HEARTS, CardRank.FOUR).equals(new Object()));
    }
}
