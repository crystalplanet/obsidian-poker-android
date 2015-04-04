package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Stack;

public class TurnsTest extends TestCase {

    public void testActiveIterator() {
        Stack<Player> playersStack = new Stack<Player>();

        PokerGame game = new TestGame();

        for (Player player : players) {
            player.joinGame(game);
            player.drawCard(new Card(CardSuit.SPADES, CardRank.KING));
            player.drawCard(new Card(CardSuit.DIAMONDS, CardRank.NINE));
            playersStack.push(player);
        }

        int count = 0;

        for (Player player : new Turns(players)) {
            assertNotNull(player);

            if (player == playersStack.peek())
                playersStack.pop().fold();

            ++count;
        }

        assertEquals(14, count);
    }

    private ArrayList<Player> players = new ArrayList<Player>() {{
        add(new Player(null, new Chips(10), null));
        add(new Player(null, new Chips(10), null));
        add(new Player(null, new Chips(10), null));
        add(new Player(null, new Chips(10), null));
        add(new Player(null, new Chips(10), null));
    }};

    private class TestGame extends PokerGame {
        public TestGame() {
            super(null, null);
        }

        @Override
        public void waitForNextPlayerAction() {
        }
    }
}
