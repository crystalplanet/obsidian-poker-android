package com.crystalplanet.obsidianpoker.game.stage;

import com.crystalplanet.obsidianpoker.game.chips.Chips;
import com.crystalplanet.obsidianpoker.game.GameObserver;
import com.crystalplanet.obsidianpoker.game.Player;
import com.crystalplanet.obsidianpoker.game.PokerRound;
import com.crystalplanet.obsidianpoker.game.card.Card;
import com.crystalplanet.obsidianpoker.game.card.CardRank;
import com.crystalplanet.obsidianpoker.game.card.CardSuit;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RoundStageTest extends TestCase {

    public void testIsOverAllChecked() {
        RoundStage stage = new TestRoundStage(prepareGame());

        for (Player player : players) {
            assertFalse(stage.isOver());
            player.check();
        }

        assertTrue(stage.isOver());
    }

    public void testIsOverNotEnoughPlayers() {
        RoundStage stage = new TestRoundStage(prepareGame());

        players.get(0).fold();

        assertFalse(stage.isOver());

        players.get(1).fold();

        assertTrue(stage.isOver());
    }

    public void testDealStageBigBlindOption() {
        TestRound round = prepareGame();

        RoundStage stage = new DealStage(round);

        for (Player player : players) {
            assertFalse(stage.isOver());
            round.nextPlayer();
            player.check();
        }

        assertFalse(stage.isOver());

        round.nextPlayer();

        players.get(1).check();

        assertTrue(stage.isOver());
    }

    public void testNext() {
        RoundStage stage = new DealStage(prepareGame());

        assertTrue((stage = stage.next()) instanceof FlopStage);
        assertTrue((stage = stage.next()) instanceof TurnStage);
        assertTrue((stage = stage.next()) instanceof RiverStage);

        assertNull(stage.next());
    }

    public void testStageSkipping() {
        RoundStage stages[] = {
            new DealStage(prepareGame()),
            new FlopStage(prepareGame()),
            new TurnStage(prepareGame()),
            new RiverStage(prepareGame())
        };

        players.get(0).fold();
        players.get(1).fold();

        for (RoundStage stage : stages)
            assertNull(stage.next());
    }

    private TestRound prepareGame() {
        TestRound round = new TestRound(players);

        for (Player player : players) {
            player.drawCard(new Card(CardSuit.SPADES, CardRank.KING));
            player.drawCard(new Card(CardSuit.DIAMONDS, CardRank.QUEEN));
        }

        return round;
    }

    private List<Player> players = new ArrayList<Player>() {{
        add(new Player(null, new Chips(20), null));
        add(new Player(null, new Chips(20), null));
        add(new Player(null, new Chips(20), null));
    }};

    private class TestRound extends PokerRound {
        private int current = 0;

        public TestRound(List<Player> players) {
            super(new ArrayList<GameObserver>(), players, null, null);
        }

        public Player nextPlayer() {
            return players.get(current = ++current == players.size() ? 0 : current);
        }

        @Override
        public Player currentPlayer() {
            return players.get(current);
        }

        @Override
        public Chips currentBet() {
            return new Chips(10);
        }

        @Override
        public Chips playersBet(Player player) {
            return currentBet();
        }

        @Override
        public void waitForNextPlayerAction()
        {
        }
    }

    private class TestRoundStage extends RoundStage {
        public TestRoundStage(PokerRound round) {
            super(round);
        }

        @Override
        public RoundStage next() {
            return null;
        }
    }
}
