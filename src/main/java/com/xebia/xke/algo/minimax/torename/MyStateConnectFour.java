package com.xebia.xke.algo.minimax.torename;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;

public class MyStateConnectFour extends StateConnectFour {

    public MyStateConnectFour(Board board, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
        super(board, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
    }

    @Override
    protected StateConnectFour createNextState(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
        return new MyStateConnectFour(boardCloned, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
    }

    @Override
    protected int getScore() {
        if (board.getWinnerCounterColor() != null) {
            if (counterColorTested.equals(board.getWinnerCounterColor())) {
                return State.BEST_SCORE;
            } else {
                return -State.BEST_SCORE;
            }
        }

        int myHighestScore = getHighestLineScore(counterColorTested);
        int otherHighestScore = getHighestLineScore(counterColorTested.getOtherCounterColor());

        return myHighestScore - otherHighestScore;
    }

    private int getHighestLineScore(CounterColor counterColor) {
        //FIXME :   - return highest line size
        //          - check horizontally _
        //          - check vertically |
        //          - check ascending diagonal /
        //          - check descending diagonal \
        return 0;
    }
}
