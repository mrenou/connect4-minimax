package com.xebia.xke.algo.minimax.connect4;

import com.google.common.annotations.VisibleForTesting;

public class ConnectFour {

    private CounterColor currentCounterColor;
    private CounterColor winnerCounterColor;

    @VisibleForTesting
    Board board = new Board();

    public ConnectFour() {
        currentCounterColor = CounterColor.YELLOW;
    }

    public boolean putCounter(int columnIndex) {
        //TODO stop if end game
        int verticalIndex = board.putCounter(columnIndex, currentCounterColor);
        if (verticalIndex != -1) {
            currentCounterColor = getNotCurrentCounterColor();
            winnerCounterColor = board.getWinnerCounterColor();
            return true;
        }
        return false;
    }

    public CounterColor getNotCurrentCounterColor() {
        return currentCounterColor.getOtherCounterColor();
    }

    public boolean isEndGame() {
        return board.isFull();
    }

    public CounterColor getCurrentCounterColor() {
        return currentCounterColor;
    }

    public CounterColor getCounterColorOfWinner() {
        return winnerCounterColor;
    }

    public Board getBoard() {
        return board;
    }
}
