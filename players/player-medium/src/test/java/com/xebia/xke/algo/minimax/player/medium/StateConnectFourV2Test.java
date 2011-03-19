package com.xebia.xke.algo.minimax.player.medium;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.BoardFactory;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class StateConnectFourV2Test {

    private abstract class EvalBoardTester {

        private Board board;

        public EvalBoardTester board(Board board) {
            this.board = board;
            return this;
        }

        public EvalBoardTester shouldHaveScore(int score) {
            assertThat(computeScore(board)).as(board.toString()).isEqualTo(score);

            // test inverse
            Board inverseBoard = board.getInverseBoard();
            assertThat(computeScore(inverseBoard)).as(inverseBoard.toString()).isEqualTo(-score);

            return this;
        }

        protected abstract int computeScore(Board board);
    }

    @Test
    public void shouldReturnCorrectScoreForHorizontalLines() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV2 stateConnectFourV2 = new StateConnectFourV2(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV2.getHorizontalScore();

            }
        };

        evalBoardTester.board(BoardFactory.createBoard("R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("//////R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("///R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("/Y/R/Y")).shouldHaveScore(-1);

        evalBoardTester.board(BoardFactory.createBoard("R/R")).shouldHaveScore(50);
        evalBoardTester.board(BoardFactory.createBoard("/////R/R")).shouldHaveScore(50);
        evalBoardTester.board(BoardFactory.createBoard("///R/R")).shouldHaveScore(250);
        evalBoardTester.board(BoardFactory.createBoard("/Y/R/R/Y")).shouldHaveScore(8);

        evalBoardTester.board(BoardFactory.createBoard("R/R/R")).shouldHaveScore(500);
        evalBoardTester.board(BoardFactory.createBoard("////R/R/R")).shouldHaveScore(500);
        evalBoardTester.board(BoardFactory.createBoard("//R/R/R")).shouldHaveScore(2500);
        evalBoardTester.board(BoardFactory.createBoard("/Y/R/R/R/Y")).shouldHaveScore(98);

        evalBoardTester.board(BoardFactory.createBoard("R/R/R/R")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("///R/R/R/R")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//R/R/R/R")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/Y/R/R/R/R/Y")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
    }

    @Test
    public void shouldReturnCorrectScoreForVerticalLines() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV2 stateConnectFourV2 = new StateConnectFourV2(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV2.getVerticalScore();

            }
        };

        evalBoardTester.board(BoardFactory.createBoard("R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("//////R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("///R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("/YRY")).shouldHaveScore(-1);

        evalBoardTester.board(BoardFactory.createBoard("RR")).shouldHaveScore(10);
        evalBoardTester.board(BoardFactory.createBoard("//////RR")).shouldHaveScore(10);
        evalBoardTester.board(BoardFactory.createBoard("////RR")).shouldHaveScore(10);
        evalBoardTester.board(BoardFactory.createBoard("/YRRY")).shouldHaveScore(8);

        evalBoardTester.board(BoardFactory.createBoard("RRR")).shouldHaveScore(100);
        evalBoardTester.board(BoardFactory.createBoard("////RRR")).shouldHaveScore(100);
        evalBoardTester.board(BoardFactory.createBoard("///RRR")).shouldHaveScore(100);
        evalBoardTester.board(BoardFactory.createBoard("/YRRRY")).shouldHaveScore(98);

        evalBoardTester.board(BoardFactory.createBoard("RRRR")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//////RRRR")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/RRRR")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/YRRRRY")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
    }

    @Test
    public void shouldReturnCorrectScoreForDiagonalAscLines() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV2 stateConnectFourV2 = new StateConnectFourV2(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV2.getDiagonalAscendingScore();

            }
        };

        evalBoardTester.board(BoardFactory.createBoard("R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("//////R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("///R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("/YRY")).shouldHaveScore(-1);

        // 1 + 50
        evalBoardTester.board(BoardFactory.createBoard("R/RR")).shouldHaveScore(51);
        // 1 + 10 + 50
        evalBoardTester.board(BoardFactory.createBoard("/////RR/RRR")).shouldHaveScore(61);
        // 1 + 50 + 250
        evalBoardTester.board(BoardFactory.createBoard("///RR/RRR")).shouldHaveScore(301);
        // 1 + 10
        evalBoardTester.board(BoardFactory.createBoard("/////R/RR")).shouldHaveScore(11);
        // 1 + 10 + 10 - 1 - 4
        evalBoardTester.board(BoardFactory.createBoard("//Y/RR/RRR/YYYY")).shouldHaveScore(16);


        // 1 + 50 + 500
        evalBoardTester.board(BoardFactory.createBoard("R/RR/RRR")).shouldHaveScore(551);
        // 1 + 10 + 100 + 500
        evalBoardTester.board(BoardFactory.createBoard("////RR/RRR/RRRR")).shouldHaveScore(611);
        // 1 + 50 + 500 + 2500
        evalBoardTester.board(BoardFactory.createBoard("///RR/RRR/RRRR")).shouldHaveScore(3051);
        // 1 + 10 + 100
        evalBoardTester.board(BoardFactory.createBoard("////R/RR/RRR")).shouldHaveScore(111);
        // 1 + 10 + 100 + 100 - 1 - 5
        evalBoardTester.board(BoardFactory.createBoard("//Y/RR/RRR/RRRR/YYYYY")).shouldHaveScore(205);

        evalBoardTester.board(BoardFactory.createBoard("R/RR/RRR/RRRR")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("///RR/RRR/RRRR/RRRRR")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//RR/RRR/RRRR/RRRRR")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("///R/RR/RRR/RRRR")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/Y/RR/RRR/RRRR/RRRRR/YYYYYY")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
    }

    @Test
    public void shouldReturnCorrectScoreForDiagonalDescLines() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV2 stateConnectFourV2 = new StateConnectFourV2(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV2.getDiagonalDescendingScore();

            }
        };

        evalBoardTester.board(BoardFactory.createBoard("R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("//////R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("///R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("/YRY")).shouldHaveScore(-1);

        // 1 + 10 + 50
        evalBoardTester.board(BoardFactory.createBoard("RRR/RR")).shouldHaveScore(61);
        // 1 + 50
        evalBoardTester.board(BoardFactory.createBoard("/////RR/R")).shouldHaveScore(51);
        // 1 + 50 + 250
        evalBoardTester.board(BoardFactory.createBoard("///RRR/RR")).shouldHaveScore(301);
        // 1 + 10
        evalBoardTester.board(BoardFactory.createBoard("RR/R/////")).shouldHaveScore(11);
        // 1 + 10 + 10 - 1 - 4
        evalBoardTester.board(BoardFactory.createBoard("//YYYY/RRR/RR/Y")).shouldHaveScore(16);


        // 1 + 10 + 100 + 500
        evalBoardTester.board(BoardFactory.createBoard("RRRR/RRR/RR")).shouldHaveScore(611);
        // 1 + 50 + 500
        evalBoardTester.board(BoardFactory.createBoard("////RRR/RR/R")).shouldHaveScore(551);
        // 1 + 50 + 500 + 2500
        evalBoardTester.board(BoardFactory.createBoard("///RRRR/RRR/RR")).shouldHaveScore(3051);
        // 1 + 10 + 100
        evalBoardTester.board(BoardFactory.createBoard("RRR/RR/R////")).shouldHaveScore(111);
        // 1 + 10 + 100 + 100 - 1 - 5
        evalBoardTester.board(BoardFactory.createBoard("//YYYYY/RRRR/RRR/RR/Y")).shouldHaveScore(205);

        evalBoardTester.board(BoardFactory.createBoard("RRRR/RRR/RR/R")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("RRRRR/RRRR/RRR/RR///")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//RRRRR/RRRR/RRR/RR")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("///RRRR/RRR/RR/R")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/YYYYYY/RRRRR/RRRR/RRR/RR/Y")).shouldHaveScore(StateConnectFourV2.BEST_SCORE);
    }

    @Test
    public void someTests() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV2 stateConnectFourV2 = new StateConnectFourV2(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV2.getScore();

            }
        };

        evalBoardTester.board(BoardFactory.createBoard("RYRYRY/YRYRYR/YRYRYR/RYRYRY/RYRYRY/YRYRYR/YRYRYR")).shouldHaveScore(0);

        /*
         * hscore = -180
         * vscore -18
         * dascscore =  71
         * ddesscore = -180
         *
         * RYYRRYR
         * YRYYYRY
         * YYYRRRY
         * RRRYYRR
         * RYRRYYY
         * RRYYYRY
         *
         */
        evalBoardTester.board(BoardFactory.createBoard("RRRYYR/RYRYRY/YRRYYY/YRYRYR/YYYRYR/RYRRRY/YYRYYR")).shouldHaveScore(-307);

         /*
         * hscore = -180
         * vscore -18
         * dascscore =  71
         * ddesscore = -180
         *
         * RYYRRYR
         * YRYYYRY
         * YYYRRRY
         * RRRYYRR
         * RYRRYYY
         * RRYYYRY
         *
         * hscore = -180
         * vscore -18
         * dascscore =  71
         * ddesscore = -180
         *
         * RYYRRYR
         * YRYYYRY
         * YYYRRRY
         * RRRYYRR
         * RYRRYYY
         * RRYYYRY
         *
         */
        evalBoardTester.board(BoardFactory.createBoard("RRRYYR/RYRYRY/YRRYYY/YRYRYR/YYYRYR/RYRRRY/YYRYYR")).shouldHaveScore(-307);
    }
}
