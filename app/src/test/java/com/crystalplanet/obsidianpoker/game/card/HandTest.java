package com.crystalplanet.obsidianpoker.game.card;

import junit.framework.TestCase;

import java.util.HashSet;

public class HandTest extends TestCase {

    public void testEquals() {
        assertTrue(new Hand(flush).equals(new Hand(flush)));
        assertTrue(new Hand(flush).equals(new Hand(flushCopy)));
        assertFalse(new Hand(flush).equals(flushOtherSuit));
        assertFalse(new Hand(flush).equals(null));
        assertFalse(new Hand(flush).equals(new Object()));
    }

    public void testBasicComparison() {
        assertTrue(new Hand(straightFlush).compareTo(new Hand(fourOfAKind)) > 0);
        assertTrue(new Hand(fourOfAKind).compareTo(new Hand(fullHouse)) > 0);
        assertTrue(new Hand(fullHouse).compareTo(new Hand(flush)) > 0);
        assertTrue(new Hand(flush).compareTo(new Hand(straight)) > 0);
        assertTrue(new Hand(straight).compareTo(new Hand(threeOfAKind)) > 0);
        assertTrue(new Hand(threeOfAKind).compareTo(new Hand(twoPairs)) > 0);
        assertTrue(new Hand(twoPairs).compareTo(new Hand(pair)) > 0);
        assertTrue(new Hand(pair).compareTo(new Hand(highCard)) > 0);
    }

    public void testStraightWithLowAce() {
        assertTrue(new Hand(straight).compareTo(new Hand(straightLowAce)) > 0);
        assertTrue(new Hand(straightLowAce).compareTo(new Hand(threeOfAKind)) > 0);
    }

    public void testFlush() {
        assertTrue(new Hand(flush).compareTo(new Hand(flushLower)) > 0);
        assertTrue(new Hand(flush).compareTo(new Hand(flushOtherSuit)) == 0);
    }

    public void testPairs() {
        assertTrue(new Hand(pair).compareTo(new Hand(equalPairHand)) == 0);
        assertTrue(new Hand(pair).compareTo(new Hand(equalPair)) > 0);
        assertTrue(new Hand(pair).compareTo(new Hand(otherPair)) > 0);
    }

    public void testHighCard() {
        assertTrue(new Hand(highCard).compareTo(new Hand(lowerHighCard)) > 0);
    }

    private HashSet<Card> straightFlush = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.CUBS, CardRank.KING));
        add(new Card(CardSuit.CUBS, CardRank.QUEEN));
        add(new Card(CardSuit.CUBS, CardRank.JACK));
        add(new Card(CardSuit.CUBS, CardRank.TEN));
    }};

    private HashSet<Card> fourOfAKind = new HashSet<Card>() {{
        add(new Card(CardSuit.SPADES, CardRank.FIVE));
        add(new Card(CardSuit.HEARTS, CardRank.FIVE));
        add(new Card(CardSuit.CUBS, CardRank.FIVE));
        add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        add(new Card(CardSuit.CUBS, CardRank.KING));
    }};

    private HashSet<Card> fullHouse = new HashSet<Card>() {{
        add(new Card(CardSuit.SPADES, CardRank.EIGHT));
        add(new Card(CardSuit.DIAMONDS, CardRank.EIGHT));
        add(new Card(CardSuit.HEARTS, CardRank.EIGHT));
        add(new Card(CardSuit.HEARTS, CardRank.TEN));
        add(new Card(CardSuit.DIAMONDS, CardRank.TEN));
    }};

    private HashSet<Card> flush = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.TEN));
        add(new Card(CardSuit.CUBS, CardRank.TWO));
        add(new Card(CardSuit.CUBS, CardRank.JACK));
        add(new Card(CardSuit.CUBS, CardRank.QUEEN));
        add(new Card(CardSuit.CUBS, CardRank.KING));
    }};

    private HashSet<Card> flushCopy = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.TEN));
        add(new Card(CardSuit.CUBS, CardRank.TWO));
        add(new Card(CardSuit.CUBS, CardRank.JACK));
        add(new Card(CardSuit.CUBS, CardRank.QUEEN));
        add(new Card(CardSuit.CUBS, CardRank.KING));
    }};

    private HashSet<Card> flushLower = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.TEN));
        add(new Card(CardSuit.HEARTS, CardRank.TWO));
        add(new Card(CardSuit.HEARTS, CardRank.JACK));
        add(new Card(CardSuit.HEARTS, CardRank.QUEEN));
        add(new Card(CardSuit.HEARTS, CardRank.THREE));
    }};

    private HashSet<Card> flushOtherSuit = new HashSet<Card>() {{
        add(new Card(CardSuit.SPADES, CardRank.TEN));
        add(new Card(CardSuit.SPADES, CardRank.TWO));
        add(new Card(CardSuit.SPADES, CardRank.JACK));
        add(new Card(CardSuit.SPADES, CardRank.QUEEN));
        add(new Card(CardSuit.SPADES, CardRank.KING));
    }};

    private HashSet<Card> straight = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.SIX));
        add(new Card(CardSuit.HEARTS, CardRank.SEVEN));
        add(new Card(CardSuit.CUBS, CardRank.EIGHT));
        add(new Card(CardSuit.DIAMONDS, CardRank.NINE));
        add(new Card(CardSuit.SPADES, CardRank.TEN));
    }};

    private HashSet<Card> straightLowAce = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.FIVE));
        add(new Card(CardSuit.HEARTS, CardRank.FOUR));
        add(new Card(CardSuit.HEARTS, CardRank.THREE));
        add(new Card(CardSuit.SPADES, CardRank.TWO));
        add(new Card(CardSuit.HEARTS, CardRank.ACE));
    }};

    private HashSet<Card> threeOfAKind = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.TEN));
        add(new Card(CardSuit.DIAMONDS, CardRank.TEN));
        add(new Card(CardSuit.SPADES, CardRank.TEN));
        add(new Card(CardSuit.HEARTS, CardRank.EIGHT));
        add(new Card(CardSuit.HEARTS, CardRank.NINE));
    }};

    private HashSet<Card> twoPairs = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.TEN));
        add(new Card(CardSuit.SPADES, CardRank.TEN));
        add(new Card(CardSuit.HEARTS, CardRank.ACE));
        add(new Card(CardSuit.DIAMONDS, CardRank.ACE));
        add(new Card(CardSuit.HEARTS, CardRank.NINE));
    }};

    private HashSet<Card> pair = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.THREE));
        add(new Card(CardSuit.HEARTS, CardRank.THREE));
        add(new Card(CardSuit.SPADES, CardRank.NINE));
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.DIAMONDS, CardRank.JACK));
    }};

    private HashSet<Card> equalPairHand = new HashSet<Card>() {{
        add(new Card(CardSuit.SPADES, CardRank.THREE));
        add(new Card(CardSuit.DIAMONDS, CardRank.THREE));
        add(new Card(CardSuit.CUBS, CardRank.NINE));
        add(new Card(CardSuit.HEARTS, CardRank.ACE));
        add(new Card(CardSuit.SPADES, CardRank.JACK));
    }};

    private HashSet<Card> equalPair = new HashSet<Card>() {{
        add(new Card(CardSuit.DIAMONDS, CardRank.THREE));
        add(new Card(CardSuit.SPADES, CardRank.THREE));
        add(new Card(CardSuit.CUBS, CardRank.FIVE));
        add(new Card(CardSuit.DIAMONDS, CardRank.EIGHT));
        add(new Card(CardSuit.CUBS, CardRank.SEVEN));
    }};

    private HashSet<Card> otherPair = new HashSet<Card>() {{
        add(new Card(CardSuit.DIAMONDS, CardRank.TWO));
        add(new Card(CardSuit.SPADES, CardRank.TWO));
        add(new Card(CardSuit.CUBS, CardRank.FIVE));
        add(new Card(CardSuit.DIAMONDS, CardRank.EIGHT));
        add(new Card(CardSuit.CUBS, CardRank.SEVEN));
    }};

    private HashSet<Card> highCard = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.ACE));
        add(new Card(CardSuit.SPADES, CardRank.KING));
        add(new Card(CardSuit.HEARTS, CardRank.QUEEN));
        add(new Card(CardSuit.HEARTS, CardRank.JACK));
        add(new Card(CardSuit.HEARTS, CardRank.NINE));
    }};

    private HashSet<Card> lowerHighCard = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.DIAMONDS, CardRank.KING));
        add(new Card(CardSuit.CUBS, CardRank.QUEEN));
        add(new Card(CardSuit.CUBS, CardRank.JACK));
        add(new Card(CardSuit.DIAMONDS, CardRank.FOUR));
    }};
}
