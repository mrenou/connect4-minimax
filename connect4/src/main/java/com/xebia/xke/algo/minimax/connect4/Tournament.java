package com.xebia.xke.algo.minimax.connect4;

import com.google.common.annotations.VisibleForTesting;

import java.util.*;

public class Tournament {

    private List<PlayerScored> playerScoredList = new ArrayList<PlayerScored>();

    private Map<String, PlayerScored> playerScoredMap = new HashMap<String, PlayerScored>();

    @VisibleForTesting
    Queue<Match> matches = new LinkedList<Match>();

    @VisibleForTesting
    boolean running = false;

    private int timeout = 0;

    public Tournament() {

    }

    public Tournament(int timeout) {
        this.timeout = timeout;
    }

    public void addPlayerLoader(PlayerLoader playerLoader) {
        checkIfNotRunning();
        PlayerScored playerScored = new PlayerScored(playerLoader);
        playerScoredList.add(playerScored);
        playerScoredMap.put(playerScored.getPlayerLoader().getName(), playerScored);
    }

    public void start() {
        checkIfNotRunning();
        Queue<PlayerScored> tmpPlayers = new LinkedList<PlayerScored>(playerScoredList);

        while (!tmpPlayers.isEmpty()) {
            PlayerScored player1 = tmpPlayers.poll();
            for (PlayerScored player2 : tmpPlayers) {
                matches.add(new Match(player1.getPlayerLoader().loadPlayer(), player2.getPlayerLoader().loadPlayer(), timeout));
            }
        }
        
        running = true;
        resetScores();
    }

    public void addPlayerLoaders(Collection<PlayerLoader> playerLoaders) {
        for (PlayerLoader playerLoader : playerLoaders) {
            addPlayerLoader(playerLoader);
        }
    }

    @VisibleForTesting
    void checkIfNotRunning() {
        if (running) {
            throw new IllegalStateException("the tournament is running...");
        }
    }

    @VisibleForTesting
    void checkIfRunning() {
        if (!running) {
            throw new IllegalStateException("the tournament is not running...");
        }
    }

    public Match playNextMatch() {
        checkIfRunning();
        Match currentMatch = pollNextMatch();

        if (currentMatch != null) {
            Player winner = currentMatch.play();

            setMatchResult(winner, currentMatch);
        }
        return currentMatch;
    }

    public void playAllMatches() {
        checkIfRunning();
        while(getNextMatch() != null) {
            playNextMatch();
        }
    }

    private void resetScores() {
        for (PlayerScored playerScored : playerScoredList) {
            playerScored.reset();
        }
    }

    private void sortScores() {
        Collections.sort(playerScoredList, new Comparator<PlayerScored>() {

            @Override
            public int compare(PlayerScored playerScored1, PlayerScored playerScored2) {
                if (playerScored1.getScore().equals(playerScored2.getScore())) {
                    return playerScored1.getPlayerLoader().getName().compareTo(playerScored2.getPlayerLoader().getName());
                }
                return playerScored2.getScore() - playerScored1.getScore();
            }
        });
    }

    public Integer getScore(PlayerLoader playerLoader) {
        return playerScoredMap.get(playerLoader.getName()).getScore();
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
            builder.append(playerScored.getPlayerLoader().getName());
            builder.append(" ");
            builder.append(playerScored.getScore());
            builder.append("\n");
        }
        return builder.toString();
    }

    /*
     *
     * Methods used when the match playing is not controlled by the tournament
     *
     */

    public Match getNextMatch() {
        return matches.peek();
    }

    public Match pollNextMatch() {
        checkIfRunning();
        return matches.poll();
    }

    public void setMatchResult(Player winner, Match match) {
        if (winner == null) {
            playerScoredMap.get(match.getPlayer(CounterColor.RED).getName()).addPoints(1);
            playerScoredMap.get(match.getPlayer(CounterColor.YELLOW).getName()).addPoints(1);
        } else {
            playerScoredMap.get(winner.getName()).addPoints(3);
        }
        sortScores();
        if (getNextMatch() == null) {
            running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }
}
