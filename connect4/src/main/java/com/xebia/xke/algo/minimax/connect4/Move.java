package com.xebia.xke.algo.minimax.connect4;

public class Move {

    private CounterColor counterColor;

    private int columnIndex;

    private int verticalIndex;

    private boolean winningMove;

    private boolean timeoutMove;

    static public Move createTimeoutMove(CounterColor counterColor) {
        Move move = new Move(counterColor, -1, -1, false, true);
        return move;
    }

    static public Move createMove(CounterColor counterColor, int columnIndex, int verticalIndex, boolean winningMove) {
        Move move = new Move(counterColor, columnIndex, verticalIndex, winningMove, false);
        return move;
    }

    protected Move(CounterColor counterColor, int columnIndex, int verticalIndex, boolean winningMove, boolean timeoutMove) {
        this.counterColor = counterColor;
        this.columnIndex = columnIndex;
        this.verticalIndex = verticalIndex;
        this.winningMove = winningMove;
        this.timeoutMove = timeoutMove;
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

    public boolean isTimeoutMove() {
        return timeoutMove;
    }

    public boolean isValidMove() {
        return verticalIndex != -1;
    }

    public boolean isEndingMove() {
        return isWinningMove() || isTimeoutMove() || !isValidMove();
    }
}
