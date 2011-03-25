package com.xebia.xke.algo.minimax.connect4;

import java.util.Arrays;

public class Board {

    private static final int NB_COLUMNS = 7;
    private static final int COLUMN_SIZE = 6;
    private static final long FULL_BOARD = ((1L << ((COLUMN_SIZE + 1) * NB_COLUMNS)) - 1);
    private static final long FULL_COLUMN = ((1 << (COLUMN_SIZE + 1)) -1);
    private static final long BOTTOM = FULL_BOARD / FULL_COLUMN;
    private static final long TOP = BOTTOM << COLUMN_SIZE;

    private int numberOfCounters;
    long[] colorBoards = new long[2]; // RED [0] YELLOW [1]
    byte[] columnsMaxValueUsed;
    private CounterColor winnerCounterColor;


    public Board() {
        columnsMaxValueUsed = new byte[NB_COLUMNS];

        // reset ?
        numberOfCounters = 0;
        colorBoards[0] = colorBoards[1] = 0L;
        // init with min value for each columns
        // for 7 * 6 board : 0, 7, 14, 21, 28, 35, 42
        for (int i = 0; i < NB_COLUMNS; i++) {
            columnsMaxValueUsed[i] = (byte)((COLUMN_SIZE + 1) * i);
        }
        winnerCounterColor = null;
    }

    public CounterColor getWinnerCounterColor() {
        return winnerCounterColor;
    }

    public Integer getNumberOfCounters() {
        return numberOfCounters;
    }

    public boolean isInBoard(int columnIndex, int verticalIndex) {
        return columnIndex >= 0 && columnIndex < NB_COLUMNS &&
                verticalIndex >= 0 && verticalIndex < COLUMN_SIZE;
    }

    public boolean hasCounterColor(int columnIndex, int verticalIndex, CounterColor counterColor) {
        return getCounterColor(columnIndex, verticalIndex) == counterColor;
    }

    public boolean isEmpty(int columnIndex, int verticalIndex) {
        return isInBoard(columnIndex, verticalIndex) && checkCounterColorAtPosition(columnIndex, verticalIndex) == null;
    }

    public boolean isPlayableForNextTurn(int columnIndex, int verticalIndex) {
        return isEmpty(columnIndex, verticalIndex) && (verticalIndex == 0 || !isEmpty(columnIndex, verticalIndex - 1));
    }

    public CounterColor getCounterColor(int columnIndex, int verticalIndex) {
        if (isInBoard(columnIndex, verticalIndex)) {
            return checkCounterColorAtPosition(columnIndex, verticalIndex);
        }
        return null;
    }

    private CounterColor checkCounterColorAtPosition(int columnIndex, int verticalIndex) {
        if ((colorBoards[0] & (1L << (((COLUMN_SIZE + 1) * columnIndex) + verticalIndex))) != 0) {
            return CounterColor.RED;
        }
        if ((colorBoards[1] & (1L << (((COLUMN_SIZE + 1) * columnIndex) + verticalIndex))) != 0) {
            return CounterColor.YELLOW;
        }
        return null;
    }

    public int putCounter(int columnIndex, CounterColor counterColor) {
        //TODO need ?
        /*if (winnerCounterColor != null) {
            throw new IllegalStateException("The game is ended.");
        }*/

        if (columnIndex < 0 || columnIndex >= NB_COLUMNS || columnIsfull(columnIndex)) {
            return -1;
        }
        if (CounterColor.RED.equals(counterColor)) {
            //TODO inclusive or ?
            colorBoards[0] ^= 1L << columnsMaxValueUsed[columnIndex]++;
        } else {
            colorBoards[1] ^= 1L << columnsMaxValueUsed[columnIndex]++;
        }
        //TODO test vertical index
        int verticalIndex = (columnsMaxValueUsed[columnIndex] - ((COLUMN_SIZE + 1) * columnIndex)) - 1;
        //TODO record move ?
        if (checkWinningMove(colorBoards[0])) {
            winnerCounterColor = CounterColor.RED;
        }
        if (checkWinningMove(colorBoards[1])) {
            winnerCounterColor = CounterColor.YELLOW;
        }
        numberOfCounters++;
        return verticalIndex;
    }

    public int getNbColumns() {
        return NB_COLUMNS;
    }

    public int getColumnSize() {
        return COLUMN_SIZE;
    }

    public Board clone() {
        Board board = new Board();

        board.numberOfCounters = numberOfCounters;
        board.colorBoards = colorBoards.clone();
        board.columnsMaxValueUsed = columnsMaxValueUsed.clone();
        board.winnerCounterColor = winnerCounterColor;
        return board;
    }

    public boolean columnIsfull(int indexColumn) {
        if (((1L << columnsMaxValueUsed[indexColumn]) & TOP) != 0 ) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return numberOfCounters == COLUMN_SIZE * NB_COLUMNS;
    }

    private boolean checkWinningMove(long board) {

        if (/*check diagonal \ */ testLinesOf4(board, COLUMN_SIZE) ||
                /*check horizontal - */ testLinesOf4(board, COLUMN_SIZE + 1) ||
                /*check diagonal / */ testLinesOf4(board, COLUMN_SIZE + 2) ||
                /*check vertical | */ testLinesOf4(board, 1)) {
            return true;
        }
        return false;
    }

    private boolean testLinesOf4(long board, int shiftValue) {
        // First shift compared all element with the next element
        // So for a given line of 4, compare 1 with 2, 2 with 3 and 3 with 4
        long firstShiftedBoard = shiftAndMergeBoard(board, shiftValue);
        // Second shift (twice of the first) compared each previous results with the next-next element
        // So for in fact, result compare of 1 and 2 is compared with result compare of 3 and 4
        long secondShiftedBoard = shiftAndMergeBoard(firstShiftedBoard, 2 * shiftValue);
        // So if 1 == 2 and 3 == 4 and 1&2 == 3&4  so 1==2==3==4, so you have a four line
        //
        // Binary example with diag \ (shift value == columnsize == 6)
        //......1 .....1. ....1.. ...1... => board with diag \ line of 4
        //....... .....1. ....1.. ...1... => first shift, board >> 6
        //....... .....1. ....1.. ...1... => first merge
        //
        //....... .....1. ....1.. ...1... => re-use first merge result
        //....... ....... ....... ...1... => second shift, board >> 12
        //....... ....... ....... ...1... => second merge
        // Result != 0, so we have a line
        if (secondShiftedBoard != 0) {
            return true;
        }
        return false;
    }

    private long shiftAndMergeBoard(long board, int shiftValue) {
        return board & (board >> shiftValue);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Board) {
            Board board = (Board) o;
            return Arrays.equals(board.colorBoards, colorBoards);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(colorBoards);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("\n");
        for (int verticalIndex = getColumnSize() - 1; verticalIndex >= 0; verticalIndex--) {
            for (int boardIndex = verticalIndex; boardIndex < ((COLUMN_SIZE + 1) * NB_COLUMNS); boardIndex += COLUMN_SIZE + 1) {
                long mask = 1L << boardIndex;
                if ((colorBoards[0] & mask) != 0) {
                    b.append("R");
                } else if ((colorBoards[1] & mask) != 0) {
                    b.append("Y");
                } else {
                    b.append(".");
                }
            }
            b.append("\n");
        }
        return b.toString();
    }

    public Board getInverseBoard() {
        Board boardCloned = (Board) this.clone();
        boardCloned.colorBoards[0]  = colorBoards[1];
        boardCloned.colorBoards[1]  = colorBoards[0];
        if (boardCloned.winnerCounterColor != null) {
            boardCloned.winnerCounterColor = boardCloned.winnerCounterColor.getOtherCounterColor();
        }
        return boardCloned;
    }
}
