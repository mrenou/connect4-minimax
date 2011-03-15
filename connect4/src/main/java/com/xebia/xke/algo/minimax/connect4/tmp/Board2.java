package com.xebia.xke.algo.minimax.connect4.tmp;

import com.xebia.xke.algo.minimax.connect4.ConnectFourConfig;
import com.xebia.xke.algo.minimax.connect4.CounterColor;

public class Board2 implements Cloneable {

    private int nbColumns;
    private int columnSize;
    private int numberOfCounters;

    //default for test
    CounterColor[][] boardValues;

    private int winningLineSize = 4;
    private CounterColor winnerCounterColor;

    Board2(int nbColumns, int columnSize) {
        this.nbColumns = nbColumns;
        this.columnSize = columnSize;
        boardValues = new CounterColor[this.nbColumns][this.columnSize];
    }

    Board2() {
        this(ConnectFourConfig.create().getNbColumns(), ConnectFourConfig.create().getColumnSize());
    }

    public CounterColor getWinnerCounterColor() {
        return winnerCounterColor;
    }

    public Integer getNumberOfCounters() {
        return numberOfCounters;
    }

    public boolean isInBoard(int columnIndex, int verticalIndex) {
        return columnIndex >= 0 && columnIndex < nbColumns &&
                verticalIndex >= 0 && verticalIndex < columnSize;
    }

    public boolean hasCounterColor(int columnIndex, int verticalIndex, CounterColor counterColor) {
        return isInBoard(columnIndex, verticalIndex) && boardValues[columnIndex][verticalIndex] != null && counterColor.equals(boardValues[columnIndex][verticalIndex]);
    }

    public boolean isEmpty(int columnIndex, int verticalIndex) {
        return isInBoard(columnIndex, verticalIndex) && boardValues[columnIndex][verticalIndex] == null;
    }

    public boolean isPlayableForNextTurn(int columnIndex, int verticalIndex) {
        return isEmpty(columnIndex, verticalIndex) && (verticalIndex == -1 || !isEmpty(columnIndex, verticalIndex - 1));
    }

    private int getNextValidVerticalIndex(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < nbColumns) {
            int verticalIndex = 0;
            while (verticalIndex < boardValues[columnIndex].length && boardValues[columnIndex][verticalIndex] != null) {
                verticalIndex++;
            }
            if (verticalIndex >= 0 && verticalIndex < columnSize) {
                return verticalIndex;
            }
        }
        return -1;
    }

    public CounterColor getCounterColorAtPosition(int indexColumn, int verticalIndex) {
        if (isInBoard(indexColumn, verticalIndex)) {
            return boardValues[indexColumn][verticalIndex];
        }
        return null;
    }

    int putCounter(int columnIndex, CounterColor counterColor) {
        //TODO need ?
        /*if (winnerCounterColor != null) {
            throw new IllegalStateException("The game is ended.");
        }*/

        int verticalIndex = getNextValidVerticalIndex(columnIndex);
        if (verticalIndex != -1) {
            boardValues[columnIndex][verticalIndex] = counterColor;
            numberOfCounters++;
            checkWinningMove(columnIndex, verticalIndex, counterColor);
        }
        return verticalIndex;
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public int getColumnSize() {
        return columnSize;
    }

    @Override
    protected Object clone() {
        Board2 board = new Board2();

        board.columnSize = columnSize;
        board.nbColumns = nbColumns;
        board.numberOfCounters = numberOfCounters;
        board.boardValues = new CounterColor[nbColumns][0];
        board.winnerCounterColor = winnerCounterColor;
        for (int columnIndex = 0; columnIndex < nbColumns; columnIndex++) {
            board.boardValues[columnIndex] = boardValues[columnIndex].clone();
        }

        return board;
    }

    boolean columnIsfull(int indexColumn) {
        if (boardValues[indexColumn][columnSize -1] != null) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return numberOfCounters == columnSize * nbColumns;
    }

    private void checkWinningMove(int columnIndex, int verticalIndex, CounterColor counterColor) {
        if (checkVerticalWinningLine(columnIndex, verticalIndex, counterColor) ||
                checkHorizontalWinningLine(columnIndex, verticalIndex, counterColor) ||
                checkAscendingDiagonalWinningLine(columnIndex, verticalIndex, counterColor) ||
                checkDescendingDiagonalWinningLine(columnIndex, verticalIndex, counterColor)) {
            this.winnerCounterColor = counterColor;
        }
    }

    private boolean checkVerticalWinningLine(int currentColumnIndex, int currentVerticalIndex, CounterColor counterColor) {
        return checkWinningLine(currentColumnIndex, currentVerticalIndex - (winningLineSize - 1), 0, 1, counterColor);
    }

    private boolean checkHorizontalWinningLine(int currentColumnIndex, int currentVerticalIndex, CounterColor counterColor) {
        return checkWinningLine(currentColumnIndex - (winningLineSize - 1), currentVerticalIndex, 1, 0, counterColor);
    }

    private boolean checkAscendingDiagonalWinningLine(int currentColumnIndex, int currentVerticalIndex, CounterColor counterColor) {
        return checkWinningLine(currentColumnIndex - (winningLineSize - 1), currentVerticalIndex - (winningLineSize - 1), 1, 1, counterColor);
    }

    private boolean checkDescendingDiagonalWinningLine(int currentColumnIndex, int currentVerticalIndex, CounterColor counterColor) {
        return checkWinningLine(currentColumnIndex - (winningLineSize - 1), currentVerticalIndex + (winningLineSize - 1), 1, -1, counterColor);
    }

    private boolean checkWinningLine(int columnIndex, int verticalIndex, int columnIndexIncVal, int verticalIndexIncVal, CounterColor counterColor) {
        int winnerCounter = 0;

        for (int i = 0; i < ((winningLineSize * 2) - 1); i++) {
            if (hasCounterColor(columnIndex, verticalIndex, counterColor)) {
                winnerCounter++;
            } else {
                winnerCounter = 0;
            }
            if (winnerCounter == winningLineSize) {
                return true;
            }
            columnIndex += columnIndexIncVal;
            verticalIndex += verticalIndexIncVal;
        }
        return false;
    }

    //TODO see that
    @Override
    public boolean equals(Object o) {
        if (toString().equals(o.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("\n");
        for (int verticalIndex = getColumnSize() - 1; verticalIndex >= 0; verticalIndex--) {
            for (int columnIndex = 0; columnIndex < getNbColumns(); columnIndex++) {
                if (boardValues[columnIndex][verticalIndex] == null) {
                    b.append(".");
                }
                else if (boardValues[columnIndex][verticalIndex].equals(CounterColor.RED)) {
                    b.append("R");
                }
                else if (boardValues[columnIndex][verticalIndex].equals(CounterColor.YELLOW)) {
                    b.append("Y");
                }
            }
            b.append("\n");
        }
        return b.toString();
    }

    public Board2 getInverseBoard() {
        Board2 boardCloned = (Board2) this.clone();
        for (int verticalIndex = getColumnSize() - 1; verticalIndex >= 0; verticalIndex--) {
            for (int columnIndex = 0; columnIndex < getNbColumns(); columnIndex++) {
                if (boardCloned.boardValues[columnIndex][verticalIndex] != null) {
                    boardCloned.boardValues[columnIndex][verticalIndex] = boardCloned.boardValues[columnIndex][verticalIndex].getOtherCounterColor();
                }
            }
        }
        if (boardCloned.winnerCounterColor != null) {
           boardCloned.winnerCounterColor = boardCloned.winnerCounterColor.getOtherCounterColor();
        }
        return boardCloned;
    }
}
