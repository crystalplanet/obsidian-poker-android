package com.crystalplanet.obsidianpoker.service;

import com.crystalplanet.obsidianpoker.game.GameObserver;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GameObserverManagerTest {

    @Test
    public void testManageObservers() {
        ArrayList<Observer> observers = new ArrayList<Observer>() {{
            add(new Observer());
            add(new Observer());
            add(new Observer());
        }};

        GameObserverManager om = new GameObserverManager();

        Assert.assertEquals(new ArrayList<GameObserver>(), om.getAllObservers());

        for (Observer observer : observers)
            om.add(observer);

        Assert.assertEquals(observers, om.getAllObservers());

        om.remove(observers.get(0));

        Assert.assertEquals(observers.subList(1,3), om.getAllObservers());

        om.add(observers.get(1));

        Assert.assertEquals(observers.subList(1, 3), om.getAllObservers());
    }

    private class Observer implements GameObserver {

        @Override
        public void update() {

        }
    }
}
