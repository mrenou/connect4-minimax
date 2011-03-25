package com.xebia.xke.algo.minimax.connect4;

import com.google.common.annotations.VisibleForTesting;

public class ConnectFour {

    private CounterColor currentCounterColor;
    private CounterColor winnerCounterColor;

    private int lastValidVerticalIndex = -1;

    @VisibleForTesting
    Board board = new Board();

    public ConnectFour() {
        currentCounterColor = CounterColor.YELLOW;
    }

    public Move putCounter(int columnIndex) {
        //TODO stop if end game
        int verticalIndex = board.putCounter(columnIndex, currentCounterColor);
        if (verticalIndex != -1) {
            currentCounterColor = currentCounterColor.getOtherCounterColor();
            winnerCounterColor = board.getWinnerCounterColor();
            lastValidVerticalIndex = verticalIndex;
        }
        return new Move(currentCounterColor.getOtherCounterColor(), columnIndex, verticalIndex, board.getWinnerCounterColor() != null);
    }

    public boolean isEndGame() {
        return board.isFull();
    }

    public CounterColor getCurrentCounterColor() {
        return currentCounterColor;
    }

    public Board getBoard() {
        return board;
    }

    public int getLastValidVerticalIndex() {
        return lastValidVerticalIndex;
    }
}
