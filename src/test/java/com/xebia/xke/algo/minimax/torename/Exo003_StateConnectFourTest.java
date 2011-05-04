package com.xebia.xke.algo.minimax.torename;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.BoardFactory;
import com.xebia.xke.algo.minimax.connect4.CounterColor;
import org.junit.Test;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

/**
 *  Exercice goal : Implement children states iteration. Implement final state test.
 *
 */
public class Exo003_StateConnectFourTest {


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
    public void stateC4_should_return_all_children_states() {
        //given
        Board board = BoardFactory.createBoard("R");
        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.YELLOW, 0, 3);

        //when
        Iterator<? extends State> childStatesIt = stateConnectFourTest.childStatesIterator().iterator();

        //then
        StateConnectFour nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("RY"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("R/Y"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("R//Y"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("R///Y"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("R////Y"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("R/////Y"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("R//////Y"));

        assertThat(childStatesIt.hasNext()).isFalse();

    }

    @Test
    public void stateC4_should_return_1_children_states_when_only_first_column_is_available() {
        //given
        Board board = BoardFactory.createBoard("/YRRYRY/YRYRRR/RYRRYR/RYRYYY/YYRYRY/YRYYRR");
        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.YELLOW, 0, 3);

        //when
        Iterator<? extends State> childStatesIt = stateConnectFourTest.childStatesIterator().iterator();

        //then
        assertThat(childStatesIt.hasNext()).isTrue();

        StateConnectFour nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("Y/YRRYRY/YRYRRR/RYRRYR/RYRYYY/YYRYRY/YRYYRR"));

        assertThat(childStatesIt.hasNext()).isFalse();
    }

    @Test
    public void stateC4_should_return_all_children_states_when_one_column_is_full() {
        //given
        Board board = BoardFactory.createBoard("/RYRYRY");
        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.YELLOW, 0, 3);

        //when
        Iterator<? extends State> childStatesIt = stateConnectFourTest.childStatesIterator().iterator();

        //then
        StateConnectFour nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("Y/RYRYRY/"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("/RYRYRY/Y"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("/RYRYRY//Y"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("/RYRYRY///Y"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("/RYRYRY////Y"));

        nextState = (StateConnectFour) childStatesIt.next();
        assertThat(nextState.board).isEqualTo(BoardFactory.createBoard("/RYRYRY/////Y"));

        assertThat(childStatesIt.hasNext()).isFalse();
    }

    @Test
    public void stateC4_should_return_no_children_states_when_board_is_full() {
        //given
        Board board = BoardFactory.createBoard("YRYRYR/YRYRYR/YRYRYR/RYRYRY/YRYRYR/YRYRYR/YRYRYR");
        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.YELLOW, 0, 3);

        //when
        Iterator<? extends State> childStatesIt = stateConnectFourTest.childStatesIterator().iterator();

        //then
        assertThat(childStatesIt.hasNext()).isFalse();
    }

    @Test
    public void stateC4_should_be_a_end_state_when_board_is_full() {
        //given
        Board board = BoardFactory.createBoard("YRYRYR/YRYRYR/YRYRYR/RYRYRY/YRYRYR/YRYRYR/YRYRYR");
        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.RED, 0, 3);

        //when
        boolean isFinalState = stateConnectFourTest.isFinalState(0);

        //then
        assertThat(isFinalState).isTrue();
    }

    @Test
    public void stateC4_should_be_a_end_state_when_depth_is_max_depth() {
        //given
        Board board = BoardFactory.createBoard("/Y");
        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.RED, 0, 3);

       //when
        boolean isFinalState = stateConnectFourTest.isFinalState(3);

        //then
        assertThat(isFinalState).isTrue();
    }

    @Test
    public void stateC4_should_be_a_end_state_when_player_wins() {
        //given
        Board board = BoardFactory.createBoard("YRYRYR/YRYRYR/YRYRYR/Y");
        StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.RED, 0, 3);

        //when
        boolean isFinalState = stateConnectFourTest.isFinalState(0);

        //then
        assertThat(isFinalState).isTrue();
    }

    @Test
    public void stateC4_should_not_be_a_end_state() {
        //given
        Board board = BoardFactory.createBoard("YRYRYR/YRYRYR/YRYRYR/RYRYRY/YRYRYR/YRYRYR/YRYRY");
                StateConnectFourTestImpl stateConnectFourTest = new StateConnectFourTestImpl(board, CounterColor.RED, CounterColor.RED, 0, 3);

        //when
        boolean isFinalState = stateConnectFourTest.isFinalState(0);

        //then
        assertThat(isFinalState).isFalse();
    }
}
