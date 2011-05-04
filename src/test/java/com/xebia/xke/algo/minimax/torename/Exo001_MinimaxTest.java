package com.xebia.xke.algo.minimax.torename;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Exercice goal : Implement minimax algo.
 *
 */
public class Exo001_MinimaxTest {

    Minimax minimax = new Minimax();

    // Implementation of State just for the test
    class StateTest extends State {

        private int score;

        private boolean isFinalState = false;

        private int maxDepth = 1;

        private Collection<? extends State> nextStates;

        @Override
        public int getScore() {
            return score;
        }

        @Override
        public Iterable<? extends State> childStatesIterator() {
            return nextStates;
        }

        @Override
        public boolean isFinalState(int depth) {
            return isFinalState || maxDepth == depth;
        }
    }

    @Test
    public void max_should_return_the_max_score() {
        //given
        int minScore = 1;
        int maxScore = 2;

        //when
        int scoreReturned = minimax.max(minScore, maxScore);

        //then
        assertThat(scoreReturned).isEqualTo(maxScore);
    }

    @Test
    public void min_should_return_the_min_score() {
        //given
        int minScore = 1;
        int maxScore = 2;

        //when
        int scoreReturned = minimax.min(minScore, maxScore);

        //then
        assertThat(scoreReturned).isEqualTo(minScore);
    }

    @Test
    public void min_score_should_get_the_min_score_of_children() {
        //given
        StateTest stateTest = new StateTest();
        stateTest.score = 42;
        stateTest.nextStates = getChildrenStates(32, 45, 11);

        //when
        int scoreReturned = minimax.getMinScore(stateTest, 0);

        //then
        assertThat(scoreReturned).isEqualTo(11);
    }

    @Test
    public void max_score_should_get_the_max_score_of_children() {
        //given
        StateTest stateTest = new StateTest();
        stateTest.score = 42;
        stateTest.nextStates = getChildrenStates(32, 45, 11);

        //when
        int scoreReturned = minimax.getMaxScore(stateTest, 0);

        //then
        assertThat(scoreReturned).isEqualTo(45);
    }

    @Test
    public void minimax_should_return_score_if_is_final_state() {
        //given
        StateTest stateTest = new StateTest();
        stateTest.score = 42;
        stateTest.nextStates = getChildrenStates(32, 45, 11);
        stateTest.isFinalState = true;

        //when
        int scoreReturnedByMin = minimax.getMinScore(stateTest, 0);
        int scoreReturnedByMax = minimax.getMaxScore(stateTest, 0);

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

        //when
        int scoreReturnedByMin = minimax.getMinScore(stateTest, 1);
        int scoreReturnedByMax = minimax.getMaxScore(stateTest, 1);

        //then
        assertThat(scoreReturnedByMin).isEqualTo(42);
        assertThat(scoreReturnedByMax).isEqualTo(42);
    }

    @Test
    public void minimax_should_return_the_best_child_state() {
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
        StateTest stateTestReturned = minimax.minimax(stateTest);

        //then
        assertThat(stateTestReturned).isSameAs(childState2);
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
