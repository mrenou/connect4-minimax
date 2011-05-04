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

        int myMaxScore = getMaxScore(counterColorTested);
        int otherMaxScore = getMaxScore(counterColorTested.getOtherCounterColor());

        return myMaxScore - otherMaxScore;
    }

    private int getMaxScore(CounterColor counterColor) {
        int maxScore = -1;
        long myBinaryBoard = board.getBinaryBoard(counterColor);

        //test horizontal
        maxScore = max(maxScore, getMaxLineSize(myBinaryBoard, board.getColumnSize() + 1)) ;
        //test vertical
        maxScore = max(maxScore, getMaxLineSize(myBinaryBoard, 1)) ;
        //test diag /
        maxScore = max(maxScore, getMaxLineSize(myBinaryBoard, board.getColumnSize() + 2)) ;
        //test diage \
        maxScore = max(maxScore, getMaxLineSize(myBinaryBoard, board.getColumnSize()));

        return maxScore;
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

}
