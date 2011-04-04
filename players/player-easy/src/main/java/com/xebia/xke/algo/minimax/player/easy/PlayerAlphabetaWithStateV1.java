package com.xebia.xke.algo.minimax.player.easy;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.SimplePlayer;
import com.xebia.xke.algo.minimax.tools.AlphaBeta;
import com.xebia.xke.algo.minimax.tools.StateConnectFour;

public class PlayerAlphabetaWithStateV1 extends SimplePlayer {

    AlphaBeta alphaBeta = new AlphaBeta();
    private static final int MAX_DEPTH = 5;

    @Override
    public int play(CounterColor counterColor, Board board) {
        StateConnectFour stateConnectFour = new StateConnectFourV1((Board) board.clone(), counterColor, counterColor, -1, MAX_DEPTH);

        StateConnectFour bestNextState = alphaBeta.minimax(stateConnectFour);
        return bestNextState.getColumnIndexPlayed();
    }

    @Override
    public String getStats() {
        return alphaBeta.getStats();
    }
}
