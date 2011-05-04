package com.xebia.xke.algo.minimax.torename;

import com.google.common.annotations.VisibleForTesting;

public class AlphaBeta {

    public <T extends State> T alphabeta(T state) {
        T bestState = null;

        int bestScore = -State.BEST_SCORE;
        int alpha = -State.BEST_SCORE;
        int beta = +State.BEST_SCORE;

        //FIXME select the highest score among the states child :
        //      - increase the depth
        //      - use max method

        return bestState;
    }

    @VisibleForTesting
    int getMaxScore(State state, int depth, int alpha, int beta) {
        //FIXME if the state is a final state, return the score of the state

        int maxScore = -State.BEST_SCORE;

        //FIXME - select the highest score among the states child :
        //      - increase the depth
        //      - if child score > beta, return the score immediately (prune)
        //      - update alpha value, the highest score among the states child (same as max score)
        //      - use max method

        return maxScore;
    }

    @VisibleForTesting
    int getMinScore(State state, int depth, int alpha, int beta) {
        //FIXME if the state is a final state, return the score of the state

        int minScore = State.BEST_SCORE;

        //FIXME - select the lowest score among the states child :
        //      - increase the depth
        //      - if child score < alpha, return the score immediately (prune)
        //      - update beta value, the lowest score among the states child (same as min score)
        //      - use max method

        return minScore;
    }

    @VisibleForTesting
    int max(int score1, int score2) {
        //FIXME select the highest score
        return 0;
    }

    @VisibleForTesting
    int min(int score1, int score2) {
        //FIXME select the lowest score
        return 0;
    }
}
