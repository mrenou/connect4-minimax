package com.xebia.xke.algo.minimax.torename;

import com.google.common.annotations.VisibleForTesting;

public class AlphaBeta {

    public <T extends State> T alphabeta(T state) {
        T bestState = null;

        int bestScore = -State.BEST_SCORE;
        int alpha = -State.BEST_SCORE;
        int beta = +State.BEST_SCORE;

        for (T nextState : state.<T>nextStatesIterator()) {
            int score = getMinScore(nextState, 1, alpha, beta);
            if (score >= bestScore) {
                bestScore = score;
                bestState = nextState;
            }
        }
        return bestState;
    }

    @VisibleForTesting
    int getMaxScore(State state, int depth, int alpha, int beta) {
        if (state.isFinalState(depth)) {
            return state.getScore();
        }
        int maxScore = -State.BEST_SCORE;

        for (State childState : state.nextStatesIterator()) {
            int childScore = getMinScore(childState, depth + 1, alpha, beta);

            if (childScore > beta) {
                return childScore;
            }
            maxScore = max(maxScore, childScore);
            alpha = max(alpha, childScore);
        }
        return maxScore;
    }

    @VisibleForTesting
    int getMinScore(State state, int depth, int alpha, int beta) {
        if (state.isFinalState(depth)) {
            return state.getScore();
        }
        int minScore = State.BEST_SCORE;

        for (State childState : state.nextStatesIterator()) {
            int childScore = getMaxScore(childState, depth + 1, alpha, beta);

            if (childScore < alpha) {
                return childScore;
            }
            minScore = min(minScore, childScore);
            beta = min(beta, childScore);
        }
        return minScore;
    }

    @VisibleForTesting
    int max(int score1, int score2) {
        if (score1 > score2) {
            return score1;
        }
        return score2;
    }

    @VisibleForTesting
    int min(int score1, int score2) {
        if (score1 < score2) {
            return score1;
        }
        return score2;
    }
}
