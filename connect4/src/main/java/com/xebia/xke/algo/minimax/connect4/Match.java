package com.xebia.xke.algo.minimax.connect4;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Match {

    private Map<CounterColor, Player> players = new HashMap<CounterColor, Player>();

    private ConnectFour connectFour;

    public Match(Player player1, Player player2) {
        connectFour = new ConnectFour();
        if (new Random().nextBoolean()) {
            players.put(connectFour.getCurrentCounterColor(), player1);
            players.put(connectFour.getCurrentCounterColor().getOtherCounterColor(), player2);
        } else {
            players.put(connectFour.getCurrentCounterColor(), player2);
            players.put(connectFour.getCurrentCounterColor().getOtherCounterColor(), player1);
        }
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
}
