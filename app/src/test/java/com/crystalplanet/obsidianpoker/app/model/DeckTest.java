package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;

public class DeckTest extends TestCase {

    public void testShuffle() {
        ArrayList<Card> sortedDeck = new ArrayList<Card>();
        ArrayList<Card> shuffledDeck = new ArrayList<Card>();

        Deck d1 = new Deck();
        Deck d2 = new Deck();

        d2.shuffle();

        while (!d1.isEmpty()) {
            sortedDeck.add(d1.nextCard());
            shuffledDeck.add(d2.nextCard());
        }

        Assert.assertFalse(compareOrder(sortedDeck, shuffledDeck));

        Collections.sort(sortedDeck);
        Collections.sort(shuffledDeck);

        for (int i=0; i<52; ++i) {
            Assert.assertEquals(0, sortedDeck.get(i).compareTo(shuffledDeck.get(i)));
        }
    }

    private boolean compareOrder(ArrayList<Card> l1, ArrayList<Card> l2) {
        int equal = 0;
        for (int i=0; i<52; ++i) {
            if (l1.get(i).equals(l2.get(i))) {
                ++equal;
            }
        }

        return equal > 3;
    }
}