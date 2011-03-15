package com.xebia.xke.algo.minimax.tools;

public class AlphaBeta {

    private int nbStatesExploredLastTurn;

    private int nbStatesExplored;

    private int nbTurns = 0;

    public <T extends State> T minimax(T state) {
        T bestState = null;

        int bestScore = -State.BEST_SCORE;
        int alpha = -State.BEST_SCORE;
        int beta = +State.BEST_SCORE;
        nbStatesExploredLastTurn = 0;
        nbTurns++;

        for (T nextState : state.<T>nextStatesIterator()) {
            int score = getMinScore(nextState, 1, alpha, beta);
            if (score >= bestScore) {
                bestScore = score;
                bestState = nextState;
            }
        }
        return bestState;
    }

    int getMaxScore(State state, int depth, int alpha, int beta) {
        nbStatesExplored ++;
        nbStatesExploredLastTurn++;

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

    int getMinScore(State state, int depth, int alpha, int beta) {
        nbStatesExplored++;
        nbStatesExploredLastTurn++;

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

    public String getLastTurnStats() {
        return "Nb states explored : " + nbStatesExploredLastTurn;
    }

    public String getStats() {
        return "Average ofnb states explored : " + ((double)nbStatesExplored / nbTurns);
    }
}
