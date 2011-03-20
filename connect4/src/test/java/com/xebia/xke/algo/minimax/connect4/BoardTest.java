package com.xebia.xke.algo.minimax.connect4;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BoardTest {

    private Board board = new Board();

    @Test
    public void should_return_0_counters() {
        assertThat(board.getNumberOfCounters()).isEqualTo(0);
    }

    @Test
    public void should_return_3_counters() {
        board.putCounter(0, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);

        assertThat(board.getNumberOfCounters()).isEqualTo(3);
    }

    @Test
    public void should_be_at_correct_position_if_the_column_is_empty() {
        int verticalIndex = board.putCounter(0, CounterColor.RED);

        assertThat(verticalIndex).isGreaterThanOrEqualTo(0);
        assertThat(board.getNumberOfCounters()).isEqualTo(1);
        assertThat(board.getCounterColor(0, 0)).isEqualTo(CounterColor.RED);
    }

    @Test
    public void should_be_at_correct_position_if_the_column_has_counters() {

        int verticalIndex = board.putCounter(0, CounterColor.RED);
        assertThat(verticalIndex).isGreaterThanOrEqualTo(0);
        verticalIndex = board.putCounter(0, CounterColor.YELLOW);

        assertThat(verticalIndex).isGreaterThanOrEqualTo(0);
        assertThat(board.getNumberOfCounters()).isEqualTo(2);
        assertThat(board.getCounterColor(0, 0)).isEqualTo(CounterColor.RED);
        assertThat(board.getCounterColor(0, 1)).isEqualTo(CounterColor.YELLOW);
        assertThat(board.getCounterColor(0, 2)).isNull();
        assertThat(board.getCounterColor(1, 0)).isNull();
    }

    @Test
    public void should_not_put_counter_if_the_column_is_full() {
        Board board = BoardFactory.createBoard("RYRYRY");

        int verticalIndex = board.putCounter(0, CounterColor.YELLOW);

        assertThat(verticalIndex).isEqualTo(-1);
        assertThat(board.getNumberOfCounters()).isEqualTo(6);
        assertThat(board).isEqualTo(BoardFactory.createBoard("RYRYRY"));
    }

    @Test
    public void should_not_put_counter_out_of_board() {
        Board board = new Board();

        int verticalIndex = board.putCounter(8, CounterColor.RED);

        assertThat(verticalIndex).isEqualTo(-1);
        assertThat(board.getNumberOfCounters()).isEqualTo(0);
    }

    @Test
    public void should_is_in_board() {
        assertThat(board.isInBoard(3, 2)).isTrue();
        assertThat(board.isInBoard(0, 0)).isTrue();
        assertThat(board.isInBoard(0, 5)).isTrue();
        assertThat(board.isInBoard(6, 0)).isTrue();
        assertThat(board.isInBoard(6, 5)).isTrue();
    }

    @Test
    public void should_is_not_in_board() {
        assertThat(board.isInBoard(-2, -1)).isFalse();
        assertThat(board.isInBoard(0, 6)).isFalse();
        assertThat(board.isInBoard(7, 3)).isFalse();
        assertThat(board.isInBoard(6, -1)).isFalse();
        assertThat(board.isInBoard(7, 6)).isFalse();
    }

    @Test
    public void should_get_color_correct_color() {

        board.putCounter(0, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);

        assertThat(board.getCounterColor(0, 0)).isEqualTo(CounterColor.RED);
        assertThat(board.getCounterColor(0, 1)).isEqualTo(CounterColor.YELLOW);
        assertThat(board.getCounterColor(0, 2)).isNull();
        assertThat(board.getCounterColor(1, 0)).isEqualTo(CounterColor.RED);
        assertThat(board.getCounterColor(1, 1)).isEqualTo(CounterColor.YELLOW);
        assertThat(board.getCounterColor(1, 2)).isEqualTo(CounterColor.YELLOW);
        assertThat(board.getCounterColor(2, 0)).isEqualTo(CounterColor.YELLOW);
        assertThat(board.getCounterColor(2, 1)).isNull();
        assertThat(board.getCounterColor(2, 2)).isNull();
        assertThat(board.getCounterColor(3, 0)).isEqualTo(CounterColor.RED);
        assertThat(board.getCounterColor(3, 1)).isNull();
        assertThat(board.getCounterColor(3, 2)).isNull();
    }

    @Test
    public void should_return_true_if_the_column_is_full() {
        Board board = BoardFactory.createBoard("RYRYRY");

        assertThat(board.columnIsfull(0)).isTrue();
        assertThat(board.columnIsfull(1)).isFalse();
    }

    @Test
    public void should_return_false_if_the_column_is_not_full() {
        board.putCounter(0, CounterColor.RED);

        assertThat(board.columnIsfull(0)).isFalse();
    }


    @Test
    public void should_return_true_if_the_board_is_full() {
        Board board = BoardFactory.createBoard("RYRYRY/RYRYRY/RYRYRY/YRYRYR/RYRYRY/RYRYRY/RYRYRY");

        assertThat(board.columnIsfull(0)).isTrue();
    }

    @Test
    public void should_return_false_if_the_board_is_not_full() {
        board.putCounter(0, CounterColor.RED);

        assertThat(board.columnIsfull(0)).isFalse();
    }

    @Test
    public void should_return_false_if_there_is_counter() {
        board.putCounter(2, CounterColor.RED);

        assertThat(board.isEmpty(2, 0)).isFalse();
    }

    @Test
    public void should_return_true_if_there_is_not_counter() {
        board.putCounter(2, CounterColor.RED);

        assertThat(board.isEmpty(2, 1)).isTrue();
    }

    @Test
    public void should_return_true_if_playable_for_next_turn() {
        board.putCounter(2, CounterColor.RED);

        assertThat(board.isEmpty(2, 1)).isTrue();
        assertThat(board.isEmpty(3, 0)).isTrue();
    }

    @Test
    public void should_return_false_if_not_playable_for_next_turn() {
        board.putCounter(2, CounterColor.RED);

        assertThat(board.isPlayableForNextTurn(2, 2)).isFalse();
        assertThat(board.isPlayableForNextTurn(3, 1)).isFalse();
    }

    @Test
    public void should_return_inverse_board() {
        board.putCounter(1, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);
        board.putCounter(4, CounterColor.YELLOW);

        Board inverseBoard = board.getInverseBoard();

        assertThat(inverseBoard.getCounterColor(0, 0)).isNull();
        assertThat(inverseBoard.getCounterColor(1, 0)).isEqualTo(CounterColor.YELLOW);
        assertThat(inverseBoard.getCounterColor(1, 1)).isEqualTo(CounterColor.RED);
        assertThat(inverseBoard.getCounterColor(1, 2)).isNull();
        assertThat(inverseBoard.getCounterColor(2, 0)).isEqualTo(CounterColor.RED);
        assertThat(inverseBoard.getCounterColor(2, 1)).isEqualTo(CounterColor.YELLOW);
        assertThat(inverseBoard.getCounterColor(2, 2)).isEqualTo(CounterColor.RED);
        assertThat(inverseBoard.getCounterColor(2, 3)).isNull();
        assertThat(inverseBoard.getCounterColor(3, 0)).isEqualTo(CounterColor.YELLOW);
        assertThat(inverseBoard.getCounterColor(3, 1)).isEqualTo(CounterColor.RED);
        assertThat(inverseBoard.getCounterColor(3, 2)).isEqualTo(CounterColor.YELLOW);
        assertThat(inverseBoard.getCounterColor(3, 3)).isNull();
        assertThat(inverseBoard.getCounterColor(4, 0)).isEqualTo(CounterColor.RED);
        assertThat(inverseBoard.getCounterColor(4, 1)).isNull();
        assertThat(inverseBoard.getCounterColor(5, 0)).isNull();

    }

    @Test
    public void should_win_with_vertical_line_with_winning_counter_at_end() {
        Board board = new Board();

        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(0, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);

        /*
        * O
        * ox
        * ox
        * ox
        */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(7);
    }


    @Test
    public void should_win_with_horizontal_line_with_winning_counter_at_end() {
        Board board = new Board();

        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(0, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);

        /*
        * xxx
        * oooO
        */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(7);
    }

    @Test
    public void should_win_with_ascending_diagonal_line_with_winning_counter_at_end() {
        Board board = new Board();

        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(0, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);

        /**
         *    O
         *   ox
         * xooo
         * oxxx
         */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(11);
    }

    @Test
    public void should_win_with_descending_diagonal_line_with_winning_counter_at_end() {
        Board board = new Board();
        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(5, CounterColor.RED);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(5, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);
        board.putCounter(5, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);

        /**
         * o
         * xo   x
         * ooo  x
         * xxxO o
         */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(13);
    }


    @Test
    public void should_win_with_horizontal_line_with_winning_counter_at_start() {
        Board board = new Board();
        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(3, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);

        /*
        *  xxx
        * Oooo
        */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(7);
    }


    @Test
    public void should_win_with_ascending_diagonal_line_with_winning_counter_at_start() {
        Board board = new Board();
        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(5, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(5, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);
        board.putCounter(5, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);

        /**
         *    o
         *   ox x
         *  ooo x
         * Oxxx o
         */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(13);
    }

    @Test
    public void should_win_with_descending_diagonal_line_with_winning_counter_at_start() {
        Board board = new Board();
        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(3, CounterColor.RED);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);

        /**
         * O
         * xo
         * ooox
         * xxxo
         */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(11);
    }


    @Test
    public void should_win_with_horizontal_line_with_winning_counter_at_middle() {
        Board board = new Board();
        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(3, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);

        /*
        * xx x
        * ooOo
        */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(7);
    }


    @Test
    public void should_win_with_ascending_diagonal_line_with_winning_counter_at_middle() {
        Board board = new Board();
        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(0, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);
        board.putCounter(5, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);


        /**
         *    o
         *   Ox
         *  ooo
         * oxxx x
         */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(11);
    }


    @Test
    public void should_win_with_descending_diagonal_line_with_winning_counter_at_middle() {
        Board board = new Board();
        assertThat(board.getWinnerCounterColor()).isNull();

        board.putCounter(3, CounterColor.RED);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);
        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(0, CounterColor.RED);
        board.putCounter(5, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);

        /**
         * o
         * xO
         * ooo
         * xxxo x
         */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(11);
    }


    @Test
    public void should_win_with_ascending_diagonal_line_with_winning_counter_at_middle_on_right_bord_limit() {
        Board board = new Board();
        assertThat(board.getWinnerCounterColor()).isNull();


        board.putCounter(0, CounterColor.RED);
        board.putCounter(1, CounterColor.RED);
        board.putCounter(2, CounterColor.RED);
        board.putCounter(3, CounterColor.YELLOW);
        board.putCounter(4, CounterColor.YELLOW);
        board.putCounter(5, CounterColor.YELLOW);
        board.putCounter(6, CounterColor.RED);

        board.putCounter(0, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(2, CounterColor.YELLOW);
        board.putCounter(3, CounterColor.RED);
        board.putCounter(4, CounterColor.RED);
        board.putCounter(5, CounterColor.RED);
        board.putCounter(6, CounterColor.YELLOW);

        board.putCounter(3, CounterColor.RED);
        board.putCounter(4, CounterColor.YELLOW);
        board.putCounter(4, CounterColor.RED);
        board.putCounter(5, CounterColor.YELLOW);
        board.putCounter(5, CounterColor.RED);
        board.putCounter(6, CounterColor.YELLOW);
        board.putCounter(6, CounterColor.RED);
        board.putCounter(6, CounterColor.YELLOW);
        board.putCounter(6, CounterColor.RED);
        board.putCounter(4, CounterColor.YELLOW);
        board.putCounter(5, CounterColor.RED);

        /**      _
         *       o|
         *     xOx|
         *     ooo|
         *    oxxx|
         * xxxooox
         * oooxxxo
         */

        assertThat(board.getWinnerCounterColor()).isEqualTo(CounterColor.RED);
        assertThat(board.getNumberOfCounters()).isEqualTo(25);
    }

    @Test
    public void should_be_not_winner() {
        Board board = BoardFactory.createBoard("RYRYRY/RYRYRY/RYRYRY/YRYRYR/RYRYRY/RYRYRY/RYRYRY");

        assertThat(board.getWinnerCounterColor()).isNull();
        assertThat(board.getNumberOfCounters()).isEqualTo(42);
    }

    @Test
    public void should_have_winner_at_the_begining() {
        Board board = new Board();

        assertThat(board.getWinnerCounterColor()).isNull();
        assertThat(board.getNumberOfCounters()).isEqualTo(0);
    }
}
