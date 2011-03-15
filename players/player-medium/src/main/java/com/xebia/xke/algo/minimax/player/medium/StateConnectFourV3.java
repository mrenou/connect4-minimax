package com.xebia.xke.algo.minimax.player.medium;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.tools.State;
import com.xebia.xke.algo.minimax.tools.StateConnectFour;

public class StateConnectFourV3 extends StateConnectFour {

    private enum LineType {
        VERTICAL, HORIZONTAL, DIAGONAL_ASC, DIAGONAL_DES
    }

    public StateConnectFourV3(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
        super(boardCloned, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
    }

    int getDiagonalAscendingScore() {
        //
        int score = 0;
        for (int verticalStartIndex = 0; verticalStartIndex < board.getColumnSize(); verticalStartIndex++) {
            int verticalIndex = verticalStartIndex;
            int columnIndex = 0;

            int lineSize = 0;
            boolean startWithEmpty = false;
            boolean endWithEmpty = false;
            CounterColor scoringCounterColor = null;

            for (; verticalIndex < board.getColumnSize() && columnIndex < board.getNbColumns(); verticalIndex++, columnIndex++) {
                if (board.isEmpty(columnIndex, verticalIndex)) {
                    score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_ASC, lineSize, startWithEmpty, endWithEmpty);
                    lineSize = 0;
                    startWithEmpty = false;
                    endWithEmpty = false;
                    scoringCounterColor = null;
                }
                if (board.isEmpty(columnIndex, verticalIndex) == false) {
                    if (scoringCounterColor == null || board.hasCounterColor(columnIndex, verticalIndex, scoringCounterColor) == false) {
                        score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_ASC, lineSize, startWithEmpty, endWithEmpty);
                        scoringCounterColor = board.getCounterColor(columnIndex, verticalIndex);
                        lineSize = 0;
                        startWithEmpty = false;
                        endWithEmpty = false;
                        lineSize = 1;
                    } else {
                        lineSize++;
                    }
                    if (board.isPlayableForNextTurn(columnIndex - 1, verticalIndex - 1) == true) {
                        startWithEmpty = true;
                    }
                    if (board.isPlayableForNextTurn(columnIndex + 1, verticalIndex + 1) == true) {
                        endWithEmpty = true;
                    }
                }
            }
            score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_ASC, lineSize, startWithEmpty, endWithEmpty);
        }
        for (int columnStartIndex = 1; columnStartIndex < board.getNbColumns(); columnStartIndex++) {
            int verticalIndex = 0;
            int columnIndex = columnStartIndex;

            int lineSize = 0;
            boolean startWithEmpty = false;
            boolean endWithEmpty = false;
            CounterColor scoringCounterColor = null;

            for (; verticalIndex < board.getColumnSize() && columnIndex < board.getNbColumns(); verticalIndex++, columnIndex++) {
                if (board.isEmpty(columnIndex, verticalIndex)) {
                    score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_ASC, lineSize, startWithEmpty, endWithEmpty);
                    lineSize = 0;
                    startWithEmpty = false;
                    endWithEmpty = false;
                    scoringCounterColor = null;
                }
                if (board.isEmpty(columnIndex, verticalIndex) == false) {
                    if (scoringCounterColor == null || board.hasCounterColor(columnIndex, verticalIndex, scoringCounterColor) == false) {
                        score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_ASC, lineSize, startWithEmpty, endWithEmpty);
                        scoringCounterColor = board.getCounterColor(columnIndex, verticalIndex);
                        lineSize = 0;
                        startWithEmpty = false;
                        endWithEmpty = false;
                        lineSize = 1;
                    } else {
                        lineSize++;
                    }
                    if (board.isPlayableForNextTurn(columnIndex - 1, verticalIndex - 1) == true) {
                        startWithEmpty = true;
                    }
                    if (board.isPlayableForNextTurn(columnIndex + 1, verticalIndex + 1) == true) {
                        endWithEmpty = true;
                    }
                }
            }
            score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_ASC, lineSize, startWithEmpty, endWithEmpty);
        }
        return score;
    }

    int getDiagonalDescendingScore() {
        //
        int score = 0;
        for (int verticalStartIndex = 0; verticalStartIndex < board.getColumnSize(); verticalStartIndex++) {
            int verticalIndex = verticalStartIndex;
            int columnIndex = 0;

            int lineSize = 0;
            boolean startWithEmpty = false;
            boolean endWithEmpty = false;
            CounterColor scoringCounterColor = null;

            for (; verticalIndex >= 0 && columnIndex < board.getNbColumns(); verticalIndex--, columnIndex++) {
                if (board.isEmpty(columnIndex, verticalIndex)) {
                    score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_DES, lineSize, startWithEmpty, endWithEmpty);
                    lineSize = 0;
                    startWithEmpty = false;
                    endWithEmpty = false;
                    scoringCounterColor = null;
                }
                if (board.isEmpty(columnIndex, verticalIndex) == false) {
                    if (scoringCounterColor == null || board.hasCounterColor(columnIndex, verticalIndex, scoringCounterColor) == false) {
                        score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_DES, lineSize, startWithEmpty, endWithEmpty);
                        scoringCounterColor = board.getCounterColor(columnIndex, verticalIndex);
                        lineSize = 0;
                        startWithEmpty = false;
                        endWithEmpty = false;
                        lineSize = 1;
                    } else {
                        lineSize++;
                    }
                    if (board.isPlayableForNextTurn(columnIndex - 1, verticalIndex + 1) == true) {
                        startWithEmpty = true;
                    }
                    if (board.isPlayableForNextTurn(columnIndex + 1, verticalIndex - 1) == true) {
                        endWithEmpty = true;
                    }
                }
            }
            score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_DES, lineSize, startWithEmpty, endWithEmpty);
        }
        for (int columnStartIndex = 1; columnStartIndex < board.getNbColumns(); columnStartIndex++) {
            int verticalIndex = board.getColumnSize() -1;
            int columnIndex = columnStartIndex;

            int lineSize = 0;
            boolean startWithEmpty = false;
            boolean endWithEmpty = false;
            CounterColor scoringCounterColor = null;

            for (; verticalIndex >= 0 && columnIndex < board.getNbColumns(); verticalIndex--, columnIndex++) {
                if (board.isEmpty(columnIndex, verticalIndex)) {
                    score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_DES, lineSize, startWithEmpty, endWithEmpty);
                    lineSize = 0;
                    startWithEmpty = false;
                    endWithEmpty = false;
                    scoringCounterColor = null;
                }
                if (board.isEmpty(columnIndex, verticalIndex) == false) {
                    if (scoringCounterColor == null || board.hasCounterColor(columnIndex, verticalIndex, scoringCounterColor) == false) {
                        score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_DES, lineSize, startWithEmpty, endWithEmpty);
                        scoringCounterColor = board.getCounterColor(columnIndex, verticalIndex);
                        lineSize = 0;
                        startWithEmpty = false;
                        endWithEmpty = false;
                        lineSize = 1;
                    } else {
                        lineSize++;
                    }
                    if (board.isPlayableForNextTurn(columnIndex - 1, verticalIndex + 1) == true) {
                        startWithEmpty = true;
                    }
                    if (board.isPlayableForNextTurn(columnIndex + 1, verticalIndex - 1) == true) {
                        endWithEmpty = true;
                    }
                }
            }
            score += getScoreLine(scoringCounterColor, LineType.DIAGONAL_DES, lineSize, startWithEmpty, endWithEmpty);
        }
        return score;
    }

    int getHorizontalScore() {
        // horizontal
        int score = 0;
        for (int verticalIndex = 0; verticalIndex < board.getColumnSize(); verticalIndex++) {
            int lineSize = 0;
            boolean startWithEmpty = false;
            boolean endWithEmpty = false;
            CounterColor scoringCounterColor = null;


            for (int columnIndex = 0; columnIndex < board.getNbColumns(); columnIndex++) {
                if (board.isEmpty(columnIndex, verticalIndex)) {
                    score += getScoreLine(scoringCounterColor, LineType.HORIZONTAL, lineSize, startWithEmpty, endWithEmpty);
                    lineSize = 0;
                    startWithEmpty = false;
                    endWithEmpty = false;
                    scoringCounterColor = null;
                }
                if (board.isEmpty(columnIndex, verticalIndex) == false) {
                    if (scoringCounterColor == null || board.hasCounterColor(columnIndex, verticalIndex, scoringCounterColor) == false) {
                        score += getScoreLine(scoringCounterColor, LineType.HORIZONTAL, lineSize, startWithEmpty, endWithEmpty);
                        scoringCounterColor = board.getCounterColor(columnIndex, verticalIndex);
                        lineSize = 0;
                        startWithEmpty = false;
                        endWithEmpty = false;
                        lineSize = 1;
                    } else {
                        lineSize++;
                    }
                    if (board.isPlayableForNextTurn(columnIndex - 1, verticalIndex) == true) {
                        startWithEmpty = true;
                    }
                    if (board.isPlayableForNextTurn(columnIndex + 1, verticalIndex) == true) {
                        endWithEmpty = true;
                    }
                }
            }
            score += getScoreLine(scoringCounterColor, LineType.HORIZONTAL, lineSize, startWithEmpty, endWithEmpty);
        }
        return score;
    }

    int getVerticalScore() {
        int score = 0;

        for (int columnIndex = 0; columnIndex < board.getNbColumns(); columnIndex++) {
            int lineSize = 0;
            boolean startWithEmpty = false;
            boolean endWithEmpty = false;
            CounterColor scoringCounterColor = null;

            for (int verticalIndex = 0; verticalIndex < board.getColumnSize(); verticalIndex++) {
                if (board.isEmpty(columnIndex, verticalIndex)) {
                    score += getScoreLine(scoringCounterColor, LineType.VERTICAL, lineSize, startWithEmpty, endWithEmpty);
                    lineSize = 0;
                    startWithEmpty = false;
                    endWithEmpty = false;
                    scoringCounterColor = null;
                    break;
                }
                if (board.isEmpty(columnIndex, verticalIndex) == false) {
                    if (scoringCounterColor == null || board.hasCounterColor(columnIndex, verticalIndex, scoringCounterColor) == false) {
                        score += getScoreLine(scoringCounterColor, LineType.VERTICAL, lineSize, startWithEmpty, endWithEmpty);
                        scoringCounterColor = board.getCounterColor(columnIndex, verticalIndex);
                        lineSize = 0;
                        startWithEmpty = false;
                        endWithEmpty = false;
                        lineSize = 1;
                    } else {
                        lineSize++;
                    }
                    if (board.isPlayableForNextTurn(columnIndex, verticalIndex - 1) == true) {
                        startWithEmpty = true;
                    }
                    if (board.isPlayableForNextTurn(columnIndex, verticalIndex + 1) == true) {
                        endWithEmpty = true;
                    }
                }
            }
            score += getScoreLine(scoringCounterColor, LineType.VERTICAL, lineSize, startWithEmpty, endWithEmpty);
        }
        return score;
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
        return getHorizontalScore() + getVerticalScore() + getDiagonalAscendingScore() + getDiagonalDescendingScore();
    }

    private int getScoreLine(CounterColor scoringCounterColor, LineType lineType, int lineSize, boolean startWithEmpty, boolean endWithEmpty) {
        int score;

        if (lineSize == 0) {
            return 0;
        } else if (lineSize == 1) {
            score = 1;
        } else {
            score = (int) (1 * Math.pow(10, (lineSize - 1)));
            if (!LineType.VERTICAL.equals(lineType)) {
                if (startWithEmpty == true) {
                    score *= 5;
                }
                if (endWithEmpty == true) {
                    score *= 5;
                }
            }
        }
        if (!counterColorTested.equals(scoringCounterColor)) {
            score = -score;
        }
        return score;
    }

    @Override
    protected StateConnectFour createNextState(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
        return new StateConnectFourV3(boardCloned, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
    }
}
