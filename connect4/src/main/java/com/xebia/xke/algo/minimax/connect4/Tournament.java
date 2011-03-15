package com.xebia.xke.algo.minimax.connect4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tournament {

    List<Player> players = new ArrayList<Player>();

    Queue<Match> matches = new LinkedList<Match>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        Queue<Player> tmpPlayers = new LinkedList<Player>(players);

        while (!tmpPlayers.isEmpty()) {
         Player player1 = tmpPlayers.poll();
            for (Player player2 : tmpPlayers) {
                matches.add(new Match(player1, player2));
            }
        }

    }
}
