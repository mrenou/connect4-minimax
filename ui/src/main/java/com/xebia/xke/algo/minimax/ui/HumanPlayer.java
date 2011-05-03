package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.SimplePlayer;

public class HumanPlayer extends SimplePlayer {
    @Override
    public int play(CounterColor counterColor, Board board) {
        return 0;
    }

    @Override
    public String getName() {
        return "Human";
    }
}
