package com.xebia.xke.algo.minimax.torename;

import com.xebia.xke.algo.minimax.connect4.Board;
import com.xebia.xke.algo.minimax.connect4.CounterColor;

import java.util.Iterator;

public abstract class StateConnectFour extends State {

    protected Board board;

    protected CounterColor counterColorTested;

    private CounterColor counterColorToPlay;

    private int columnIndexPlayed;

    private int maxDepth;

    public StateConnectFour(Board board, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth) {
        this.board = board;
        this.counterColorTested = counterColorTested;
        this.counterColorToPlay = counterColorToPlay;
        this.columnIndexPlayed = columnIndexPlayed;
        this.maxDepth = maxDepth;
    }

    @Override
    public Iterable<? extends State> nextStatesIterator() {
        return new  Iterable<StateConnectFour>() {
            @Override
            public Iterator<StateConnectFour> iterator() {
                Iterator<StateConnectFour> iterator = new Iterator<StateConnectFour>() {

                    int columnIndex = 0;

                    @Override
                    public boolean hasNext() {
                        if (board.isFull()) {
                            return false;
                        }

                        while(columnIndex < board.getNbColumns() && board.columnIsfull(columnIndex)) {
                            columnIndex++;
                        }
                        return columnIndex < board.getNbColumns();
                    }

                    @Override
                    public StateConnectFour next() {
                        Board boardCloned = (Board) board.clone();
                        int columnIndexPlayed;

                        while(columnIndex < board.getNbColumns() && board.columnIsfull(columnIndex)) {
                            columnIndex++;
                        }

                        if (columnIndex >= boardCloned.getNbColumns() || boardCloned.putCounter(columnIndex, counterColorToPlay) == -1) {
                            throw new IllegalStateException();
                        }
                        columnIndexPlayed = columnIndex;
                        columnIndex++;
                        return createNextState(boardCloned, counterColorTested, counterColorToPlay.getOtherCounterColor(), columnIndexPlayed, maxDepth);
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };

                return iterator;
            }
        };
    }

    protected abstract StateConnectFour createNextState(Board boardCloned, CounterColor counterColorTested, CounterColor counterColorToPlay, int columnIndexPlayed, int maxDepth);

    @Override
    protected boolean isFinalState(int depth) {
        return depth == maxDepth || board.getWinnerCounterColor() != null || board.isFull();
    }

    public int getColumnIndexPlayed() {
        return columnIndexPlayed;
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
