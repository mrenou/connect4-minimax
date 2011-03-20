package com.xebia.xke.algo.minimax.connect4;

import java.util.Random;

public class ConnectFour {

    private CounterColor currentCounterColor;
    private CounterColor winnerCounterColor;

    // package for test
    Board board;

    private int winningLineSize;

    public ConnectFour(ConnectFourConfig connectFourConfig) {
        board = new Board(connectFourConfig.getNbColumns(), connectFourConfig.getColumnSize());
        currentCounterColor = CounterColor.YELLOW;
        winningLineSize = connectFourConfig.getWinningLineSize();
    }

    public ConnectFour() {
        this(ConnectFourConfig.create());
    }

    public boolean putCounter(int columnIndex) {
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
