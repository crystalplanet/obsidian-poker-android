package com.crystalplanet.obsidianpoker.game;

import com.crystalplanet.obsidianpoker.game.card.Card;
import com.crystalplanet.obsidianpoker.game.card.Hand;
import com.crystalplanet.obsidianpoker.game.card.HandCombinations;
import com.crystalplanet.obsidianpoker.game.chips.Chips;
import com.crystalplanet.obsidianpoker.game.stage.RoundStage;

import java.util.*;

public class Player {

    private String name;

    private PokerRound round;

    private RoundStage currentStage;

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

    public void joinGame(PokerRound round) {
        this.round = round;
        reset();
        updateGameStage();
    }

    public void reset() {
        cards = new HashSet<Card>();
        checked = false;
    }

    public void play() {
        updateGameStage();
        handler.getNextAction(this);
    }

    public void drawCard(Card card) {
        if (cards.size() > 1) throw new RuntimeException("A player can only hold two cards!");
        cards.add(card);
    }

    public Set<Card> cards() {
        return cards;
    }

    public void addChips(Chips amount) {
        chips = chips.add(amount);
    }

    public Chips chips() {
        return chips;
    }

    public Hand hand(Collection<Card> commonCards) {
        return new HandCombinations(cards, commonCards).bestHand();
    }

    public boolean isActive() {
        return chips != null && chips.compareTo(new Chips(0)) > 0 && !isAllIn();
    }

    public void fold() {
        reset();
        notifyDone();
    }

    public boolean isFolded() {
        return cards.size() != 2;
    }

    public boolean canCheck() {
        return !isFolded() && (round.currentBet().equals(round.playersBet(this)) || isAllIn());
    }

    public void check() {
        if (!canCheck()) throw new RuntimeException("Wrong action! The player cannot check!");

        checked = true;
        notifyDone();
    }

    public boolean hasChecked() {
        return !isFolded() && checked && round.currentStage().equals(currentStage);
    }

    public boolean canCall() {
        return !isFolded();
    }

    public void call() {
        if (!canCall()) throw new RuntimeException("Wrong action! The player cannot call!");
        if (!(round.currentBet().substract(round.playersBet(this)).compareTo(chips) < 0)) {
            allIn();
            return;
        }

        placeBet(round.currentBet().substract(round.playersBet(this)));
        check();
    }

    public boolean canBet() {
        return !isFolded() && round.stagePotSize().equals(new Chips(0)) && !chips.equals(new Chips(0));
    }

    public void bet(Chips bet) {
        if (!canBet()) throw new RuntimeException("Wrong action! The player cannot bet!");
        if (!(new Chips(0).compareTo(bet) < 0) || chips.compareTo(bet) < 0)
            throw new RuntimeException("Invalid bet amount!");

        placeBet(bet);
        check();
    }

    public boolean canRaise() {
        return !isFolded() && round.currentBet().compareTo(chips.add(round.playersBet(this))) < 0;
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
        return !isFolded() && chips.compareTo(new Chips(0)) == 0 && checked;
    }

    private Chips minimumBet() {
        return round.currentBet().add(round.currentBet()).substract(round.playersBet(this));
    }

    private void updateGameStage() {
        currentStage = round.currentStage();
    }

    private void notifyDone() {
        round.waitForNextPlayerAction();
    }

    private void placeBet(Chips bet) {
        chips = chips.substract(bet);
        round.takeBet(this, bet);
    }
}
