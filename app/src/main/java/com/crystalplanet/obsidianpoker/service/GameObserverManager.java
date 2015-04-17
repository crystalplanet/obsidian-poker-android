package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class GameObserverManager {

    private List<GameObserver> observers = new ArrayList<GameObserver>();

    public void add(GameObserver observer) {
        if (!observers.contains(observer)) observers.add(observer);
    }

    public void remove(GameObserver observer) {
        observers.remove(observer);
    }

    public List<GameObserver> getAllObservers() {
        return observers;
    }
}
