package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Stack;

public class TurnTest extends TestCase {

    public void testActiveIterator() {
        Stack<Player> playersStack = new Stack<Player>();

        TestRound round = new TestRound();

        for (Player player : players) {
            player.joinGame(round);
            player.drawCard(new Card(CardSuit.SPADES, CardRank.KING));
            player.drawCard(new Card(CardSuit.DIAMONDS, CardRank.NINE));
            playersStack.push(player);
        }

        int count = 0;

        Turn turn = new Turn(players);

        while (turn.hasNext()) {
            Player player = turn.next();

            Assert.assertNotNull(player);

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

    private class TestRound extends PokerRound {
        public TestRound() {
            super(new ArrayList<GameObserver>(), new ArrayList<Player>(), null, null);
        }

        @Override
        public void waitForNextPlayerAction() {
        }
    }
}
