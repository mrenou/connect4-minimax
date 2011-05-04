package com.xebia.xke.algo.minimax.torename;

public class Minimax {

    public <T extends State> T minimax(T state) {
        T bestState = null;

        int bestScore = -State.BEST_SCORE;

        for (T childState : state.<T>childStatesIterator()) {
            int score = getMinScore(childState, 1);
            if (score > bestScore) {
                bestScore = score;
                bestState = childState;
            }
        }
        return bestState;
    }

    int getMaxScore(State state, int depth) {

        if (state.isFinalState(depth)) {
            return state.getScore();
        }
        int maxScore = -State.BEST_SCORE;

        for (State childState : state.childStatesIterator()) {
            maxScore = max(maxScore, getMinScore(childState, depth + 1));
        }
        return maxScore;
    }

    int getMinScore(State state, int depth) {

        if (state.isFinalState(depth)) {
            return state.getScore();
        }
        int minScore = State.BEST_SCORE;

        for (State childState : state.childStatesIterator()) {
            minScore = min(minScore, getMaxScore(childState, depth + 1));
        }
        return minScore;
    }

    int max(int score1, int score2) {
        if (score1 > score2) {
            return score1;
        }
        return score2;
    }

    int min(int score1, int score2) {
        if (score1 < score2) {
            return score1;
        }
        return score2;
    }
}
