package com.xebia.xke.algo.minimax.tools;

import com.xebia.xke.algo.minimax.tools.Minimax;
import com.xebia.xke.algo.minimax.tools.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class MinimaxTest {

    private Minimax minimax = new Minimax();

    class StateTest extends State {

        int score;
        boolean isFinalState = false;
        Collection<? extends State> nextStates;

        protected int getScore() {
            return score;
        }

        @Override
        public Iterable<? extends State> nextStatesIterator() {
            return nextStates;
        }

        @Override
        boolean isFinalState(int depth) {
            return isFinalState || depth == 1;
        }
    }

    @Test
    public void max_should_return_the_max_score() {
        int scoreReturned = minimax.max(1, 2);

        assertThat(scoreReturned).isEqualTo(2);
    }

    @Test
    public void min_should_return_the_min_score() {
        int scoreReturned = minimax.min(1, 2);

        assertThat(scoreReturned).isEqualTo(1);
    }

    @Test
    public void minmax_max_should_get_score_if_the_depth_is_1() {
        StateTest stateTest = new StateTest();

        stateTest.score = 42;

        assertThat(minimax.getMaxScore(stateTest, 1)).isEqualTo(42);
    }

    @Test
    public void minmax_min_should_get_score_if_the_depth_is_1() {
        StateTest stateTest = new StateTest();

        stateTest.score = 42;

        assertThat(minimax.getMinScore(stateTest, 1)).isEqualTo(42);
    }

    @Test
    public void minmax_max_should_get_score_if_is_final_state() {
        StateTest stateTest = new StateTest();

        stateTest.score = 42;
        stateTest.isFinalState = true;

        assertThat(minimax.getMinScore(stateTest, 3)).isEqualTo(42);
    }

    @Test
    public void minmax_min_should_get_score_if_is_final_state() {
        StateTest stateTest = new StateTest();

        stateTest.score = 42;
        stateTest.isFinalState = true;

        assertThat(minimax.getMinScore(stateTest, 3)).isEqualTo(42);
    }

    @Test
    public void max_should_get_the_max_score_of_children() {
        StateTest stateTest = new StateTest();
        List<StateTest> nextStates = new ArrayList<StateTest>();

        StateTest nextState1 = new StateTest();
        nextState1.score = 32;
        nextStates.add(nextState1);
        StateTest nextState2 = new StateTest();
        nextState2.score = 45;
        nextStates.add(nextState2);
        StateTest nextState3 = new StateTest();
        nextState3.score = 11;
        nextStates.add(nextState3);

        stateTest.nextStates = nextStates;

        assertThat(minimax.getMaxScore(stateTest, 0)).isEqualTo(45);
    }

    @Test
    public void min_should_get_the_min_score_of_children() {
        StateTest stateTest = new StateTest();
        List<StateTest> nextStates = new ArrayList<StateTest>();

        StateTest nextState1 = new StateTest();
        nextState1.score = 32;
        nextStates.add(nextState1);
        StateTest nextState2 = new StateTest();
        nextState2.score = 45;
        nextStates.add(nextState2);
        StateTest nextState3 = new StateTest();
        nextState3.score = 11;
        nextStates.add(nextState3);

        stateTest.nextStates = nextStates;

        assertThat(minimax.getMinScore(stateTest, 0)).isEqualTo(11);
    }

}
