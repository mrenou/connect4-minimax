package com.xebia.xke.algo.minimax.player.medium;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.BoardFactory;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.tools.StateConnectFour;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class StateConnectFourV3Test {

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
                StateConnectFourV3 stateConnectFourV3 = new StateConnectFourV3(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV3.getHorizontalScore();

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
    }

    @Test
    public void shouldReturnCorrectScoreForVerticalLines() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV3 stateConnectFourV3 = new StateConnectFourV3(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV3.getVerticalScore();

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
    }

    @Test
    public void shouldReturnCorrectScoreForDiagonalAscLines() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV3 stateConnectFourV3 = new StateConnectFourV3(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV3.getDiagonalAscendingScore();

            }
        };

        evalBoardTester.board(BoardFactory.createBoard("R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("//////R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("///R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("/YRY")).shouldHaveScore(-1);

        // 1 + 10
        evalBoardTester.board(BoardFactory.createBoard("R/RR")).shouldHaveScore(11);
        // 1 + 10 + 50
        evalBoardTester.board(BoardFactory.createBoard("R/RR/RR")).shouldHaveScore(61);
        // 1 + 10 + 50
        evalBoardTester.board(BoardFactory.createBoard("/////RR/RRR")).shouldHaveScore(61);
        // 1 + 10 + + 10 + 50
        evalBoardTester.board(BoardFactory.createBoard("/////RRR/RRRR")).shouldHaveScore(71);
        // 1 + 10 + 100 + 250
        evalBoardTester.board(BoardFactory.createBoard("///RR/RRR/RRR")).shouldHaveScore(361);
        // 1 + 10
        evalBoardTester.board(BoardFactory.createBoard("/////R/RR")).shouldHaveScore(11);
        // 1 + 10 + 10 - 1 - 4
        evalBoardTester.board(BoardFactory.createBoard("//Y/RR/RRR/YYYY")).shouldHaveScore(16);


        // 1 + 10 + 100
        evalBoardTester.board(BoardFactory.createBoard("R/RR/RRR")).shouldHaveScore(111);
        // 1 + 10 + 100 + 500
        evalBoardTester.board(BoardFactory.createBoard("R/RR/RRR/RRR")).shouldHaveScore(611);
        // 1 + 10 + 100 + 500
        evalBoardTester.board(BoardFactory.createBoard("////RR/RRR/RRRR")).shouldHaveScore(611);
        // 1 + 10 + 100 + 500
        evalBoardTester.board(BoardFactory.createBoard("////RRR/RRRR/RRRRR")).shouldHaveScore(711);
        // 1 + 10 + 100 + 100 + 2500 -1
        evalBoardTester.board(BoardFactory.createBoard("///RR/RRR/RRRR/RRRY")).shouldHaveScore(2710);
        // 1 + 10 + 100
        evalBoardTester.board(BoardFactory.createBoard("////R/RR/RRR")).shouldHaveScore(111);
        // 1 + 10 + 100 + 100 - 1 - 5
        evalBoardTester.board(BoardFactory.createBoard("//Y/RR/RRR/RRRR/YYYYY")).shouldHaveScore(205);
    }

    @Test
    public void shouldReturnCorrectScoreForDiagonalDescLines() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV3 stateConnectFourV3 = new StateConnectFourV3(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV3.getDiagonalDescendingScore();

            }
        };

        evalBoardTester.board(BoardFactory.createBoard("R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("//////R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("///R")).shouldHaveScore(1);
        evalBoardTester.board(BoardFactory.createBoard("/YRY")).shouldHaveScore(-1);

        // 1 + 10 + 10 + 50
        evalBoardTester.board(BoardFactory.createBoard("RRRR/RRR")).shouldHaveScore(71);
        // 1 + 10 + 50
        evalBoardTester.board(BoardFactory.createBoard("RRR/RR")).shouldHaveScore(61);
        // 1 + 10
        evalBoardTester.board(BoardFactory.createBoard("/////RR/R")).shouldHaveScore(11);
        // 1 + 10 + 50
        evalBoardTester.board(BoardFactory.createBoard("////RR/RR/R")).shouldHaveScore(61);
        // 1 + 10 + 100 + 250
        evalBoardTester.board(BoardFactory.createBoard("//RRR/RRR/RR")).shouldHaveScore(361);
        // 1 + 10
        evalBoardTester.board(BoardFactory.createBoard("RR/R/////")).shouldHaveScore(11);
        // 1 + 10 + 10 - 1 - 4
        evalBoardTester.board(BoardFactory.createBoard("//YYYY/RRR/RR/Y")).shouldHaveScore(16);


        // 1 + 10 + 100 + 100 + 500
        evalBoardTester.board(BoardFactory.createBoard("RRRRR/RRRR/RRR")).shouldHaveScore(711);
        // 1 + 10 + 100 + 500
        evalBoardTester.board(BoardFactory.createBoard("RRRR/RRR/RR")).shouldHaveScore(611);
        // 1 + 10 + 100
        evalBoardTester.board(BoardFactory.createBoard("////RRR/RR/R")).shouldHaveScore(111);
        // 1 + 10 + 100 + 500
        evalBoardTester.board(BoardFactory.createBoard("///RRR/RRR/RR/R")).shouldHaveScore(611);
        // 1 + 10 + 100 + 100 + 2500 -1
        evalBoardTester.board(BoardFactory.createBoard("//RRRY/RRRR/RRR/RR")).shouldHaveScore(2710);
        // 1 + 10 + 100
        evalBoardTester.board(BoardFactory.createBoard("RRR/RR/R////")).shouldHaveScore(111);
        // 1 + 10 + 100 + 100 - 1 - 5
        evalBoardTester.board(BoardFactory.createBoard("//YYYYY/RRRR/RRR/RR/Y")).shouldHaveScore(205);
    }

    @Test
    public void someTests() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV3 stateConnectFourV3 = new StateConnectFourV3(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV3.getScore();

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
        * hscore = +9 / +10 -1 -1 +1 -1 +1 -1 +1 -100 +100 +50 -50 +1 -1
        * vscore = +90 / +100 +1 -1 +1 -1 -1 +10 -10 -10 +1 -1 +10 -10 +1
        * dascscore = 1 / +1 -10 +10 -1 +1 -1 +1 +10 -1 +1 -1 +1 -10 +1 +1 -1 -1
        * ddesscore = +209 / +1 +10 +1 -10 +250 -1 +1 -1 -1 -1 +1 -1 -50 -1 +1 +10
        *
        * .......
        * ..Y....
        * .YY..R.
        * RRR..RR
        * RYR.YYY
        * RRY.YRY
        *
        *
        */
        evalBoardTester.board(BoardFactory.createBoard("RRR/RYRY/YRRYY//YY/RYRR/YYR")).shouldHaveScore(309);
    }

    @Test
    public void winningStatesShouldBeReturnsBestScore() {

        EvalBoardTester evalBoardTester = new EvalBoardTester() {
            @Override
            protected int computeScore(Board board) {
                StateConnectFourV3 stateConnectFourV3 = new StateConnectFourV3(board, CounterColor.RED, CounterColor.YELLOW, 1, 3);
                return stateConnectFourV3.getScore();

            }
        };


        evalBoardTester.board(BoardFactory.createBoard("R/R/R/R")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("///R/R/R/R")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//R/R/R/R")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/Y/R/R/R/R/Y")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);

        evalBoardTester.board(BoardFactory.createBoard("RRRR")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//////RRRR")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/RRRR")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/YRRRRY")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);

        evalBoardTester.board(BoardFactory.createBoard("R/RR/RRR/RRRR")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("///RR/RRR/RRRR/RRRRR")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//RR/RRR/RRRR/RRRRR")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("///R/RR/RRR/RRRR")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/Y/RR/RRR/YRRR/YYRRR/RRRYYY")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);

        evalBoardTester.board(BoardFactory.createBoard("RRRR/RRR/RR/R")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("RRRRR/RRRR/RRR/RR///")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//RRRRR/RRRR/RRR/RR")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("///RRRR/RRR/RR/R")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/RRRYYY/YYYRR/RRRR/YRR/RR/Y")).shouldHaveScore(StateConnectFourV3.BEST_SCORE);

        evalBoardTester.board(BoardFactory.createBoard("/RRRR")).shouldHaveScore(StateConnectFour.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("/R/R/R/R")).shouldHaveScore(StateConnectFour.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("R/RR/RRR/YRRR")).shouldHaveScore(StateConnectFour.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//R/RR/RRR/YRRR")).shouldHaveScore(StateConnectFour.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("///RRRY/RRR/RR/R")).shouldHaveScore(StateConnectFour.BEST_SCORE);
        evalBoardTester.board(BoardFactory.createBoard("//RRRY/RRR/RR/R/")).shouldHaveScore(StateConnectFour.BEST_SCORE);
    }
}
