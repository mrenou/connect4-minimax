package com.xebia.xke.algo.minimax.torename;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Exercice goal : Implement alphabeta algo.
 *
 */
public class Exo002_AlphaBetaTest {

    private AlphaBeta alphabeta = new AlphaBeta();

    class StateTest extends State {

        int score;
        boolean isFinalState = false;
        Collection<StateTest> nextStates = new ArrayList<StateTest>();
        boolean scored = false;
        private int maxDepth;

        public StateTest(int maxDepth) {
            this.maxDepth = maxDepth;
        }

        public StateTest() {
            this.maxDepth = 1;
        }

        protected int getScore() {
            scored = true;
            return score;
        }

        @Override
        public Iterable<? extends State> nextStatesIterator() {

            //for (StateTest state : nextStates) {
            //    state.scored = true;
            //}
            return nextStates;
        }

        @Override
        protected boolean isFinalState(int depth) {
            return isFinalState || depth == maxDepth;
        }


    }


    @Test
    public void max_should_return_the_max_score() {
        //given
        int minScore = 1;
        int maxScore = 2;

        //when
        int scoreReturned = alphabeta.max(minScore, maxScore);

        //then
        assertThat(scoreReturned).isEqualTo(maxScore);
    }

    @Test
    public void min_should_return_the_min_score() {
        //given
        int minScore = 1;
        int maxScore = 2;

        //when
        int scoreReturned = alphabeta.min(minScore, maxScore);

        //then
        assertThat(scoreReturned).isEqualTo(minScore);
    }

    @Test
    public void min_score_should_get_the_min_score_of_children() {
        //given
        StateTest stateTest = new StateTest();
        stateTest.score = 42;
        stateTest.nextStates = getChildrenStates(32, 45, 11);

        int alpha = -State.BEST_SCORE;
        int beta = State.BEST_SCORE;

        //when
        int scoreReturned = alphabeta.getMinScore(stateTest, 0, alpha, beta);

        //then
        assertThat(scoreReturned).isEqualTo(11);
    }

    @Test
    public void max_score_should_get_the_max_score_of_children() {
        //given
        StateTest stateTest = new StateTest();
        stateTest.score = 42;
        stateTest.nextStates = getChildrenStates(32, 45, 11);

        int alpha = -State.BEST_SCORE;
        int beta = State.BEST_SCORE;

        //when
        int scoreReturned = alphabeta.getMaxScore(stateTest, 0, alpha, beta);

        //then
        assertThat(scoreReturned).isEqualTo(45);
    }

    @Test
    public void alphabeta_should_return_score_if_is_final_state() {
        //given
        StateTest stateTest = new StateTest();
        stateTest.score = 42;
        stateTest.nextStates = getChildrenStates(32, 45, 11);
        stateTest.isFinalState = true;

        int alpha = -State.BEST_SCORE;
        int beta = State.BEST_SCORE;

        //when
        int scoreReturnedByMin = alphabeta.getMinScore(stateTest, 0, alpha, beta);
        int scoreReturnedByMax = alphabeta.getMaxScore(stateTest, 0, alpha, beta);

        //then
        assertThat(scoreReturnedByMin).isEqualTo(42);
        assertThat(scoreReturnedByMax).isEqualTo(42);
    }

    @Test
    public void minmax_should_return_score_if_the_depth_is_1() {
        //given
        StateTest stateTest = new StateTest();
        stateTest.score = 42;
        stateTest.nextStates = getChildrenStates(32, 45, 11);

        int alpha = -State.BEST_SCORE;
        int beta = State.BEST_SCORE;

        //when
        int scoreReturnedByMin = alphabeta.getMinScore(stateTest, 1, alpha, beta);
        int scoreReturnedByMax = alphabeta.getMaxScore(stateTest, 1, alpha, beta);

        //then
        assertThat(scoreReturnedByMin).isEqualTo(42);
        assertThat(scoreReturnedByMax).isEqualTo(42);
    }

    @Test
    public void alphabeta_should_return_the_best_child_state() {
        //given
        StateTest stateTest = new StateTest();
        List<StateTest> childrenStates = new ArrayList<StateTest>();

        StateTest childState1 = new StateTest();
        childState1.score = 32;
        childrenStates.add(childState1);

        StateTest childState2 = new StateTest();
        childState2.score = 45;
        childrenStates.add(childState2);

        StateTest childState3 = new StateTest();
        childState3.score = 11;
        childrenStates.add(childState3);

        stateTest.nextStates = childrenStates;

        //when
        StateTest stateTestReturned = alphabeta.alphabeta(stateTest);

        //then
        assertThat(stateTestReturned).isSameAs(childState2);
    }

    @Test
    public void alphabeta_should_return_the_first_score_which_is_greater_than_beta() {
        //given
        StateTest stateTest = new StateTest();
        stateTest.nextStates = getChildrenStates(11, 32, 45);

        int alpha = -State.BEST_SCORE;
        int beta = 20;

        //when
        int scoreReturned = alphabeta.getMaxScore(stateTest, 0, alpha, beta);

        //then
        assertThat(scoreReturned).isEqualTo(32);
    }

    @Test
    public void alphabeta_should_return_the_first_score_which_is_less_than_alpha() {
        //given
        StateTest stateTest = new StateTest();
        stateTest.nextStates = getChildrenStates(45, 32, 11);

        int alpha = 40;
        int beta = State.BEST_SCORE;

        //when
        int scoreReturned = alphabeta.getMinScore(stateTest, 0, alpha, beta);

        //then
        assertThat(scoreReturned).isEqualTo(32);
    }

    @Test
    public void alphabeta_should_not_explore_states_with_score_which_is_less_than_alpha() {
        //Given
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

        int alpha = -State.BEST_SCORE;
        int beta = State.BEST_SCORE;

        //when
        int scoreReturned = alphabeta.getMaxScore(stateTest, 0, alpha, beta);

        //then
        assertThat(scoreReturned).isEqualTo(40);
        assertThat(nextState1.scored).isFalse();
        assertThat(nextState11.scored).isTrue();
        assertThat(nextState12.scored).isTrue();
        assertThat(nextState2.scored).isFalse();
        assertThat(nextState21.scored).isTrue();
        assertThat(nextState22.scored).isTrue();
        assertThat(nextState23.scored).isFalse();
    }

    @Test
    public void max_should_not_explore_states_with_score_which_is_greater_than_beta() {
        //given
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

        int alpha = -State.BEST_SCORE;
        int beta = State.BEST_SCORE;

        //when
        int scoreReturned = alphabeta.getMinScore(stateTest, 0, alpha, beta);

        //then
        assertThat(scoreReturned).isEqualTo(20);
        assertThat(nextState1.scored).isFalse();
        assertThat(nextState11.scored).isTrue();
        assertThat(nextState12.scored).isTrue();
        assertThat(nextState2.scored).isFalse();
        assertThat(nextState21.scored).isTrue();
        assertThat(nextState22.scored).isTrue();
        assertThat(nextState23.scored).isFalse();
    }

    private List<StateTest> getChildrenStates(int... scores) {
        List<StateTest> childrenStates = new ArrayList<StateTest>();

        for (int score : scores) {
            StateTest childState1 = new StateTest();
            childState1.score = score;
            childrenStates.add(childState1);
        }

        return childrenStates;
    }
}
