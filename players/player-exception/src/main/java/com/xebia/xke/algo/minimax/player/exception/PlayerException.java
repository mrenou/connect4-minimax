package com.xebia.xke.algo.minimax.player.exception;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.SimplePlayer;

public class PlayerException extends SimplePlayer {


    @Override
    public int play(CounterColor counterColor, Board board) {
        throw new NullPointerException("BOOM");
    }

}
