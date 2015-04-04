package com.crystalplanet.obsidianpoker.app.model;

import java.util.Collections;
import java.util.Stack;

public class Deck {

    private Stack<Card> cards = new Stack<Card>();

    public Deck() {
        for (CardSuit suit : CardSuit.SUITS)
            for (CardRank rank : CardRank.RANKS)
                cards.push(new Card(suit, rank));
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card nextCard() {
        return cards.pop();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
