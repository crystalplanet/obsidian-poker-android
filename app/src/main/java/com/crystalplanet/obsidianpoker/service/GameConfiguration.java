package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.chips.Chips;

public class GameConfiguration {

    private Chips smallBlind;

    public void setSmallBlind(Chips smallBlind) {
        this.smallBlind = smallBlind;
    }

    public Chips getSmallBlind() {
        return smallBlind;
    }
}