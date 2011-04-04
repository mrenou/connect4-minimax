package com.xebia.xke.algo.minimax.connect4;

abstract public class SimplePlayer implements Player {

    private String name;

    @Override
    public void setName(String name) {
       this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
