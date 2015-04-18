package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.PokerRound;

public class PokerGame {

    GameObserverManager observerManager;

    PlayerManager playerManager;

    DeckProvider deckProvider;

    GameConfiguration gameConfiguration;

    PokerRoundFactory roundFactory;

    PokerRound currentRound;

    public PokerGame(
        PokerRoundFactory roundFactory,
        GameConfiguration gameConfiguration,
        DeckProvider deckProvider,
        PlayerManager playerManager,
        GameObserverManager observerManager
    ) {
        this.observerManager = observerManager;
        this.playerManager = playerManager;
        this.deckProvider = deckProvider;
        this.gameConfiguration = gameConfiguration;
        this.roundFactory = roundFactory;
    }

    public PokerRound currentRound() {
        return currentRound;
    }

    public PokerRound nextRound() {
        return (currentRound = roundFactory.newRound(
            observerManager.getAllObservers(),
            currentRound != null ? currentRound.players() : playerManager.getPlayers(),
            deckProvider.shuffledDeck(),
            gameConfiguration.getSmallBlind()
        ));
    }
}
