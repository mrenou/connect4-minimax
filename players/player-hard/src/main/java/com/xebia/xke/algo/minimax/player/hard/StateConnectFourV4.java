package com.xebia.xke.algo.minimax.player.hard;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.tools.State;
import com.xebia.xke.algo.minimax.tools.StateConnectFour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class StateConnectFourV4 extends StateConnectFour {

    private static Map<String, Integer> _8pos = new HashMap<String, Integer>();

    static {
        BufferedReader reader = new BufferedReader(new InputStreamReader(StateConnectFourV4.class.getResourceAsStream("/connect-4.data")));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String board = line.substring(0, 83);
                String state = line.substring(84);

                if (state.equals("loss")) {
                    _8pos.put(board, -State.BEST_SCORE);
                } else if (state.equals("win")) {
                    _8pos.put(board, State.BEST_SCORE);
                } if (state.equals("draw")) {
                    _8pos.put(board, 0);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error during loading 8ply file.");
        }

    }

    private enum LineType {
        VERTICAL, HORIZONTAL, DIAGONAL_ASC, DIAGONAL_DES
    }

    public StateConnectFourV4(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
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
        if (board.getNumberOfCounters() == 8) {
            Integer precalculatedScore = _8pos.get(getBoardAsPlainString());
            if (precalculatedScore != null) {
                return precalculatedScore;
            }
        }
        if (board.getWinnerCounterColor() != null) {
            if (counterColorTested.equals(board.getWinnerCounterColor())) {
                return State.BEST_SCORE;
            } else {
                return -State.BEST_SCORE;
            }
        }
        return getHorizontalScore() + getVerticalScore() + getDiagonalAscendingScore() + getDiagonalDescendingScore();
    }

    private String getBoardAsPlainString() {
        StringBuilder b = new StringBuilder();

        for (int columnIndex = 0; columnIndex < board.getNbColumns(); columnIndex++) {
            for (int verticalIndex = 0; verticalIndex < board.getColumnSize(); verticalIndex++) {
                if (!b.toString().isEmpty()) {
                    b.append(",");
                }
                if (board.getCounterColor(columnIndex, verticalIndex) == null) {
                    b.append("b");
                }
                else if (board.getCounterColor(columnIndex, verticalIndex).equals(counterColorTested)) {
                    b.append("o");
                }
                else if (board.getCounterColor(columnIndex, verticalIndex).equals(counterColorTested.getOtherCounterColor())) {
                    b.append("x");
                }
            }
        }
        return b.toString();
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
        return new StateConnectFourV4(boardCloned, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
    }

    @Override
    protected boolean isFinalState(int depth) {
        if (board.getNumberOfCounters() == 8) {
            return true;
        }
        return super.isFinalState(depth);
    }
}
