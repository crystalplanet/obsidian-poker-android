package com.crystalplanet.obsidianpoker.game.card;

import java.util.*;

public class HandCombinations implements Iterable<Hand> {

    private Set<Hand> hands = new HashSet<Hand>();

    public HandCombinations(Collection<Card>... cardCollections) {
        List<Card> cards = new ArrayList<Card>();
        for (Collection<Card> cardCollection : cardCollections)
            cards.addAll(cardCollection);

        hands = handCombinations(cards);
    }

    public Hand bestHand() {
        SortedSet<Hand> sortedHands = new TreeSet<Hand>(hands);
        return sortedHands.last();
    }

    @Override
    public Iterator<Hand> iterator() {
        return hands.iterator();
    }

    private Set<Hand> handCombinations(List<Card> cards) {
        if (cards.size() == 5) return new HashSet<Hand>(Arrays.asList(new Hand(new HashSet<Card>(cards))));

        Set<Hand> hands = new HashSet<Hand>();
        List<Card> tmp;
        for (Card card : cards) {
            (tmp = new ArrayList<Card>(cards)).remove(card);
            hands.addAll(handCombinations(tmp));
        }

        return hands;
    }
}
