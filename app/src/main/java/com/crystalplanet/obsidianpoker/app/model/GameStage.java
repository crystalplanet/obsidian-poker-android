package com.crystalplanet.obsidianpoker.app.model;

public class GameStage {

    public static final int INIT = 1;
    public static final int FLOP = 2;
    public static final int TURN = 3;
    public static final int RIVER = 4;
    public static final int SHOWDOWN = 5;

    private int stage;

    public GameStage() {
        stage = INIT;
    }

    public int current() {
        return stage;
    }

    public int next() {
        if (stage == SHOWDOWN) throw new RuntimeException("The final GameStage.SHOWDOWN has already been reached!");

        return ++stage;
    }
}
