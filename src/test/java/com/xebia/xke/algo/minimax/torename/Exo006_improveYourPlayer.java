package com.xebia.xke.algo.minimax.torename;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.Match;
import com.xebia.xke.algo.minimax.connect4.SimplePlayer;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class Exo006_improveYourPlayer {

    class Opponent extends SimplePlayer {

        Queue<Integer>  moves = new LinkedList<Integer>();

        @Override
        public int play(CounterColor counterColor, Board board) {
            if (moves.size() > 0) {
                return moves.poll();
            }
            for (int columnIndex = 0; columnIndex < board.getNbColumns(); columnIndex++) {
                if (!board.columnIsfull(columnIndex)) {
                    return columnIndex;
                }
            }
            return 0;
        }
    }

    @Test
    public void improve_your_player() {
        Opponent opponent = new Opponent();

        opponent.moves.add(1);
        opponent.moves.add(2);
        opponent.moves.add(3);
        opponent.moves.add(4);

        Match match = new Match(new MyPlayer(), opponent, 0);

        match.play();
    }

}
