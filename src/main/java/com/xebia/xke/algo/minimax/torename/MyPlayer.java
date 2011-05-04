package com.xebia.xke.algo.minimax.torename;


import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.SimplePlayer;

public class MyPlayer extends SimplePlayer {

    private static final int MAX_DEPTH = 4;

    private AlphaBeta alphaBeta = new AlphaBeta();

    @Override
    public int play(CounterColor counterColor, Board board) {
        StateConnectFour stateConnectFour = new MyStateConnectFour((Board) board.clone(), counterColor, counterColor, -1, MAX_DEPTH);

        StateConnectFour bestNextState = alphaBeta.alphabeta(stateConnectFour);
        return bestNextState.getColumnIndexPlayed();
    }

}
