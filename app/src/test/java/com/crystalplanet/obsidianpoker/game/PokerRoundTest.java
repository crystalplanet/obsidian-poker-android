package com.crystalplanet.obsidianpoker.game;

import com.crystalplanet.obsidianpoker.game.card.Card;
import com.crystalplanet.obsidianpoker.game.card.CardRank;
import com.crystalplanet.obsidianpoker.game.card.CardSuit;
import com.crystalplanet.obsidianpoker.game.card.Deck;
import com.crystalplanet.obsidianpoker.game.chips.Chips;
import com.crystalplanet.obsidianpoker.game.stage.DealStage;
import com.crystalplanet.obsidianpoker.game.stage.FlopStage;
import com.crystalplanet.obsidianpoker.game.stage.RiverStage;
import com.crystalplanet.obsidianpoker.game.stage.TurnStage;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokerRoundTest extends TestCase {

    public void testRoundFlow() {
        ArrayList<GameObserver> observers = new ArrayList<GameObserver>(Arrays.asList(observer));

        PokerRound round = new PokerRound(observers, players, new TestDeck(), new Chips(25));

        _testInitialization(round);
        _testFlop(round);
        _testTurn(round);
        _testRiver(round);
        _testFinalBettingRound(round);
        _testShowdown(round);
    }

    private void _testInitialization(PokerRound round) {
        observer.notified = 0;

        round.run();

        assertEquals(1, observer.notified);
        assertEquals(players.get(0), round.dealer());
        assertEquals(new Chips(25), round.smallBlind());

        assertEquals(players.get(3), round.currentPlayer());

        assertEquals(new Chips(75), round.stagePotSize());
        assertEquals(new Chips(75), round.mainPotSize());
        assertEquals(new Chips(50), round.currentBet());
        assertEquals(new Chips(50), round.playersBet(players.get(2)));
        assertEquals(new Chips(25), round.playersBet(players.get(1)));
        assertEquals(new Chips(0), round.playersBet(players.get(0)));
        assertEquals(new Chips(0), round.playersBet(players.get(3)));

        assertTrue(round.commonCards().isEmpty());

        assertEquals(new DealStage(null), round.currentStage());
    }

    private void _testFlop(PokerRound round) {
        handler.takeAction(2);

        assertEquals(players.get(0), round.currentPlayer());

        handler.takeAction(2);

        assertEquals(players.get(1), round.currentPlayer());

        handler.takeAction(2);

        assertEquals(players.get(2), round.currentPlayer());
        assertEquals(new Chips(50), round.currentBet());
        assertEquals(new Chips(200), round.stagePotSize());
        assertEquals(new Chips(200), round.mainPotSize());

        for (Player player : players)
            assertEquals(new Chips(50), round.playersBet(player));

        assertTrue(round.commonCards().isEmpty());
        assertEquals(new DealStage(null), round.currentStage());

        handler.takeAction(1);

        assertEquals(3, round.commonCards().size());

        assertEquals(new Card(CardSuit.HEARTS, CardRank.FIVE), round.commonCards().get(0));
        assertEquals(new Card(CardSuit.DIAMONDS, CardRank.SIX), round.commonCards().get(1));
        assertEquals(new Card(CardSuit.SPADES, CardRank.JACK), round.commonCards().get(2));

        assertEquals(new FlopStage(null), round.currentStage());
        assertEquals(new Chips(0), round.stagePotSize());
        assertEquals(new Chips(200), round.mainPotSize());
        assertEquals(players.get(1), round.currentPlayer());
    }

    private void _testTurn(PokerRound round) {
        handler.takeAction(1);

        assertEquals(players.get(2), round.currentPlayer());

        handler.takeAction(3);

        assertEquals(players.get(3), round.currentPlayer());

        handler.takeAction(0);

        assertEquals(players.get(0), round.currentPlayer());

        handler.takeAction(2);

        assertEquals(new FlopStage(null), round.currentStage());
        assertEquals(3, round.commonCards().size());
        assertEquals(players.get(1), round.currentPlayer());
        assertEquals(new Chips(250), round.mainPotSize());
        assertEquals(new Chips(50), round.stagePotSize());
        assertEquals(new Chips(25), round.playersBet(players.get(0)));
        assertEquals(new Chips(0), round.playersBet(players.get(1)));
        assertEquals(new Chips(25), round.playersBet(players.get(2)));
        assertEquals(new Chips(0), round.playersBet(players.get(3)));

        handler.takeAction(2);

        assertEquals(4, round.commonCards().size());

        assertEquals(new Card(CardSuit.CUBS, CardRank.QUEEN), round.commonCards().get(3));

        assertEquals(new TurnStage(null), round.currentStage());
        assertEquals(new Chips(275), round.mainPotSize());
        assertEquals(new Chips(0), round.stagePotSize());
        assertEquals(players.get(1), round.currentPlayer());
    }

    private void _testRiver(PokerRound round) {
        handler.takeAction(3);

        assertEquals(players.get(2), round.currentPlayer());

        handler.takeAction(2);

        assertEquals(players.get(0), round.currentPlayer());

        handler.takeAction(4);
        handler.takeAction(2);

        assertEquals(new TurnStage(null), round.currentStage());
        assertEquals(4, round.commonCards().size());
        assertEquals(players.get(2), round.currentPlayer());
        assertEquals(new Chips(400), round.mainPotSize());
        assertEquals(new Chips(125), round.stagePotSize());
        assertEquals(new Chips(50), round.playersBet(players.get(0)));
        assertEquals(new Chips(50), round.playersBet(players.get(1)));
        assertEquals(new Chips(25), round.playersBet(players.get(2)));
        assertEquals(new Chips(0), round.playersBet(players.get(3)));

        handler.takeAction(2);

        assertEquals(5, round.commonCards().size());

        assertEquals(new Card(CardSuit.CUBS, CardRank.NINE), round.commonCards().get(4));

        assertEquals(new RiverStage(null), round.currentStage());
        assertEquals(new Chips(425), round.mainPotSize());
        assertEquals(new Chips(0), round.stagePotSize());
        assertEquals(players.get(1), round.currentPlayer());
    }

    private void _testFinalBettingRound(PokerRound round) {
        handler.takeAction(1);
        handler.takeAction(1);

        assertEquals(players.get(0), round.currentPlayer());
        assertEquals(new RiverStage(null), round.currentStage());
        assertEquals(new Chips(425), round.mainPotSize());
        assertEquals(new Chips(0), round.stagePotSize());

        handler.takeAction(1);

        assertEquals(null, round.currentStage());
        assertEquals(null, round.currentPlayer());
        assertEquals(new Chips(0), round.mainPotSize());
    }

    private void _testShowdown(PokerRound round) {
        assertEquals(new Chips(425), round.playersWinnings(players.get(2)));
        assertEquals(new Chips(0), round.playersWinnings(players.get(0)));
        assertEquals(new Chips(0), round.playersWinnings(players.get(1)));
        assertEquals(new Chips(0), round.playersWinnings(players.get(3)));
    }

    private Observer observer = new Observer();

    private Handler handler = new Handler();

    private List<Player> players = new ArrayList<Player>() {{
        add(new Player("A", new Chips(1000), handler));
        add(new Player("B", new Chips(1000), handler));
        add(new Player("C", new Chips(1000), handler));
        add(new Player("D", new Chips(1000), handler));
    }};

    private class Observer implements GameObserver {
        public int notified = 0;

        @Override
        public void update() {
            ++notified;
        }
    }

    private class Handler implements PlayerHandler {
        private Player player;

        @Override
        public void getNextAction(Player player) {
            this.player = player;
        }

        public void takeAction(int action) {
            switch (action) {
                case 5:
                    player.allIn();
                    return;
                case 4:
                    player.raise(new Chips(50));
                    return;
                case 3:
                    player.bet(new Chips(25));
                    return;
                case 2:
                    player.call();
                    return;
                case 1:
                    player.check();
                    return;
                case 0:
                    player.fold();
            }
        }
    }

    private class TestDeck extends Deck {

        private int cursor = 0;

        private List<Card> cards = new ArrayList<Card>() {{
            add(new Card(CardSuit.CUBS, CardRank.THREE));
            add(new Card(CardSuit.HEARTS, CardRank.TEN));
            add(new Card(CardSuit.CUBS, CardRank.JACK));
            add(new Card(CardSuit.DIAMONDS, CardRank.TWO));
            add(new Card(CardSuit.HEARTS, CardRank.FOUR));
            add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
            add(new Card(CardSuit.HEARTS, CardRank.JACK));
            add(new Card(CardSuit.DIAMONDS, CardRank.SEVEN));
            add(new Card(CardSuit.SPADES, CardRank.ACE)); // spacer
            add(new Card(CardSuit.HEARTS, CardRank.FIVE));
            add(new Card(CardSuit.DIAMONDS, CardRank.SIX));
            add(new Card(CardSuit.SPADES, CardRank.JACK));
            add(new Card(CardSuit.DIAMONDS, CardRank.JACK)); // spacer
            add(new Card(CardSuit.CUBS, CardRank.QUEEN));
            add(new Card(CardSuit.DIAMONDS, CardRank.KING)); // spacer
            add(new Card(CardSuit.CUBS, CardRank.NINE));
            add(new Card(CardSuit.CUBS, CardRank.TWO)); // ...
        }};

        @Override
        public Card nextCard() {
            return cards.get(cursor++);
        }
    }
}
