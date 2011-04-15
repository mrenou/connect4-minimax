package com.xebia.xke.algo.minimax.tools;

public abstract class State {

    public static final int BEST_SCORE = 999999999;

    protected abstract int getScore();

    public abstract <T extends State>Iterable<T> nextStatesIterator();

    abstract protected boolean isFinalState(int depth);
}
