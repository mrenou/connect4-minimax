package com.xebia.xke.algo.minimax.player.medium;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.tools.StateConnectFour;

public class StateConnectFourV1 extends StateConnectFour {

    public StateConnectFourV1(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
        super(boardCloned, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
    }

    @Override
    protected int getScore() {
        MaxLineSizeCounter myMaxLineSizeCounter = new MaxLineSizeCounter(counterColorTested, board);
        MaxLineSizeCounter otherMaxLineSizeCounter = new MaxLineSizeCounter(counterColorTested.getOtherCounterColor(), board);

        // horizontal
        for (int verticalIndex = 0; verticalIndex < board.getColumnSize(); verticalIndex++) {
            myMaxLineSizeCounter.initCounter();
            otherMaxLineSizeCounter.initCounter();
            for (int columnIndex = 0; columnIndex < board.getNbColumns(); columnIndex++) {
                if (myMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return 9999;
                }
                if (otherMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return -9999;
                }
            }
        }

        // vertical
        for (int columnIndex = 0; columnIndex < board.getNbColumns(); columnIndex++) {
            myMaxLineSizeCounter.initCounter();
            otherMaxLineSizeCounter.initCounter();
            for (int verticalIndex = 0; verticalIndex < board.getColumnSize(); verticalIndex++) {
                if (myMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return 9999;
                }
                if (otherMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return -9999;
                }
            }
        }

        // ascending diag left
        for (int verticalStartIndex = 0; verticalStartIndex < board.getColumnSize(); verticalStartIndex++) {
            myMaxLineSizeCounter.initCounter();
            otherMaxLineSizeCounter.initCounter();
            int verticalIndex = verticalStartIndex;
            int columnIndex = 0;

            for (; verticalIndex < board.getColumnSize() && columnIndex < board.getNbColumns(); verticalIndex++, columnIndex++) {
                if (myMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return 9999;
                }
                if (otherMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return -9999;
                }
            }
        }

        // ascending diag right
        for (int columnStartIndex = 1; columnStartIndex < board.getNbColumns(); columnStartIndex++) {
            myMaxLineSizeCounter.initCounter();
            otherMaxLineSizeCounter.initCounter();
            int verticalIndex = 0;
            int columnIndex = columnStartIndex;

            for (; verticalIndex < board.getColumnSize() && columnIndex < board.getNbColumns(); verticalIndex++, columnIndex++) {
                if (myMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return 9999;
                }
                if (otherMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return -9999;
                }
            }
        }


        // descending diag left
        for (int verticalStartIndex = 0; verticalStartIndex < board.getColumnSize(); verticalStartIndex++) {
            myMaxLineSizeCounter.initCounter();
            otherMaxLineSizeCounter.initCounter();
            int verticalIndex = verticalStartIndex;
            int columnIndex = 0;

            for (; verticalIndex < board.getColumnSize() && columnIndex < board.getNbColumns(); verticalIndex--, columnIndex++) {
                if (myMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return 9999;
                }
                if (otherMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return -9999;
                }
            }
        }

        // descending diag right
        for (int columnStartIndex = 1; columnStartIndex < board.getNbColumns(); columnStartIndex++) {
            myMaxLineSizeCounter.initCounter();
            otherMaxLineSizeCounter.initCounter();
            int verticalIndex = board.getColumnSize() -1;
            int columnIndex = columnStartIndex;

            for (; verticalIndex < board.getColumnSize() && columnIndex < board.getNbColumns(); verticalIndex--, columnIndex++) {
                if (myMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return 9999;
                }
                if (otherMaxLineSizeCounter.checkForIncrement(columnIndex, verticalIndex)) {
                    return -9999;
                }
            }
        }
        return myMaxLineSizeCounter.getMaxLineSize() - otherMaxLineSizeCounter.getMaxLineSize();
    }

    @Override
    protected StateConnectFour createNextState(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
        return new StateConnectFourV1(boardCloned, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
    }

    private class MaxLineSizeCounter {
        private CounterColor counterColor;
        private Board board;
        private Integer maxLineSize;
        private int counter;

        public MaxLineSizeCounter(CounterColor counterColor, Board board) {
            this.counterColor = counterColor;
            this.board = board;
            this.maxLineSize = 0;
            initCounter();
        }

        public void initCounter() {
            counter = 0;
        }

        public Integer getMaxLineSize() {
            return maxLineSize;
        }

        public int getCounter() {
            return counter;
        }

        public boolean checkForIncrement(int columnIndex, int verticalIndex) {
            if (board.hasCounterColor(columnIndex, verticalIndex, counterColor)) {
                counter++;
                if (maxLineSize == null || counter > maxLineSize) {
                    maxLineSize = counter;
                }
            } else {
                counter = 0;
            }
            if (counter == 4) {
                return true;
            }
            return false;
        }
    }
}
