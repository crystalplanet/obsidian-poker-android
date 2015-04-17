package com.crystalplanet.obsidianpoker.game;

import com.crystalplanet.obsidianpoker.game.card.Card;
import com.crystalplanet.obsidianpoker.game.card.CardRank;
import com.crystalplanet.obsidianpoker.game.card.CardSuit;
import com.crystalplanet.obsidianpoker.game.card.Hand;
import com.crystalplanet.obsidianpoker.game.chips.Chips;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashSet;

public class PlayerTest extends TestCase {

    public void testToString() {
        Player player = new Player("SomePlayerName", null, null);

        assertEquals("SomePlayerName", player.toString());
    }

    public void testPlay() {
        testPlayer = null;

        Player player = new Player(null, null, testHandler);

        player.setCurrentRound(new TestRound(null, null));
        player.play();

        assertEquals(player, testPlayer);
    }

    public void testCards() {
        Player player = new Player(null, null, null);

        for (Card card : playersCards)
            player.drawCard(card);

        assertEquals(playersCards, player.cards());
    }

    public void testChips() {
        Player player = new Player(null, new Chips(50), null);

        assertEquals(new Chips(50), player.chips());

        player.addChips(new Chips(50));

        assertEquals(new Chips(100), player.chips());
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
        TestRound round = new TestRound(null, null);

        Player player = new Player(null, null, null);

        player.setCurrentRound(round);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.isFolded());
        assertEquals(2, player.cards().size());

        player.fold();

        assertTrue(round.notified);
        assertTrue(player.isFolded());
        assertEquals(0, player.cards().size());
    }

    public void testCheck() {
        TestRound round = new TestRound(new Chips(20), new Chips(20));

        Player player = new Player(null, null, null);

        player.setCurrentRound(round);
        for (Card card : playersCards)
            player.drawCard(card);

        assertTrue(player.canCheck());
        assertFalse(player.hasChecked());

        player.check();

        assertTrue(round.notified);
        assertTrue(player.hasChecked());
    }

    public void testCheckException() {
        int exceptions = 0;
        String exceptionMsg = "Wrong action! The player cannot check!";

        TestRound round1 = new TestRound(new Chips(20), new Chips(10));
        TestRound round2 = new TestRound(new Chips(20), new Chips(20));

        Player players[] = {
            new Player(null, new Chips(100), null),
            new Player(null, new Chips(100), null)
        };

        players[0].setCurrentRound(round1);
        players[1].setCurrentRound(round2);

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

        assertFalse(round1.notified);
        assertFalse(round2.notified);
        assertEquals(2, exceptions);
    }

    public void testCall() {
        TestRound round = new TestRound(new Chips(20), new Chips(10));

        Player player = new Player(null, new Chips(11), null);

        player.setCurrentRound(round);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());

        player.call();

        assertTrue(round.notified);
        assertTrue(player.hasChecked());
        assertEquals(new Chips(20), round.playersBet(player));
        assertEquals(new Chips(1), player.chips());
    }

    public void testCallWithAllIn() {
        TestRound round = new TestRound(new Chips(20), new Chips(10));

        Player player = new Player(null, new Chips(10), null);

        player.setCurrentRound(round);
        for(Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());
        assertFalse(player.isAllIn());

        player.call();

        assertTrue(round.notified);
        assertTrue(player.hasChecked());
        assertTrue(player.isAllIn());
        assertEquals(new Chips(20), round.playersBet(player));
        assertEquals(new Chips(0), player.chips());
    }

    public void testCallException() {
        String exception = "";

        TestRound round = new TestRound(null, null);

        Player player = new Player(null, null, null);

        player.setCurrentRound(round);

        try {
            player.call();
        } catch (RuntimeException e) {
            exception = e.getMessage();
        }

        assertFalse(round.notified);
        assertEquals("Wrong action! The player cannot call!", exception);
    }

    public void testBet() {
        TestRound round = new TestRound(new Chips(0), new Chips(0));

        Player player = new Player(null, new Chips(100), null);

        player.setCurrentRound(round);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());

        player.bet(new Chips(20));

        assertTrue(round.notified);
        assertTrue(player.hasChecked());
        assertEquals(new Chips(20), round.playersBet(player));
        assertEquals(new Chips(80), player.chips());
    }

    public void testBetAllIn() {
        TestRound round = new TestRound(new Chips(0), new Chips(0));

        Player player = new Player(null, new Chips(100), null);

        player.setCurrentRound(round);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());
        assertFalse(player.isAllIn());

        player.bet(new Chips(100));

        assertTrue(round.notified);
        assertTrue(player.hasChecked());
        assertTrue(player.isAllIn());
        assertEquals(new Chips(100), round.playersBet(player));
        assertEquals(new Chips(0), player.chips());
    }

    public void testBetWrongActionException() {
        int exceptions = 0;
        String exceptionMsg = "Wrong action! The player cannot bet!";

        TestRound round1 = new TestRound(new Chips(10), null);
        TestRound round2 = new TestRound(new Chips(0), null);

        Player players[] = {
            new Player("1", new Chips(100), null),
            new Player("2", new Chips(0), null),
            new Player("3", new Chips(100), null)
        };

        players[0].setCurrentRound(round1);
        players[1].setCurrentRound(round2);
        players[2].setCurrentRound(round1);

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

        assertFalse(round1.notified);
        assertFalse(round2.notified);
        assertEquals(3, exceptions);
    }

    public void testBetInvalidAmountException() {
        int exceptions = 0;
        String exceptionMsg = "Invalid bet amount!";

        TestRound round = new TestRound(new Chips(0), null);

        Player player = new Player(null, new Chips(100), null);

        player.setCurrentRound(round);
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

        assertFalse(round.notified);
        assertEquals(2, exceptions);
    }

    public void testRaise() {
        TestRound round = new TestRound(new Chips(20), new Chips(10));

        Player player = new Player(null, new Chips(100), null);

        player.setCurrentRound(round);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());

        player.raise(new Chips(30));

        assertTrue(round.notified);
        assertTrue(player.hasChecked());
        assertEquals(new Chips(40), round.playersBet(player));
        assertEquals(new Chips(70), player.chips());
    }

    public void testRaiseAllIn() {
        TestRound round = new TestRound(new Chips(20), new Chips(10));

        Player player = new Player(null, new Chips(20), null);

        player.setCurrentRound(round);
        for (Card card : playersCards)
            player.drawCard(card);

        assertFalse(player.hasChecked());
        assertFalse(player.isAllIn());

        player.raise(new Chips(20));

        assertTrue(round.notified);
        assertTrue(player.hasChecked());
        assertTrue(player.isAllIn());
        assertEquals(new Chips(30), round.playersBet(player));
        assertEquals(new Chips(0), player.chips());
    }

    public void testRaiseWrongActionException() {
        int exceptions = 0;
        String exceptionMsg = "Wrong action! The player cannot raise!";

        TestRound round = new TestRound(new Chips(20), new Chips(0));

        Player players[] = {
            new Player(null, new Chips(20), null),
            new Player(null, new Chips(30), null)
        };

        players[0].setCurrentRound(round);
        players[1].setCurrentRound(round);
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

        assertFalse(round.notified);
        assertEquals(2, exceptions);
    }

    public void testRaiseInvalidAmountException() {
        int exceptions = 0;
        String exceptionMsg = "Invalid raise amount!";

        TestRound round = new TestRound(new Chips(20), new Chips(0));

        Player player = new Player(null, new Chips(50), null);

        player.setCurrentRound(round);
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

        assertFalse(round.notified);
        assertEquals(2, exceptions);
    }

    public void testAllIn() {
        TestRound round = new TestRound(new Chips(0), new Chips(0));

        Player player = new Player(null, new Chips(20), null);

        player.setCurrentRound(round);
        for (Card card : playersCards)
            player.drawCard(card);

        player.allIn();

        assertTrue(round.notified);
        assertTrue(player.hasChecked());
        assertTrue(player.isAllIn());
        assertEquals(new Chips(20), round.playersBet(player));
        assertEquals(new Chips(0), player.chips());
    }

    public void testAllInWrongActionException() {
        int exceptions = 0;
        String exceptionMessage = "Wrong action! The player cannot go all in!";

        TestRound round = new TestRound(new Chips(0), new Chips(0));

        Player player = new Player(null, new Chips(30), null);

        player.setCurrentRound(round);

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

    private class TestRound extends PokerRound {

        public boolean notified = false;

        private Chips currentBet;
        private Chips playersBet;

        public TestRound(Chips currentBet, Chips playersBet) {
            super(new ArrayList<GameObserver>(), new ArrayList<Player>(), null, null);

            this.currentBet = currentBet;
            this.playersBet = playersBet;
        }

        @Override
        public void waitForNextPlayerAction() {
            notified = true;
        }

        @Override
        public Chips stagePotSize() {
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
