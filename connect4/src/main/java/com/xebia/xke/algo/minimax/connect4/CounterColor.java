package com.xebia.xke.algo.minimax.connect4;

public enum CounterColor {
    RED, YELLOW;

    public CounterColor getOtherCounterColor() {
        if (CounterColor.RED.equals(this)) {
            return CounterColor.YELLOW;
        } else {
            return CounterColor.RED;
        }
    }
    }
