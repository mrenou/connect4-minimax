package com.xebia.xke.algo.minimax.connect4;

public class PlayerScored {

    private PlayerLoader playerLoader;

    private Integer score = 0;


    public PlayerScored(PlayerLoader playerLoader) {
        this.playerLoader = playerLoader;
    }

    public PlayerLoader getPlayerLoader() {
        return playerLoader;
    }

    public Integer getScore() {
        return score;
    }

    public void addPoints(int points) {
        score += points;
    }

    public void reset() {
        score = 0;
    }
}
