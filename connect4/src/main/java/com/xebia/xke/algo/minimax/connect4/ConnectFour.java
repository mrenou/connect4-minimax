package com.xebia.xke.algo.minimax.connect4;

import com.google.common.annotations.VisibleForTesting;

public class ConnectFour {

    private CounterColor currentCounterColor;

    @VisibleForTesting
    Board board = new Board();

    public ConnectFour() {
        currentCounterColor = CounterColor.YELLOW;
    }

    public Move putCounter(int columnIndex) {
        int verticalIndex = board.putCounter(columnIndex, currentCounterColor);
        if (verticalIndex != -1) {
            currentCounterColor = currentCounterColor.getOtherCounterColor();
        }
        return Move.createMove(currentCounterColor.getOtherCounterColor(), columnIndex, verticalIndex, board.getWinnerCounterColor() != null);
    }

    public boolean isEndGame() {
        return board.isFull() || board.getWinnerCounterColor() != null;
    }

    public CounterColor getCurrentCounterColor() {
        return currentCounterColor;
    }

    public Board getBoard() {
        return board;
    }
}
