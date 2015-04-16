package com.crystalplanet.obsidianpoker.game.card;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DeckTest extends TestCase {

    public void testIsEmpty() {
        Deck deck = new Deck();

        Assert.assertFalse(deck.isEmpty());

        int count = 0;

        while (!deck.isEmpty()) {
            deck.nextCard();

            ++count;
        }

        Assert.assertTrue(deck.isEmpty());
        Assert.assertEquals(52, count);
    }

    public void testShuffledSet() {
        Deck freshDeck = new Deck();
        Deck shuffledDeck = new Deck();

        shuffledDeck.shuffle();

        HashMap<Deck, HashSet<Card>> cards = new HashMap<Deck, HashSet<Card>>();

        cards.put(freshDeck, new HashSet<Card>());
        cards.put(shuffledDeck, new HashSet<Card>());

        while (!freshDeck.isEmpty() || !shuffledDeck.isEmpty()) {
            cards.get(freshDeck).add(freshDeck.nextCard());
            cards.get(shuffledDeck).add(shuffledDeck.nextCard());
        }

        assertEquals(cards.get(freshDeck), cards.get(shuffledDeck));
    }
}