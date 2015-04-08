package com.crystalplanet.obsidianpoker.app.model;

import com.crystalplanet.obsidianpoker.app.model.stages.DealStage;
import com.crystalplanet.obsidianpoker.app.model.stages.FlopStage;
import com.crystalplanet.obsidianpoker.app.model.stages.RiverStage;
import com.crystalplanet.obsidianpoker.app.model.stages.TurnStage;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameStageTest extends TestCase {

    public void testIsOverAllChecked() {
        GameStage stage = new TestGameStage(prepareGame());

        for (Player player : players) {
            assertFalse(stage.isOver());
            player.check();
        }

        assertTrue(stage.isOver());
    }

    public void testIsOverNotEnoughPlayers() {
        GameStage stage = new TestGameStage(prepareGame());

        players.get(0).fold();

        assertFalse(stage.isOver());

        players.get(1).fold();

        assertTrue(stage.isOver());
    }

    public void testDealStageBigBlindOption() {
        TestGame game = prepareGame();

        GameStage stage = new DealStage(game);

        for (Player player : players) {
            assertFalse(stage.isOver());
            game.nextPlayer();
            player.check();
        }

        assertFalse(stage.isOver());

        game.nextPlayer();

        players.get(1).check();

        assertTrue(stage.isOver());
    }

    public void testNext() {
        GameStage stage = new DealStage(prepareGame());

        assertTrue((stage = stage.next()) instanceof FlopStage);
        assertTrue((stage = stage.next()) instanceof TurnStage);
        assertTrue((stage = stage.next()) instanceof RiverStage);

        assertNull(stage.next());
    }

    public void testStageSkipping() {
        GameStage stages[] = {
            new DealStage(prepareGame()),
            new FlopStage(prepareGame()),
            new TurnStage(prepareGame()),
            new RiverStage(prepareGame())
        };

        players.get(0).fold();
        players.get(1).fold();

        for (GameStage stage : stages)
            assertNull(stage.next());
    }

    private TestGame prepareGame() {
        TestGame game = new TestGame(players);

        for (Player player : players) {
            player.joinGame(game);
            player.drawCard(new Card(CardSuit.SPADES, CardRank.KING));
            player.drawCard(new Card(CardSuit.DIAMONDS, CardRank.QUEEN));
        }

        return game;
    }

    private List<Player> players = new ArrayList<Player>() {{
        add(new Player(null, new Chips(20), null));
        add(new Player(null, new Chips(20), null));
        add(new Player(null, new Chips(20), null));
    }};

    private class TestGame extends PokerGame {
        private int current = 0;

        public TestGame(List<Player> players) {
            super(players, null, null);
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

    private class TestGameStage extends GameStage {
        public TestGameStage(PokerGame game) {
            super(game);
        }

        @Override
        public GameStage next() {
            return null;
        }
    }
}
