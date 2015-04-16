package com.crystalplanet.obsidianpoker.app.model;

import com.crystalplanet.obsidianpoker.app.model.stages.DealStage;
import com.crystalplanet.obsidianpoker.app.model.stages.FlopStage;

import java.util.ArrayList;
import java.util.List;

public class PokerRound {
    private List<GameObserver> observers = new ArrayList<GameObserver>();

    private RoundStage stage = new DealStage(this);

    private Deck deck;

    private List<Card> commonCards = new ArrayList<Card>();

    private Chips smallBlind;

    private Pot pot = new Pot();

    private Winnings winnings;

    private Pot subPot = new Pot();

    private List<Player> players;

    private Player currentPlayer;

    private Turn turn;

    private boolean initialized = false;

    public PokerRound(
        List<GameObserver> observers,
        List<Player> players,
        Deck deck,
        Chips smallBlind
    ) {
        this.observers = new ArrayList<GameObserver>(observers);
        this.players = new ArrayList<Player>(players);
        this.deck = deck;
        this.smallBlind = smallBlind;
    }

    public List<Player> players() {
        return players;
    }

    public Player dealer() {
        return players.get(players.size() - 1);
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

    public RoundStage currentStage() {
        return stage;
    }

    public Chips smallBlind() {
        return smallBlind;
    }

    public Chips mainPotSize() {
        return new Pot(pot, subPot).size();
    }

    public Chips stagePotSize() {
        return subPot.size();
    }

    public Chips currentBet() {
        return subPot.currentBet();
    }

    public Chips playersBet(Player player) {
        return subPot.playersBet(player);
    }

    public Chips playersWinnings(Player player) {
        return winnings != null ? winnings.playersShare(player) : null;
    }

    public void takeBet(Player player, Chips bet) {
        subPot.takeBet(player, bet);
    }

    public List<Card> commonCards() {
        return commonCards;
    }

    public void run() {
        nextDealer();
        deal();

        nextPlayer().bet(smallBlind);
        nextPlayer().raise(smallBlind.add(smallBlind));

        initialized = true;

        waitForNextPlayerAction();
    }

    public void waitForNextPlayerAction() {
        if (!initialized) return;
        if (currentStage().isOver()) nextStage();
        if (currentStage() == null) {
            showdown();
            return;
        }

        nextPlayer();
        notifyObservers();
        currentPlayer().play();
    }

    private void showdown() {
        winnings = new Winnings(players, commonCards, pot);
    }

    private void nextStage() {
        drawCommonCards((stage = stage.next()) == null ? 0 : stage.equals(new FlopStage(null)) ? 3 : 1);
        aggregatePot();
        resetTurn();
        notifyObservers();
    }

    private void deal() {
        for (int i=0; i<2; ++i)
            for (Player player : players)
                if (player.isActive()) player.drawCard(deck.nextCard());
    }

    private void drawCommonCards(int n) {
        deck.nextCard();
        for (int i=0; i<n; ++i)
            commonCards.add(deck.nextCard());
    }

    private void aggregatePot() {
        pot = new Pot(pot, subPot);
        subPot = new Pot();
    }

    private void notifyObservers() {
        for (GameObserver observer : observers)
            observer.update();
    }

    private void resetTurn() {
        turn = new Turn(players);
        currentPlayer = null;
    }

    private void nextDealer() {
        players.add(players.remove(0));
        resetTurn();
    }

    private Player nextPlayer() {
        return (currentPlayer = turn.hasNext() ? turn.next() : null);
    }
}
