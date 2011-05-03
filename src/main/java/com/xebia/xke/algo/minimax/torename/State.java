package com.xebia.xke.algo.minimax.torename;

public abstract class State {

    public static final int BEST_SCORE = 999999999;

    abstract protected int getScore();

    abstract protected <T extends State>Iterable<T> nextStatesIterator();

    abstract protected boolean isFinalState(int depth);
}
