package com.xebia.xke.algo.minimax.connect4;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Match {

    private Map<CounterColor, Player> players = new HashMap<CounterColor, Player>();

    private CounterColor counterColorPlayer1;

    private CounterColor counterColorPlayer2;

    private ConnectFour connectFour;

    private Player winner;

    private int timeout = 0;

    public Match(Player player1, Player player2) {
        this(player1, player2, 0);
    }

    public Match(Player player1, Player player2, int timeout) {
        connectFour = new ConnectFour();
        if (new Random().nextBoolean()) {
            counterColorPlayer1 = connectFour.getCurrentCounterColor();
            counterColorPlayer2 = connectFour.getCurrentCounterColor().getOtherCounterColor();
        } else {
            counterColorPlayer2 = connectFour.getCurrentCounterColor();
            counterColorPlayer1 = connectFour.getCurrentCounterColor().getOtherCounterColor();
        }
        players.put(counterColorPlayer1, player1);
        players.put(counterColorPlayer2, player2);
        this.timeout = timeout;
    }

    public Player getNextPlayer() {
        return players.get(connectFour.getCurrentCounterColor());
    }

    public Move playNextTurn() {
        final Player currentPlayer = getNextPlayer();

        if (timeout > 0) {
            final int[] columnPlayed = new int[1];
            final boolean[] played = new boolean[1];

            final Thread currentThread = Thread.currentThread();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    columnPlayed[0] = currentPlayer.play(getCurrentCounterColor(), (Board) connectFour.getBoard().clone());
                    synchronized (played) {
                        played[0] = true;
                    }
                    currentThread.interrupt();
                }
            }, "timeout-thread");
            t.start();
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {

            }

            // check if the turn is played
            synchronized (played) {
                if (!played[0]) {
                    timeout();
                    t.stop();
                    return Move.createTimeoutMove(getCurrentCounterColor());
                }
            }

            //return the column played
            return playNextTurn(columnPlayed[0]);
        } else {
            return playNextTurn(currentPlayer.play(getCurrentCounterColor(), (Board) connectFour.getBoard().clone()));
        }
    }

    public Move playNextTurn(int columnIndex) {
        if (this.isEndMatch()) {
            throw new IllegalStateException("The match is ended.");
        }
        Move move = connectFour.putCounter(columnIndex);
        // TODO invalid move ?
        if (move.isWinningMove()) {
            winner =  players.get(move.getCounterColor());
        }
        return move;
    }

    public Player play() {
        while (!connectFour.isEndGame()) {
            Move move = playNextTurn();

            if (move.isWinningMove()) {
                System.out.println(players.get(CounterColor.YELLOW).toString() + " " + players.get(CounterColor.YELLOW).getStats());
                System.out.println(players.get(CounterColor.RED).toString() + " " + players.get(CounterColor.RED).getStats());
                return players.get(move.getCounterColor());
            }
        }
        System.out.println(players.get(CounterColor.YELLOW).toString() + " " + players.get(CounterColor.YELLOW).getStats());
        System.out.println(players.get(CounterColor.RED).toString() + " " + players.get(CounterColor.RED).getStats());
        return null;
    }

    public Map<CounterColor, Player> getPlayers() {
        return players;
    }

    public Player getPlayer(CounterColor counterColor) {
        return players.get(counterColor);
    }

    public CounterColor getCurrentCounterColor() {
        return connectFour.getCurrentCounterColor();
    }

    public boolean isEndMatch() {
        return winner != null || connectFour.isEndGame();
    }

    public CounterColor getCounterColorPlayer1() {
        return counterColorPlayer1;
    }

    public CounterColor getCounterColorPlayer2() {
        return counterColorPlayer2;
    }

    public Player getPlayer1() {
        return players.get(counterColorPlayer1);
    }

    public Player getPlayer2() {
        return players.get(counterColorPlayer2);
    }

    private void timeout() {
        winner = players.get(connectFour.getCurrentCounterColor().getOtherCounterColor());
    }
}
