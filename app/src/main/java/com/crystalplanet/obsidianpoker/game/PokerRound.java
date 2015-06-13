package com.crystalplanet.obsidianpoker.game;

import com.crystalplanet.obsidianpoker.game.card.Card;
import com.crystalplanet.obsidianpoker.game.card.Deck;
import com.crystalplanet.obsidianpoker.game.chips.Chips;
import com.crystalplanet.obsidianpoker.game.chips.Pot;
import com.crystalplanet.obsidianpoker.game.chips.Winnings;
import com.crystalplanet.obsidianpoker.game.stage.DealStage;
import com.crystalplanet.obsidianpoker.game.stage.FlopStage;
import com.crystalplanet.obsidianpoker.game.stage.RoundStage;

import java.util.ArrayList;
import java.util.Iterator;
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
        this.deck = deck;
        this.smallBlind = smallBlind;

        for (Player player : this.players = new ArrayList<Player>(players))
            player.setCurrentRound(this);
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
            notifyObservers();
            return;
        }

        nextPlayer();
        notifyObservers();
        currentPlayer().play();
    }

    public void destroy() {
        if (winnings != null) winnings.payOut();
    }

    private void showdown() {
        winnings = playersFolded(players.iterator()) == players.size() - 1
            ? new Winnings(getLastManStanding(), pot)
            : new Winnings(players, commonCards, pot);
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

    private int playersFolded(Iterator<Player> it) {
        return it.hasNext() ? (it.next().isFolded() ? 1 : 0) + playersFolded(it) : 0;
    }

    private Player getLastManStanding() {
        for (Player player : players)
            if (!player.isFolded()) return player;

        return null;
    }
}
