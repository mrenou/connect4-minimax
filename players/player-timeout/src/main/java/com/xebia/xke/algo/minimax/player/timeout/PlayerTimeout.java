package com.xebia.xke.algo.minimax.player.timeout;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.SimplePlayer;

public class PlayerTimeout extends SimplePlayer {

    private String mode = "running";

    private int validTurn = 3;

    @Override
    public int play(CounterColor counterColor, Board board) {
        if (validTurn > 0) {
            validTurn--;
            for (int columnIndex = 0; columnIndex < board.getNbColumns(); columnIndex++) {
                if (!board.columnIsfull(columnIndex)) {
                    return columnIndex;
                }
            }
            return 0;
        }

        if (mode.equals("running")) {
            while (true) {

            }

        } else if (mode.equals("sleeping")) {
            Object flag = new Object();

            synchronized (flag) {
                try {
                    flag.wait();
                } catch (InterruptedException e) {
                    //
                }
            }
            return play(counterColor, board);
        }
        return 0;
    }

    @Override
    public String getStats() {
        return null;
    }
}
