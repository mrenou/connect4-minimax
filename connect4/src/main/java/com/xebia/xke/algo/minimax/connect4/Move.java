package com.xebia.xke.algo.minimax.connect4;

public class Move {

    private CounterColor counterColor;

    private int columnIndex;

    private int verticalIndex;

    private boolean winningMove;

    public Move(CounterColor counterColor, int columnIndex, int verticalIndex, boolean winningMove) {
        this.counterColor = counterColor;
        this.columnIndex = columnIndex;
        this.verticalIndex = verticalIndex;
        this.winningMove = winningMove;
    }

    public CounterColor getCounterColor() {
        return counterColor;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getVerticalIndex() {
        return verticalIndex;
    }

    public boolean isWinningMove() {
        return winningMove;
    }

    public boolean isValidMove() {
        return verticalIndex != -1;
    }
}
