package com.xebia.xke.algo.minimax.player.easy;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.BoardFactory;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class StateConnectFourV1Test {

    @Test
    public void when_vertical_line_of_1_red_should_return_1() {
        Board board = BoardFactory.createBoard("/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(1);
    }

    @Test
    public void when_verticale_line_of_2_red_should_return_2() {
        Board board = BoardFactory.createBoard("/RR");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(2);
    }

    @Test
    public void when_vertical_line_of_3_red_should_return_3() {
        Board board = BoardFactory.createBoard("/RRR");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(3);
    }

    @Test
    public void when_vertical_line_of_4_red_should_return_999999999() {
        Board board = BoardFactory.createBoard("/RRRR");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(999999999);
    }

    @Test
    public void when_horizontal_line_of_1_red_should_return_1() {
        Board board = BoardFactory.createBoard("/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(1);
    }

    @Test
    public void when_horizontale_line_of_2_red_should_return_2() {
        Board board = BoardFactory.createBoard("/R/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(2);
    }

    @Test
    public void when_horizontal_line_of_3_red_should_return_3() {
        Board board = BoardFactory.createBoard("/R/R/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(3);
    }

    @Test
    public void when_horizontal_line_of_4_red_should_return_999999999() {
        Board board = BoardFactory.createBoard("/R/R/R/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(999999999);
    }

    @Test
    public void when_ascending_diag_line_of_1_red_should_return_1() {
        Board board = BoardFactory.createBoard("/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(1);
    }

    @Test
    public void when_ascending_diag_line_of_2_red_should_return_2() {
        Board board = BoardFactory.createBoard("/R/YR");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        // 2 - 1
        assertThat(minMaxMedium.getScore()).isEqualTo(1);
    }

    @Test
    public void when_ascending_diag_line_of_3_red_should_return_3() {
        Board board = BoardFactory.createBoard("/R/YR/RYR");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        // 3 - 2
        assertThat(minMaxMedium.getScore()).isEqualTo(1);
    }

    @Test
    public void when_ascending_diag_line_of_4_red_should_return_999999999() {
        Board board = BoardFactory.createBoard("/R/YR/RYR/YRRR");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(999999999);
    }

    @Test
    public void when_descending_diag_line_of_1_red_should_return_1() {
        Board board = BoardFactory.createBoard("/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(1);
    }

    @Test
    public void when_descending_diag_line_of_2_red_should_return_2() {
        Board board = BoardFactory.createBoard("/YR/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        // 2 - 1
        assertThat(minMaxMedium.getScore()).isEqualTo(1);
    }

    @Test
    public void when_descending_diag_line_of_3_red_should_return_3() {
        Board board = BoardFactory.createBoard("/RYR/RY/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        //3 - 2
        assertThat(minMaxMedium.getScore()).isEqualTo(1);
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
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(999999999);
    }

    @Test
    public void when_1_red_1_yellow_should_return_0() {
        Board board = BoardFactory.createBoard("/R//Y");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(0);
    }

    @Test
    public void when_column_full_with_colors_alternated_should_return_0() {
        Board board = BoardFactory.createBoard("RYRYRY");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(0);
    }

    @Test
    public void when_line_full_with_colors_alternated_should_return_1() {
        Board board = BoardFactory.createBoard("R/Y/R/Y/R/Y/R");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(0);
    }

    @Test
    public void when_2_red_1_yellow_should_return_0() {
        Board board = BoardFactory.createBoard("R/Y/R/R/Y/R/Y");
        StateConnectFourV1 minMaxMedium = new StateConnectFourV1(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);

        assertThat(minMaxMedium.getScore()).isEqualTo(1);
    }
}
