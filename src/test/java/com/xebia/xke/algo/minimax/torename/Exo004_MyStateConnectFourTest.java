package com.xebia.xke.algo.minimax.torename;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.BoardFactory;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 *  Exercice goal : Implement the scoring method with the following algo or your own algo  :
 *  score = your longest line size - opponent longest line size
 *
 */
public class Exo004_MyStateConnectFourTest {

    @Test
    public void when_vertical_line_of_1_red_should_return_1() {
        Board board = BoardFactory.createBoard("/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(1);
    }

    @Test
    public void when_verticale_line_of_2_red_should_return_2() {
        Board board = BoardFactory.createBoard("/RR");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(2);
    }

    @Test
    public void when_vertical_line_of_3_red_should_return_3() {
        Board board = BoardFactory.createBoard("/RRR");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(3);
    }

    @Test
    public void when_vertical_line_of_4_red_should_return_999999999() {
        Board board = BoardFactory.createBoard("/RRRR");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(State.BEST_SCORE);
    }

    @Test
    public void when_horizontal_line_of_1_red_should_return_1() {
        Board board = BoardFactory.createBoard("/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(1);
    }

    @Test
    public void when_horizontale_line_of_2_red_should_return_2() {
        Board board = BoardFactory.createBoard("/R/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(2);
    }

    @Test
    public void when_horizontal_line_of_3_red_should_return_3() {
        Board board = BoardFactory.createBoard("/R/R/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(3);
    }

    @Test
    public void when_horizontal_line_of_4_red_should_return_999999999() {
        Board board = BoardFactory.createBoard("/R/R/R/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(State.BEST_SCORE);
    }

    @Test
    public void when_ascending_diag_line_of_1_red_should_return_1() {
        Board board = BoardFactory.createBoard("/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(1);
    }

    @Test
    public void when_ascending_diag_line_of_2_red_should_return_2() {
        Board board = BoardFactory.createBoard("/R/YR");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        // 2 - 1
        assertThat(myState.getScore()).isEqualTo(1);
    }

    @Test
    public void when_ascending_diag_line_of_3_red_should_return_3() {
        Board board = BoardFactory.createBoard("/R/YR/RYR");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        // 3 - 2
        assertThat(myState.getScore()).isEqualTo(1);
    }

    @Test
    public void when_ascending_diag_line_of_4_red_should_return_999999999() {
        Board board = BoardFactory.createBoard("/R/YR/RYR/YRRR");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(State.BEST_SCORE);
    }

    @Test
    public void when_descending_diag_line_of_1_red_should_return_1() {
        Board board = BoardFactory.createBoard("/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(1);
    }

    @Test
    public void when_descending_diag_line_of_2_red_should_return_2() {
        Board board = BoardFactory.createBoard("/YR/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        // 2 - 1
        assertThat(myState.getScore()).isEqualTo(1);
    }

    @Test
    public void when_descending_diag_line_of_3_red_should_return_3() {
        Board board = BoardFactory.createBoard("/RYR/RY/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        //3 - 2
        assertThat(myState.getScore()).isEqualTo(1);
    }

    @Test
    public void when_descending_diag_line_of_4_red_should_return_999999999() {
        /**
         *r
         *rr
         *yrr
         *ryrr
         */

        Board board = BoardFactory.createBoard("/RYRR/YRR/RR/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(State.BEST_SCORE);
    }

    @Test
    public void when_1_red_1_yellow_should_return_0() {
        Board board = BoardFactory.createBoard("/R//Y");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(0);
    }

    @Test
    public void when_column_full_with_colors_alternated_should_return_0() {
        Board board = BoardFactory.createBoard("RYRYRY");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(0);
    }

    @Test
    public void when_line_full_with_colors_alternated_should_return_1() {
        Board board = BoardFactory.createBoard("R/Y/R/Y/R/Y/R");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(0);
    }

    @Test
    public void when_2_red_1_yellow_should_return_0() {
        Board board = BoardFactory.createBoard("R/Y/R/R/Y/R/Y");
        MyStateConnectFour myState = new MyStateConnectFour(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(myState.getScore()).isEqualTo(1);
    }
}
