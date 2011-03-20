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
            players.put(connectFour.getNotCurrentCounterColor(), player2);
        } else {
            players.put(connectFour.getCurrentCounterColor(), player2);
            players.put(connectFour.getNotCurrentCounterColor(), player1);
        }
    }

    public Player getNextPlayer() {
        return players.get(connectFour.getCurrentCounterColor());
    }

    public Player play() {
        while (!connectFour.isEndGame()) {
            Player currentPlayer = players.get(connectFour.getCurrentCounterColor());
            int columnPlayed = currentPlayer.play(connectFour.getCurrentCounterColor(), (Board) connectFour.getBoard().clone());

            connectFour.putCounter(columnPlayed);
            if (connectFour.getCounterColorOfWinner() != null) {
                System.out.println(players.get(CounterColor.YELLOW).toString() + " " + players.get(CounterColor.YELLOW).getStats());
                System.out.println(players.get(CounterColor.RED).toString() + " " + players.get(CounterColor.RED).getStats());
                return players.get(connectFour.getCounterColorOfWinner());
            }
        }
        System.out.println(players.get(CounterColor.YELLOW).toString() + " " + players.get(CounterColor.YELLOW).getStats());
        System.out.println(players.get(CounterColor.RED).toString() + " " + players.get(CounterColor.RED).getStats());
        return null;
    }
}
