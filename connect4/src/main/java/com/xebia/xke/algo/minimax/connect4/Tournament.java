package com.xebia.xke.algo.minimax.connect4;

import com.google.common.annotations.VisibleForTesting;

import java.util.*;

public class Tournament {

    private List<PlayerScored> playerScoredList = new ArrayList<PlayerScored>();

    private Map<String, PlayerScored> playerScoredMap = new HashMap<String, PlayerScored>();

    @VisibleForTesting
    Queue<Match> matches = new LinkedList<Match>();

    public void addPlayer(Player player) {
        PlayerScored playerScored = new PlayerScored(player);
        playerScoredList.add(playerScored);
        playerScoredMap.put(playerScored.getPlayer().getName(), playerScored);
    }

    public void initMatches() {
        Queue<PlayerScored> tmpPlayers = new LinkedList<PlayerScored>(playerScoredList);

        while (!tmpPlayers.isEmpty()) {
            PlayerScored player1 = tmpPlayers.poll();
            for (PlayerScored player2 : tmpPlayers) {
                matches.add(new Match(player1.getPlayer(), player2.getPlayer()));
            }
        }
    }

    public Match playNextMatch() {
        Match currentMatch = matches.poll();

        if (currentMatch != null) {
            Player winner = currentMatch.play();

            if (winner == null) {
                playerScoredMap.get(currentMatch.getPlayer(CounterColor.RED).getName()).addPoints(1);
                playerScoredMap.get(currentMatch.getPlayer(CounterColor.YELLOW).getName()).addPoints(1);
            } else {
                playerScoredMap.get(winner.getName()).addPoints(3);
            }
        }
        sortScores();
        return currentMatch;
    }

    private void sortScores() {
        Collections.sort(playerScoredList, new Comparator<PlayerScored>() {

            @Override
            public int compare(PlayerScored playerScored, PlayerScored playerScored1) {
                return playerScored1.getScore() - playerScored.getScore();
            }
        });
    }

    public void playAllMatches() {
        while(playNextMatch() != null) {
        }
    }

    public Integer getScore(Player player) {
       return playerScoredMap.get(player.getName()).getScore();
    }

    public List<PlayerScored> getScores() {
       return Collections.unmodifiableList(playerScoredList);
    }

    public String getStringtScores() {
        StringBuilder builder = new StringBuilder();
        int i = 1;

        for (PlayerScored playerScored : playerScoredList) {
            builder.append(i++);
            builder.append(" ");
            builder.append(playerScored.getPlayer().getName());
            builder.append(" ");
            builder.append(playerScored.getScore());
            builder.append("\n");
        }
        return builder.toString();
    }
}
