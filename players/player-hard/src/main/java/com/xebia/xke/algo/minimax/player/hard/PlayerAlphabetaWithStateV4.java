package com.xebia.xke.algo.minimax.player.hard;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.SimplePlayer;
import com.xebia.xke.algo.minimax.tools.AlphaBeta;
import com.xebia.xke.algo.minimax.tools.StateConnectFour;

public class PlayerAlphabetaWithStateV4 extends SimplePlayer {

    AlphaBeta alphaBeta = new AlphaBeta();
    private static final int MAX_DEPTH = 8;

    @Override
    public int play(CounterColor counterColor, Board board) {
        if (board.getNumberOfCounters() == 0) {
            return 3;
        }

        StateConnectFour stateConnectFour = new StateConnectFourV4(board.clone(), counterColor, counterColor, -1, MAX_DEPTH);

        StateConnectFour bestNextState = alphaBeta.minimax(stateConnectFour);
        return bestNextState.getColumnIndexPlayed();
    }

}
