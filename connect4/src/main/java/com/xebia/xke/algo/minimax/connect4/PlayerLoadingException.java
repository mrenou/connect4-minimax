package com.xebia.xke.algo.minimax.connect4;

public class PlayerLoadingException extends Exception {
    public PlayerLoadingException() {
    }

    public PlayerLoadingException(String s) {
        super(s);
    }

    public PlayerLoadingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PlayerLoadingException(Throwable throwable) {
        super(throwable);
    }
}
