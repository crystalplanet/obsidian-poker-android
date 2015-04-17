package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.*;
import com.crystalplanet.obsidianpoker.game.card.Deck;
import com.crystalplanet.obsidianpoker.game.chips.Chips;

import java.util.List;

public class PokerRoundFactory {

    public PokerRound newRound(List<GameObserver> observers, List<Player> players, Deck deck, Chips smallBlind) {
        return new PokerRound(observers, players, deck, smallBlind);
    }
}
