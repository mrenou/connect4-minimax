package com.xebia.xke.algo.minimax.connect4;

public class BoardFactory {
    
    public static Board createBoard(String strBoard) {
        Board board = new Board();
        return createBoard(strBoard, board);
    }

    private static Board createBoard(String strBoard, Board board) {
        String[] columns = strBoard.split("/");

        for (int i = 0; i < columns.length; i++) {
            for (int i2 = 0; i2 < columns[i].length(); i2++)
                if (columns[i].charAt(i2) == 'R') {
                    board.putCounter(i, CounterColor.RED);
                } else if (columns[i].charAt(i2) == 'Y') {
                    board.putCounter(i, CounterColor.YELLOW);
                }
        }
        return board;
    }
}
