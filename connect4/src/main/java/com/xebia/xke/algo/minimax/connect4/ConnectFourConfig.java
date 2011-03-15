package com.xebia.xke.algo.minimax.connect4;

public class ConnectFourConfig {
    private static final int DFLT_WINNING_LINE_SIZE = 4;
    private static final int DFLT_NB_COLUMNS = 7;
    private static final int DFLT_COLUMN_SIZE = 6;

    private int winningLineSize = DFLT_WINNING_LINE_SIZE;
    private int nbColumns = DFLT_NB_COLUMNS;
    private int columnSize = DFLT_COLUMN_SIZE;

    protected ConnectFourConfig() {
    }

    public static ConnectFourConfig create() {
        return new ConnectFourConfig();
    }

    public ConnectFourConfig withWinningLineSize(int winningLineSize) {
        this.winningLineSize = winningLineSize;
        return this;
    }

    public ConnectFourConfig withNbColumns(int nbColumns) {
        this.nbColumns = nbColumns;
        return this;
    }

    public ConnectFourConfig withColumnSize(int columnSize) {
        this.columnSize = columnSize;
        return this;
    }

    public int getWinningLineSize() {
        return winningLineSize;
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public int getColumnSize() {
        return columnSize;
    }
}
