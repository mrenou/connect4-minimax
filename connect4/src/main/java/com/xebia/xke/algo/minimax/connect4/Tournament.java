package com.xebia.xke.algo.minimax.connect4;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

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

    public void start2() throws PlayerLoadingException {

        /*File file = new File(".");
        Player player1 = loadPlayer(new File("./connect4/target/players/player-idiot-1.0-SNAPSHOT.jar"));
        System.out.println(player1.getName());
        Player player2 = loadPlayer(new File("./connect4/target/players/player-medium-1.0-SNAPSHOT.jar"));
        System.out.println(player2.getName());
        Match match = new Match(player1, player2);

        System.out.println(match.play());
          */
    }

    
}
