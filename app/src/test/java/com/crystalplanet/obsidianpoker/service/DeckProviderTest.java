package com.crystalplanet.obsidianpoker.service;


import com.crystalplanet.obsidianpoker.game.card.Card;
import com.crystalplanet.obsidianpoker.game.card.Deck;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class DeckProviderTest {

    @Test
    public void testNewDeck() {
        DeckProvider dp = new DeckProvider();

        Assert.assertTrue(equalCardsOrder(dp.newDeck(), dp.newDeck()));
    }

    @Test
    public void testShuffledDeck() {
        DeckProvider dp = new DeckProvider();

        Assert.assertFalse(equalCardsOrder(dp.newDeck(), dp.shuffledDeck()));
        Assert.assertFalse(equalCardsOrder(dp.shuffledDeck(), dp.shuffledDeck()));
    }

    private boolean equalCardsOrder(Deck l, Deck r) {
        HashMap<Deck, ArrayList<Card>> cards = new HashMap<Deck, ArrayList<Card>>();

        cards.put(l, new ArrayList<Card>());
        cards.put(r, new ArrayList<Card>());

        while (!l.isEmpty()) cards.get(l).add(l.nextCard());
        while (!r.isEmpty()) cards.get(r).add(r.nextCard());

        for (int i=0; i < Math.min(cards.get(l).size(), cards.get(r).size()); ++i)
            if (!cards.get(l).get(i).equals(cards.get(r).get(i)))
                return false;

        return true;
    }

}
