package com.xebia.xke.algo.minimax.player.medium;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.Player;

public class PlayerIdiot implements Player {


    @Override
    public int play(CounterColor counterColor, Board board) {
        for (int columnIndex = 0; columnIndex < board.getNbColumns(); columnIndex++) {
          if (!board.columnIsfull(columnIndex)) {
            return columnIndex;
          }
        }
        return 0;
    }

    @Override
    public String getStats() {
        return null;
    }
}
