package com.crystalplanet.obsidianpoker.app.model;

import java.util.*;

public class Player {

    private String name;

    private PokerGame game;

    private PlayerHandler handler;

    private Chips chips;

    private Set<Card> cards;

    private boolean checked;

    public Player(String name, Chips chips, PlayerHandler handler) {
        this.name = name;
        this.chips = chips;
        this.handler = handler;

        reset();
    }

    @Override
    public String toString() {
        return name;
    }

    public void joinGame(PokerGame game) {
        this.game = game;
        reset();
    }

    public void reset() {
        cards = new HashSet<Card>();
    }

    public void play() {
        handler.getNextAction(this);
    }

    public void takeCard(Card card) {
        if (cards.size() > 1) throw new RuntimeException("A player can only hold two cards!");
        cards.add(card);
    }

    public Set<Card> cards() {
        return cards;
    }

    public Chips chips() {
        return chips;
    }

    public Hand hand(Collection<Card> commonCards) {
        return new HandCombinations(cards, commonCards).bestHand();
    }

    public boolean isActive() {
        return chips.compareTo(new Chips(0)) > 0 && !isAllIn();
    }

    public void fold() {
        reset();
        notifyDone();
    }

    public boolean isFolded() {
        return cards.size() != 2;
    }

    public boolean canCheck() {
        return !isFolded() && (game.currentBet().equals(game.playersBet(this)) || isAllIn());
    }

    public void check() {
        if (!canCheck()) throw new RuntimeException("Wrong action! The player cannot check!");

        checked = true;
        notifyDone();
    }

    public boolean hasChecked() {
        return checked;
    }

    public boolean canCall() {
        return !isFolded();
    }

    public void call() {
        if (!canCall()) throw new RuntimeException("Wrong action! The player cannot call!");
        if (!(game.currentBet().substract(game.playersBet(this)).compareTo(chips) < 0)) {
            allIn();
            return;
        }

        placeBet(game.currentBet().substract(game.playersBet(this)));
        check();
    }

    public boolean canBet() {
        return !isFolded() && game.roundPotSize().equals(new Chips(0)) && !chips.equals(new Chips(0));
    }

    public void bet(Chips bet) {
        if (!canBet()) throw new RuntimeException("Wrong action! The player cannot bet!");
        if (!(new Chips(0).compareTo(bet) < 0) || chips.compareTo(bet) < 0)
            throw new RuntimeException("Invalid bet amount!");

        placeBet(bet);
        check();
    }

    public boolean canRaise() {
        return !isFolded() && game.currentBet().compareTo(chips.add(game.playersBet(this))) < 0;
    }

    public void raise(Chips raise) {
        if (!canRaise()) throw new RuntimeException("Wrong action! The player cannot raise!");
        if ((raise.compareTo(minimumBet()) < 0 && !raise.equals(chips)) || chips.compareTo(raise) < 0)
            throw new RuntimeException("Invalid raise amount!");

        placeBet(raise);
        check();
    }

    public boolean canAllIn() {
        return !(isFolded() || isAllIn());
    }

    public void allIn() {
        if (!canAllIn()) throw new RuntimeException("Wrong action! The player cannot go all in!");

        placeBet(chips);
        check();
    }

    public boolean isAllIn() {
        return chips.compareTo(new Chips(0)) == 0 && checked;
    }

    private Chips minimumBet() {
        return game.currentBet().add(game.currentBet()).substract(game.playersBet(this));
    }

    private void notifyDone() {
        if (game != null) game.waitForNextPlayerAction();
    }

    private void placeBet(Chips bet) {
        chips = chips.substract(bet);
        game.takeBet(this, bet);
    }
}
