package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.TestCase;

import java.util.HashSet;

public class PlayerTest extends TestCase {

    public void testToString() {
        Player player = new Player("SomePlayerName", null, null);

        assertEquals("SomePlayerName", player.toString());
    }

    public void testPlay() {
        testPlayer = null;

        Player player = new Player(null, null, testHandler);

        player.joinGame(new TestGame(null, null));
        player.play();

        assertEquals(player, testPlayer);
    }

    public void testCards() {
        Player player = new Player(null, null, null);

        for (Card card : playersCards)
            player.drawCard(card);

        assertEquals(playersCards, player.cards());
    }

    public void testAddCardsException() {
        String exceptionMessage = "";

        Player player = new Player(null, null, null);

        for (Card card : playersCards)
            player.drawCard(card);

        try {
            player.drawCard(new Card(CardSuit.CUBS, CardRank.SEVEN));
        } catch (RuntimeException e) {
            exceptionMessage = e.getMessage();
        }

        assertEquals("A player can only hold two cards!", exceptionMessage);
    }

    public void testReset() {
        Player player = new Player(null, null, null);

        player.drawCard(new Card(CardSuit.CUBS, CardRank.SEVEN));

        assertEquals(1, player.cards().size());

        player.reset();

        assertEquals(0, player.cards().size());
    }

    public void testHand() {
        Player player = new Player(null, null, null);

        for (Card card : playersCards) player.drawCard(card);

        assertEquals(new Hand(bestHand), player.hand(commonCards));
    }

    public void testFold() {
        TestGame game = new TestGame(null, null);

        Player player = new Player(null, null, null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.isFolded());
        assertEquals(2, player.cards().size());

        player.fold();

        assertTrue(game.notified);
        assertTrue(player.isFolded());
        assertEquals(0, player.cards().size());
    }

    public void testCheck() {
        TestGame game = new TestGame(new Chips(20), new Chips(20));

        Player player = new Player(null, null, null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        assertTrue(player.canCheck());
        assertFalse(player.hasChecked());

        player.check();

        assertTrue(game.notified);
        assertTrue(player.hasChecked());
    }

    public void testCheckException() {
        int exceptions = 0;
        String exceptionMsg = "Wrong action! The player cannot check!";

        TestGame game1 = new TestGame(new Chips(20), new Chips(10));
        TestGame game2 = new TestGame(new Chips(20), new Chips(20));

        Player players[] = {
            new Player(null, new Chips(100), null),
            new Player(null, new Chips(100), null)
        };

        players[0].joinGame(game1);
        players[1].joinGame(game2);

        for (Card card : playersCards)
            players[0].drawCard(card);

        for (Player player : players) {
            try {
                player.check();
            } catch (RuntimeException e) {
                ++exceptions;
                assertEquals(exceptionMsg, e.getMessage());
            }

            assertFalse(player.hasChecked());
        }

        assertFalse(game1.notified);
        assertFalse(game2.notified);
        assertEquals(2, exceptions);
    }

    public void testCall() {
        TestGame game = new TestGame(new Chips(20), new Chips(10));

        Player player = new Player(null, new Chips(11), null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());

        player.call();

        assertTrue(game.notified);
        assertTrue(player.hasChecked());
        assertEquals(new Chips(20), game.playersBet(player));
        assertEquals(new Chips(1), player.chips());
    }

    public void testCallWithAllIn() {
        TestGame game = new TestGame(new Chips(20), new Chips(10));

        Player player = new Player(null, new Chips(10), null);

        player.joinGame(game);
        for(Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());
        assertFalse(player.isAllIn());

        player.call();

        assertTrue(game.notified);
        assertTrue(player.hasChecked());
        assertTrue(player.isAllIn());
        assertEquals(new Chips(20), game.playersBet(player));
        assertEquals(new Chips(0), player.chips());
    }

    public void testCallException() {
        String exception = "";

        TestGame game = new TestGame(null, null);

        Player player = new Player(null, null, null);

        player.joinGame(game);

        try {
            player.call();
        } catch (RuntimeException e) {
            exception = e.getMessage();
        }

        assertFalse(game.notified);
        assertEquals("Wrong action! The player cannot call!", exception);
    }

    public void testBet() {
        TestGame game = new TestGame(new Chips(0), new Chips(0));

        Player player = new Player(null, new Chips(100), null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());

        player.bet(new Chips(20));

        assertTrue(game.notified);
        assertTrue(player.hasChecked());
        assertEquals(new Chips(20), game.playersBet(player));
        assertEquals(new Chips(80), player.chips());
    }

    public void testBetAllIn() {
        TestGame game = new TestGame(new Chips(0), new Chips(0));

        Player player = new Player(null, new Chips(100), null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());
        assertFalse(player.isAllIn());

        player.bet(new Chips(100));

        assertTrue(game.notified);
        assertTrue(player.hasChecked());
        assertTrue(player.isAllIn());
        assertEquals(new Chips(100), game.playersBet(player));
        assertEquals(new Chips(0), player.chips());
    }

    public void testBetWrongActionException() {
        int exceptions = 0;
        String exceptionMsg = "Wrong action! The player cannot bet!";

        TestGame game1 = new TestGame(new Chips(10), null);
        TestGame game2 = new TestGame(new Chips(0), null);

        Player players[] = {
            new Player("1", new Chips(100), null),
            new Player("2", new Chips(0), null),
            new Player("3", new Chips(100), null)
        };

        players[0].joinGame(game1);
        players[1].joinGame(game2);
        players[2].joinGame(game1);

        for (Player player : players)
            if (player != players[2])
                for (Card card : playersCards)
                    player.drawCard(card);

        for (Player player : players) {
            try {
                player.bet(new Chips(10));
            } catch (RuntimeException e) {
                assertEquals(exceptionMsg, e.getMessage());

                ++exceptions;
            }
        }

        assertFalse(game1.notified);
        assertFalse(game2.notified);
        assertEquals(3, exceptions);
    }

    public void testBetInvalidAmountException() {
        int exceptions = 0;
        String exceptionMsg = "Invalid bet amount!";

        TestGame game = new TestGame(new Chips(0), null);

        Player player = new Player(null, new Chips(100), null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        int amounts[] = {0, 101};

        for (int amount : amounts) {
            try {
                player.bet(new Chips(amount));
            } catch (RuntimeException e) {
                assertEquals(exceptionMsg, e.getMessage());

                ++exceptions;
            }
        }

        assertFalse(game.notified);
        assertEquals(2, exceptions);
    }

    public void testRaise() {
        TestGame game = new TestGame(new Chips(20), new Chips(10));

        Player player = new Player(null, new Chips(100), null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());

        player.raise(new Chips(30));

        assertTrue(game.notified);
        assertTrue(player.hasChecked());
        assertEquals(new Chips(40), game.playersBet(player));
        assertEquals(new Chips(70), player.chips());
    }

    public void testRaiseAllIn() {
        TestGame game = new TestGame(new Chips(20), new Chips(10));

        Player player = new Player(null, new Chips(20), null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());
        assertFalse(player.isAllIn());

        player.raise(new Chips(20));

        assertTrue(game.notified);
        assertTrue(player.hasChecked());
        assertTrue(player.isAllIn());
        assertEquals(new Chips(30), game.playersBet(player));
        assertEquals(new Chips(0), player.chips());
    }

    public void testRaiseWrongActionException() {
        int exceptions = 0;
        String exceptionMsg = "Wrong action! The player cannot raise!";

        TestGame game = new TestGame(new Chips(20), new Chips(0));

        Player players[] = {
            new Player(null, new Chips(20), null),
            new Player(null, new Chips(30), null)
        };

        players[0].joinGame(game);
        players[1].joinGame(game);
        for (Card card : playersCards)
            players[0].drawCard(card);

        for (Player player : players) {
            try {
                player.raise(new Chips(20));
            } catch (RuntimeException e) {
                assertEquals(exceptionMsg, e.getMessage());

                ++exceptions;
            }
        }

        assertFalse(game.notified);
        assertEquals(2, exceptions);
    }

    public void testRaiseInvalidAmountException() {
        int exceptions = 0;
        String exceptionMsg = "Invalid raise amount!";

        TestGame game = new TestGame(new Chips(20), new Chips(0));

        Player player = new Player(null, new Chips(50), null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        int amounts[] = {10, 60};

        for (int amount : amounts) {
            try {
                player.raise(new Chips(amount));
            } catch (RuntimeException e) {
                assertEquals(exceptionMsg, e.getMessage());

                ++exceptions;
            }
        }

        assertFalse(game.notified);
        assertEquals(2, exceptions);
    }

    public void testAllIn() {
        TestGame game = new TestGame(new Chips(0), new Chips(0));

        Player player = new Player(null, new Chips(20), null);

        player.joinGame(game);
        for (Card card : playersCards)
            player.drawCard(card);

        player.allIn();

        assertTrue(game.notified);
        assertTrue(player.hasChecked());
        assertTrue(player.isAllIn());
        assertEquals(new Chips(20), game.playersBet(player));
        assertEquals(new Chips(0), player.chips());
    }

    public void testAllInWrongActionException() {
        int exceptions = 0;
        String exceptionMessage = "Wrong action! The player cannot go all in!";

        TestGame game = new TestGame(new Chips(0), new Chips(0));

        Player player = new Player(null, new Chips(30), null);

        player.joinGame(game);

        try {
            player.allIn();
        } catch (RuntimeException e) {
            assertEquals(exceptionMessage, e.getMessage());

            ++exceptions;
        }

        for (Card card : playersCards)
            player.drawCard(card);

        player.allIn();

        try {
            player.allIn();
        } catch (RuntimeException e) {
            assertEquals(exceptionMessage, e.getMessage());

            ++exceptions;
        }

        assertEquals(2, exceptions);
    }

    private Player testPlayer;

    private PlayerHandler testHandler = new PlayerHandler() {
        @Override
        public void getNextAction(Player player) {
            testPlayer = player;
        }
    };

    private HashSet<Card> playersCards = new HashSet<Card>() {{
        add(new Card(CardSuit.CUBS, CardRank.ACE));
        add(new Card(CardSuit.SPADES, CardRank.THREE));
    }};

    private HashSet<Card> commonCards = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.KING));
        add(new Card(CardSuit.SPADES, CardRank.KING));
        add(new Card(CardSuit.HEARTS, CardRank.FIVE));
        add(new Card(CardSuit.SPADES, CardRank.FOUR));
        add(new Card(CardSuit.DIAMONDS, CardRank.TWO));
    }};

    private HashSet<Card> bestHand = new HashSet<Card>() {{
        add(new Card(CardSuit.HEARTS, CardRank.FIVE));
        add(new Card(CardSuit.SPADES, CardRank.FOUR));
        add(new Card(CardSuit.SPADES, CardRank.THREE));
        add(new Card(CardSuit.DIAMONDS, CardRank.TWO));
        add(new Card(CardSuit.CUBS, CardRank.ACE));
    }};

    private class TestGame extends PokerGame {

        public boolean notified = false;

        private Chips currentBet;
        private Chips playersBet;

        public TestGame(Chips currentBet, Chips playersBet) {
            super(null, null);

            this.currentBet = currentBet;
            this.playersBet = playersBet;
        }

        @Override
        public void waitForNextPlayerAction() {
            notified = true;
        }

        @Override
        public Chips roundPotSize() {
            return currentBet;
        }

        @Override
        public Chips currentBet() {
            return currentBet;
        }

        @Override
        public Chips playersBet(Player player) {
            return playersBet;
        }

        @Override
        public void takeBet(Player player, Chips chips) {
            playersBet = playersBet.add(chips);

            if (playersBet.compareTo(currentBet) > 0)
                currentBet = playersBet;
        }
    }
}
