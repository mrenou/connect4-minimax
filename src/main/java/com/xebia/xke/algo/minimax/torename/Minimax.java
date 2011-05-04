package com.xebia.xke.algo.minimax.torename;

public class Minimax {

    public <T extends State> T minimax(T state) {
        T bestState = null;

        int bestScore = -State.BEST_SCORE;

        //FIXME select the state with the lowest score among the states child :
        //      - use getMinScore
        //      - increase the depth

        return bestState;
    }

    int getMaxScore(State state, int depth) {

        //FIXME if the state is a final state, return the score of the state

        int maxScore = -State.BEST_SCORE;

        //FIXME - select the highest score among the states child :
        //      - increase the depth
        //      - use max method

        return maxScore;
    }

    int getMinScore(State state, int depth) {

        //FIXME if the state is a final state, return the score of the state

        int minScore = State.BEST_SCORE;

        //FIXME - select the lowest score among the states child :
        //      - increase the depth
        //      - use min method

        return minScore;
    }

    int max(int score1, int score2) {
        //FIXME select the highest score
        return 0;
    }

    int min(int score1, int score2) {
        //FIXME select the lowest score
        return 0;
    }
}
