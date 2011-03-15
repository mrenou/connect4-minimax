package com.xebia.xke.algo.minimax.tools;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.BoardFactory;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.tools.State;
import com.xebia.xke.algo.minimax.tools.StateConnectFour;
import org.junit.Test;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class StateConnectFourTest {


    private class StateConnectFourTestImpl extends StateConnectFour {

        public StateConnectFourTestImpl(Board board, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
            super(board, counterColorTested, counterColorToPlay, columnIndexPlayed, maxDepth);
        }

        @Override
        protected StateConnectFour createNextState(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
            return new StateConnectFourTestImpl(boardCloned, counterColorTested, counterColorToPlay, columnIndexPlayed, 3);
        }

        @Override
        protected int getScore() {
            return 0;
        }
    }

        @Test
    public void returns_all_children_states() {
        Board board = new Board(3,3);

        board.putCounter(0, CounterColor.RED);

        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.YELLOW, 0, 3);

        Iterator<? extends State> nextStatesIt = stateConnectFourTest.nextStatesIterator().iterator();

        StateConnectFour nextState = (StateConnectFour) nextStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("RY", board.getNbColumns(), board.getColumnSize()));

        nextState = (StateConnectFour) nextStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("R/Y", board.getNbColumns(), board.getColumnSize()));

        nextState = (StateConnectFour) nextStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("R//Y", board.getNbColumns(), board.getColumnSize()));

        assertThat(nextStatesIt.hasNext()).isFalse();

    }

    @Test
    public void returns_all_children_states_when_only_first_column_is_available() {               
        Board board = BoardFactory.createBoard("/YRRYRY/YRYRRR/RYRRYR/RYRYYY/YYRYRY/YRYYRR");

        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.YELLOW, 0, 3);

        Iterator<? extends State> nextStatesIt = stateConnectFourTest.nextStatesIterator().iterator();

        assertThat(nextStatesIt.hasNext()).isTrue();

        StateConnectFour nextState = (StateConnectFour) nextStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("Y/YRRYRY/YRYRRR/RYRRYR/RYRYYY/YYRYRY/YRYYRR"));

        assertThat(nextStatesIt.hasNext()).isFalse();
    }

    @Test
    public void returns_all_children_states_when_column_is_full() {
        Board board = new Board(3,3);

        board.putCounter(1, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);

        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.YELLOW, 0, 3);

        Iterator<? extends State> nextStatesIt = stateConnectFourTest.nextStatesIterator().iterator();

        StateConnectFour nextState = (StateConnectFour) nextStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("Y/RYR/", board.getNbColumns(), board.getColumnSize()));

        nextState = (StateConnectFour) nextStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("/RYR/Y", board.getNbColumns(), board.getColumnSize()));

        assertThat(nextStatesIt.hasNext()).isFalse();
    }

    @Test
    public void returns_no_children_states_when_board_is_full() {
        Board board = new Board(3,3);

        board.putCounter(1, CounterColor.RED);
        board.putCounter(1, CounterColor.YELLOW);
        board.putCounter(1, CounterColor.RED);

        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.YELLOW, 0, 3);

        Iterator<? extends State> nextStatesIt = stateConnectFourTest.nextStatesIterator().iterator();

        StateConnectFour nextState = (StateConnectFour) nextStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("Y/RYR/", board.getNbColumns(), board.getColumnSize()));

        nextState = (StateConnectFour) nextStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("/RYR/Y", board.getNbColumns(), board.getColumnSize()));

        assertThat(nextStatesIt.hasNext()).isFalse();
    }

    @Test
    public void should_be_a_end_state() {
        Board board = BoardFactory.createBoard("YRY/YRY/YRY",3, 3);
        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.RED, 0, 3);

        assertThat(stateConnectFourTest.isFinalState(0)).isTrue();
    }

    @Test
    public void should_not_be_a_end_state() {
        Board board = BoardFactory.createBoard("YRY/YRY/YR",3, 3);
        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.RED, 0, 3);

        assertThat(stateConnectFourTest.isFinalState(0)).isFalse();
    }
}
