package com.xebia.xke.algo.minimax.connect4;

public interface Player {

    int play(CounterColor counterColor, Board board);

    String getStats();

    void setName(String name);

    String getName();
}
