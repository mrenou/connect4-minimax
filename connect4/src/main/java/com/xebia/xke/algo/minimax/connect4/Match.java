package com.xebia.xke.algo.minimax.connect4;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Match {

    private Map<CounterColor, Player> players = new HashMap<CounterColor, Player>();

    private CounterColor counterColorPlayer1;

    private CounterColor counterColorPlayer2;

    private ConnectFour connectFour;

    public Match(Player player1, Player player2) {
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
    }

    public Player getNextPlayer() {
        return players.get(connectFour.getCurrentCounterColor());
    }

    public Move playNextTurn() {
        Player currentPlayer = players.get(connectFour.getCurrentCounterColor());
        int columnPlayed = currentPlayer.play(connectFour.getCurrentCounterColor(), (Board) connectFour.getBoard().clone());

        return playNextTurn(columnPlayed);
    }

    public Move playNextTurn(int columnIndex) {
        return connectFour.putCounter(columnIndex);
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
        return connectFour.isEndGame();
    }

    public CounterColor getCounterColorPlayer1() {
        return counterColorPlayer1;
    }

    public CounterColor getCounterColorPlayer2() {
        return counterColorPlayer2;
    }
}
