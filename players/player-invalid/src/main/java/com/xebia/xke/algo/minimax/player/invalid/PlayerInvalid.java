package com.xebia.xke.algo.minimax.player.invalid;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.SimplePlayer;

public class PlayerInvalid extends SimplePlayer {


    @Override
    public int play(CounterColor counterColor, Board board) {
        return 42;
    }

}
