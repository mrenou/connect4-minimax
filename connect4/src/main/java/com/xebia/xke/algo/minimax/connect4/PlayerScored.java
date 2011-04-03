package com.xebia.xke.algo.minimax.connect4;

public class PlayerScored {

    private Player player;

    private Integer score = 0;

    public PlayerScored(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
