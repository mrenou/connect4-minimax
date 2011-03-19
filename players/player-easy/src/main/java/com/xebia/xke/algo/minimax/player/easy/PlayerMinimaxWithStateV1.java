package com.xebia.xke.algo.minimax.player.easy;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.Player;
import com.xebia.xke.algo.minimax.tools.Minimax;
import com.xebia.xke.algo.minimax.tools.StateConnectFour;

public class PlayerMinimaxWithStateV1 implements Player {

    private Minimax minimax = new Minimax();
    private static final int MAX_DEPTH = 3;

    @Override
    public int play(CounterColor counterColor, Board board) {
        StateConnectFour stateConnectFour = new StateConnectFourV1((Board) board.clone(), counterColor, counterColor, -1, MAX_DEPTH);

        StateConnectFour bestNextState = minimax.minimax(stateConnectFour);
        return bestNextState.getColumnIndexPlayed();
    }

    @Override
    public String getStats() {
        return minimax.getStats();
    }

    @Override
    public String getName() {
        return "Easy-1";
    }

}
