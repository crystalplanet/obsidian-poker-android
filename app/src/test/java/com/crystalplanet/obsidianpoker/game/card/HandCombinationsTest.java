package com.crystalplanet.obsidianpoker.game.card;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HandCombinationsTest extends TestCase {

    public void testSingleCombination() {
        List<Hand> hands = new ArrayList<Hand>();
        for (Hand hand : new HandCombinations(baseCards))
            hands.add(hand);

        assertEquals(1, hands.size());
        assertEquals(0, new Hand(baseCards).compareTo(hands.get(0)));
    }

    public void testCombinationsRepeatingRanks() {
        List<Hand> hands = new ArrayList<Hand>();
        for (Hand hand : new HandCombinations(baseCards, repeatingRanksCards))
            hands.add(hand);

        assertEquals(16, hands.size());
    }

    public void testCombinationsNonRepeatingCards() {
        List<Hand> hands = new ArrayList<Hand>();
        for (Hand hand : new HandCombinations(baseCards, nonRepeatingCards))
            hands.add(hand);

        assertEquals(21, hands.size());
    }

    public void testBestCmbination() {
        HandCombinations combinations = new HandCombinations(baseCards, nonRepeatingCards);
        assertEquals(new Hand(bestHand), combinations.bestHand());
    }

    public void testBestHand() {
        List<Hand> hands = new ArrayList<Hand>();

        hands.add(new HandCombinations(hand1, commonCards).bestHand());
        hands.add(new HandCombinations(hand2, commonCards).bestHand());
        hands.add(new HandCombinations(hand3, commonCards).bestHand());

        assertTrue(hands.get(0).compareTo(hands.get(1)) == 0);
        assertTrue(hands.get(0).compareTo(hands.get(2)) > 0);
    }

    private Set<Card> baseCards = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.DIAMONDS, CardRank.KING));
        add(new Card(CardSuit.HEARTS, CardRank.TEN));
        add(new Card(CardSuit.SPADES, CardRank.FIVE));
        add(new Card(CardSuit.DIAMONDS, CardRank.FOUR));
    }};

    private Set<Card> repeatingRanksCards = new HashSet<Card>() {{
        add(new Card(CardSuit.DIAMONDS, CardRank.ACE));
        add(new Card(CardSuit.SPADES, CardRank.TWO));
    }};

    private Set<Card> nonRepeatingCards = new HashSet<Card>() {{
        add(new Card(CardSuit.DIAMONDS, CardRank.SEVEN));
        add(new Card(CardSuit.SPADES, CardRank.NINE));
    }};

    private Set<Card> bestHand = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.DIAMONDS, CardRank.KING));
        add(new Card(CardSuit.HEARTS, CardRank.TEN));
        add(new Card(CardSuit.SPADES, CardRank.NINE));
        add(new Card(CardSuit.DIAMONDS, CardRank.SEVEN));
    }};

    private Set<Card> hand1 = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.QUEEN));
        add(new Card(CardSuit.DIAMONDS, CardRank.SIX));
    }};

    private Set<Card> hand2 = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.FOUR));
        add(new Card(CardSuit.SPADES, CardRank.QUEEN));
    }};

    private Set<Card> hand3 = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.TWO));
        add(new Card(CardSuit.CUBS, CardRank.TEN));
    }};

    private Set<Card> commonCards = new HashSet<Card>() {{
        add(new Card(CardSuit.SPADES, CardRank.TEN));
        add(new Card(CardSuit.SPADES, CardRank.SEVEN));
        add(new Card(CardSuit.CUBS, CardRank.QUEEN));
        add(new Card(CardSuit.HEARTS, CardRank.SEVEN));
        add(new Card(CardSuit.HEARTS, CardRank.JACK));
    }};
}
