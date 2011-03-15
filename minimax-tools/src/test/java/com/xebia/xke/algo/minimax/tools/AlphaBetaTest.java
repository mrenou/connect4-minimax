package com.xebia.xke.algo.minimax.tools;

import com.xebia.xke.algo.minimax.tools.AlphaBeta;
import com.xebia.xke.algo.minimax.tools.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class AlphaBetaTest {

    private AlphaBeta alphabeta = new AlphaBeta();

    class StateTest extends State {

        int score;
        boolean isFinalState = false;
        Collection<StateTest> nextStates = new ArrayList<StateTest>();
        boolean explored = false;
        private int maxDepth;

        public StateTest(int maxDepth) {
            this.maxDepth = maxDepth;
        }

        public StateTest() {
            this.maxDepth = 1;
        }

        protected int getScore() {
            explored = true;
            return score;
        }

        @Override
        public Iterable<? extends State> nextStatesIterator() {

            //for (StateTest state : nextStates) {
            //    state.explored = true;
            //}
            return nextStates;
        }

        @Override
        boolean isFinalState(int depth) {
            return isFinalState || depth == maxDepth;
        }


    }

    @Test
    public void max_should_return_the_max_score() {
        int scoreReturned = alphabeta.max(1, 2);

        assertThat(scoreReturned).isEqualTo(2);
    }

    @Test
    public void min_should_return_the_min_score() {
        int scoreReturned = alphabeta.min(1, 2);

        assertThat(scoreReturned).isEqualTo(1);
    }

    @Test
    public void minmax_max_should_get_score_if_the_depth_is_1() {
        StateTest stateTest = new StateTest();

        stateTest.score = 42;

        assertThat(alphabeta.getMaxScore(stateTest, 1, 0, 0)).isEqualTo(42);
    }

    @Test
    public void minmax_min_should_get_score_if_the_depth_is_1() {
        StateTest stateTest = new StateTest();

        stateTest.score = 42;

        assertThat(alphabeta.getMinScore(stateTest, 1, 0, 0)).isEqualTo(42);
    }

    @Test
    public void minmax_max_should_get_score_if_is_final_state() {
        StateTest stateTest = new StateTest();

        stateTest.score = 42;
        stateTest.isFinalState = true;

        assertThat(alphabeta.getMinScore(stateTest, 3, 0, 0)).isEqualTo(42);
    }

    @Test
    public void minmax_min_should_get_score_if_is_final_state() {
        StateTest stateTest = new StateTest();

        stateTest.score = 42;
        stateTest.isFinalState = true;

        assertThat(alphabeta.getMinScore(stateTest, 3, 0, 0)).isEqualTo(42);
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

        assertThat(alphabeta.getMaxScore(stateTest, 0, -State.BEST_SCORE, State.BEST_SCORE)).isEqualTo(45);
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

        assertThat(alphabeta.getMinScore(stateTest, 0, -State.BEST_SCORE, State.BEST_SCORE)).isEqualTo(11);
    }

    @Test
    public void max_should_get_the_score_which_is_greater_than_beta() {
        StateTest stateTest = new StateTest();
        List<StateTest> nextStates = new ArrayList<StateTest>();

        StateTest nextState1 = new StateTest();
        nextState1.score = 11;
        nextStates.add(nextState1);
        StateTest nextState2 = new StateTest();
        nextState2.score = 32;
        nextStates.add(nextState2);
        StateTest nextState3 = new StateTest();
        nextState3.score = 45;
        nextStates.add(nextState3);

        stateTest.nextStates = nextStates;

        assertThat(alphabeta.getMaxScore(stateTest, 0, -State.BEST_SCORE, 20)).isEqualTo(32);
    }

    @Test
    public void min_should_get_the_score_which_is_less_than_alpha() {
        StateTest stateTest = new StateTest();
        List<StateTest> nextStates = new ArrayList<StateTest>();

        StateTest nextState1 = new StateTest();
        nextState1.score = 45;
        nextStates.add(nextState1);
        StateTest nextState2 = new StateTest();
        nextState2.score = 32;
        nextStates.add(nextState2);
        StateTest nextState3 = new StateTest();
        nextState3.score = 11;
        nextStates.add(nextState3);

        stateTest.nextStates = nextStates;

        assertThat(alphabeta.getMinScore(stateTest, 0, 40, State.BEST_SCORE)).isEqualTo(32);
    }

    @Test
    public void min_should_not_explore_states_with_score_which_is_less_than_alpha() {
        StateTest stateTest = new StateTest();

        StateTest nextState1 = new StateTest(2);
        stateTest.nextStates.add(nextState1);
        StateTest nextState2 = new StateTest(2);
        stateTest.nextStates.add(nextState2);

        StateTest nextState11 = new StateTest(2);
        nextState1.nextStates.add(nextState11);
        nextState11.score = 40;
        StateTest nextState12 = new StateTest(2);
        nextState1.nextStates.add(nextState12);
        nextState12.score = 60;

        StateTest nextState21 = new StateTest(2);
        nextState2.nextStates.add(nextState21);
        nextState21.score = 45;
        StateTest nextState22 = new StateTest(2);
        nextState2.nextStates.add(nextState22);
        nextState22.score = 35;
        StateTest nextState23 = new StateTest(2);
        nextState2.nextStates.add(nextState23);
        nextState23.score = 15;


        assertThat(alphabeta.getMaxScore(stateTest, 0, -State.BEST_SCORE, State.BEST_SCORE)).isEqualTo(40);
        assertThat(nextState23.explored).isFalse();
    }

    @Test
    public void max_should_not_explore_states_with_score_which_is_greater_than_beta() {
        StateTest stateTest = new StateTest();

        StateTest nextState1 = new StateTest(2);
        stateTest.nextStates.add(nextState1);
        StateTest nextState2 = new StateTest(2);
        stateTest.nextStates.add(nextState2);

        StateTest nextState11 = new StateTest(2);
        nextState1.nextStates.add(nextState11);
        nextState11.score = 10;
        StateTest nextState12 = new StateTest(2);
        nextState1.nextStates.add(nextState12);
        nextState12.score = 20;

        StateTest nextState21 = new StateTest(2);
        nextState2.nextStates.add(nextState21);
        nextState21.score = 15;
        StateTest nextState22 = new StateTest(2);
        nextState2.nextStates.add(nextState22);
        nextState22.score = 35;
        StateTest nextState23 = new StateTest(2);
        nextState2.nextStates.add(nextState23);
        nextState23.score = 45;


        assertThat(alphabeta.getMinScore(stateTest, 0, -State.BEST_SCORE, State.BEST_SCORE)).isEqualTo(20);
        assertThat(nextState23.explored).isFalse();

    }

}
