package com.xebia.xke.algo.minimax.player.easy;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.tools.State;
import com.xebia.xke.algo.minimax.tools.StateConnectFour;

public class StateConnectFourV1 extends StateConnectFour {

    private static final int COLUMN_SIZE = 6;

    public StateConnectFourV1(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
        super(boardCloned, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
    }

    private int getMaxLineSize(long board, int shiftValue) {
        long firstShiftedBoard = shiftAndMergeBoard(board, shiftValue);
        long secondShiftedBoard = shiftAndMergeBoard(firstShiftedBoard, shiftValue);
        long doubleShiftedBoard = shiftAndMergeBoard(firstShiftedBoard, 2 * shiftValue);

        if (doubleShiftedBoard != 0) {
            return 4;
        }
        if (secondShiftedBoard != 0) {
            return 3;
        }
        if ((firstShiftedBoard ^ secondShiftedBoard) != 0) {
            return 2;
        }
        if (board != 0) {
            return 1;
        }
        return 0;
    }


    private long shiftAndMergeBoard(long board, int shiftValue) {
        return board & (board >> shiftValue);
    }

    int max(int score1, int score2) {
        if (score1 > score2) {
            return score1;
        }
        return score2;
    }

    @Override
    protected int getScore() {
        //TODO move to abstract ?
        if (board.getWinnerCounterColor() != null) {
            if (counterColorTested.equals(board.getWinnerCounterColor())) {
                return State.BEST_SCORE;
            } else {
                return -State.BEST_SCORE;
            }
        }

        int myMaxScore = getMaxScore(counterColorTested);
        int otherMaxScore = getMaxScore(counterColorTested.getOtherCounterColor());

        return myMaxScore - otherMaxScore;
    }

    private int getMaxScore(CounterColor counterColor) {
        int maxScore = -1;
        long myBinaryBoard = board.getBinaryBoard(counterColor);

        //test horizontal
        maxScore = max(maxScore, getMaxLineSize(myBinaryBoard, COLUMN_SIZE + 1)) ;
        //test vertical
        maxScore = max(maxScore, getMaxLineSize(myBinaryBoard, 1)) ;
        //test diag /
        maxScore = max(maxScore, getMaxLineSize(myBinaryBoard, COLUMN_SIZE + 2)) ;
        //test diage \
        maxScore = max(maxScore, getMaxLineSize(myBinaryBoard, COLUMN_SIZE));

        return maxScore;
    }

    @Override
    protected StateConnectFour createNextState(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
        return new StateConnectFourV1(boardCloned, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
    }
}
