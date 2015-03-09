package com.crystalplanet.obsidianpoker.app.model;

import java.util.*;

public class HandsCombination implements Iterable<Hand> {

    private Set<Hand> hands = new HashSet<Hand>();

    public HandsCombination(Collection<Card>... cardCollections) {
        List<Card> cards = new ArrayList<Card>();
        for (Collection<Card> cardCollection : cardCollections)
            cards.addAll(cardCollection);

        hands = handCombinations(cards);
    }

    @Override
    public Iterator<Hand> iterator() {
        return hands.iterator();
    }

    private Set<Hand> handCombinations(List<Card> cards) {
        if (cards.size() == 5) return new HashSet<Hand>(Arrays.asList(new Hand(new HashSet<Card>(cards))));

        Set<Hand> hands = new HashSet<Hand>();
        for (Card card : cards) {
            List<Card> tmp = new ArrayList<Card>(cards);
            tmp.remove(card);
            hands.addAll(handCombinations(tmp));
        }

        return hands;
    }
}
