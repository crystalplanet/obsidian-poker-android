package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.card.Deck;

public class DeckProvider {

    public Deck newDeck() {
        return new Deck();
    }

    public Deck shuffledDeck() {
        Deck deck = newDeck();
        deck.shuffle();
        return deck;
    }
}
