package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Stack;

public class TurnsTest extends TestCase {

    public void testActiveIterator() {
        Stack<Player> playersStack = new Stack<Player>();

        for (Player player : players)
            playersStack.push(player);

        int count = 0;

        for (Player player : new Turns(players)) {
            if (player == playersStack.peek()) {
                playersStack.pop().sitOut();
            }

            ++count;
        }

        assertEquals(14, count);
    }

    private ArrayList<Player> players = new ArrayList<Player>() {{
        add(new Player());
        add(new Player());
        add(new Player());
        add(new Player());
        add(new Player());
    }};
}
