package com.crystalplanet.obsidianpoker.app.model;

import junit.framework.TestCase;

public class GameStageTest extends TestCase {

    public void testGameStage() {
        GameStage stage = new GameStage();

        assertEquals(GameStage.INIT, stage.current());

        assertEquals(GameStage.FLOP, stage.next());
        assertEquals(GameStage.FLOP, stage.current());

        assertEquals(GameStage.TURN, stage.next());
        assertEquals(GameStage.TURN, stage.current());

        assertEquals(GameStage.RIVER, stage.next());
        assertEquals(GameStage.RIVER, stage.current());

        assertEquals(GameStage.SHOWDOWN, stage.next());
        assertEquals(GameStage.SHOWDOWN, stage.current());
    }

    public void testFinalStageNextException() {
        GameStage stage = new GameStage();

        String exceptionMessage = null;

        try {
            for (int i = 0; i < 5; ++i) {
                stage.next();
            }
        } catch (RuntimeException e) {
            exceptionMessage = e.getMessage();
        }

        assertEquals("The final GameStage.SHOWDOWN has already been reached!", exceptionMessage);
    }
}
