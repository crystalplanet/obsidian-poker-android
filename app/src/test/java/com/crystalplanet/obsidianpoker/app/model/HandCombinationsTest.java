package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HandCombinationsTest extends TestCase {

    public void testSingleCombination() {
        List<Hand> hands = new ArrayList<Hand>();
        for (Hand hand : new HandCombinations(cards))
            hands.add(hand);

        assertEquals(1, hands.size());
        assertEquals(0, new Hand(cards).compareTo(hands.get(0)));
    }

    public void testMultipleCombinations() {
        List<Hand> hands = new ArrayList<Hand>();
        for (Hand hand : new HandCombinations(cards, moreCards))
            hands.add(hand);

        assertEquals(21, hands.size());
        assertEquals(new HashSet<Hand>(hands).size(), hands.size());
    }

    public void testBestCmbination() {
        HandCombinations combinations = new HandCombinations(cards, moreCards);
        assertEquals(new Hand(bestHand), combinations.bestHand());
    }

    private Set<Card> cards = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.DIAMONDS, CardRank.ACE));
        add(new Card(CardSuit.HEARTS, CardRank.TEN));
        add(new Card(CardSuit.SPADES, CardRank.FIVE));
        add(new Card(CardSuit.DIAMONDS, CardRank.FOUR));
    }};

    private Set<Card> moreCards = new HashSet<Card>() {{
        add(new Card(CardSuit.SPADES, CardRank.TWO));
        add(new Card(CardSuit.DIAMONDS, CardRank.KING));
    }};

    private Set<Card> bestHand = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.DIAMONDS, CardRank.ACE));
        add(new Card(CardSuit.DIAMONDS, CardRank.KING));
        add(new Card(CardSuit.HEARTS, CardRank.TEN));
        add(new Card(CardSuit.SPADES, CardRank.FIVE));
    }};
}
