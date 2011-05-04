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
                        //FIXME - return false if the board is full
                        //      - increment column index on column not full
                        //      - return false if column index > number of columns
                        return false;
                    }

                    @Override
                    public StateConnectFour next() {

                        Board boardCloned = null; //FIXME - create a new state to create a child (clone)
                        int columnIndexPlayed;

                        while(columnIndex < board.getNbColumns() && board.columnIsfull(columnIndex)) {
                            columnIndex++;
                        }

                        //FIXME - check if column index <= number of columns
                        //      - put a counter on child with selected index and couter color to play.
                        //      - thorw IllegalStateException if the move id invalid (return -1)

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
        //FIXME - final state is when max depth is reached
        //      - final state is when winner exists
        //      - final state is when equality exists (board is full)

        return false;
    }

    public int getColumnIndexPlayed() {
        return columnIndexPlayed;
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
