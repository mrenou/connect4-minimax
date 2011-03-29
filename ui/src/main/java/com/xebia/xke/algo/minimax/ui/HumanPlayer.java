package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.Player;

public class HumanPlayer implements Player {
    @Override
    public int play(CounterColor counterColor, Board board) {
        return 0;
    }

    @Override
    public String getStats() {
        return "";
    }

    @Override
    public String getName() {
        return "Human";
    }
}
