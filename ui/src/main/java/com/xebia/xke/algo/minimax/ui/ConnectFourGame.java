package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

public class ConnectFourGame {

    private static final String DFLT_PLAYERS_DIR = "players/";
    public static final String GAME_CARD_NAME = "gamePanel";
    public static final String SCORES_CARD_NAME = "scoresPanel";
    private static final String MATCH_PRES_CARD_NAME = "matchPresentationPanel";

    private Map<String, PlayerLoader> playerLoaders;

    private Match match;

    private Object wakeUpFlag = new Object();

    private PlayerPanel playerPanel1;
    private PlayerPanel playerPanel2;
    private BorderPanel borderPanel;
    private RankingPanel rankingPanel;
    private MatchPresentationPanel matchPresentationPanel;

    private Tournament tournament;
    private boolean tournamentMode = true;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public Component createComponents() {
        cardPanel = new JPanel();
        cardLayout = new CardLayout();

        cardPanel.setLayout(cardLayout);

        rankingPanel = new RankingPanel(this);
        matchPresentationPanel = new MatchPresentationPanel();

        cardPanel.add(getGamePanel(), GAME_CARD_NAME);
        cardPanel.add(rankingPanel, SCORES_CARD_NAME);
        cardPanel.add(matchPresentationPanel, MATCH_PRES_CARD_NAME);

        return cardPanel;
    }

    private void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    private Component getGamePanel() {
        //TODO where, when ?
        initPlayers();
        tournament = new Tournament();
        tournament.addPlayerLoaders(playerLoaders.values());

        borderPanel = new BorderPanel(this);
        playerPanel1 = new PlayerPanel(playerLoaders.values().toArray(new PlayerLoader[]{}), "Player 1");
        playerPanel2 = new PlayerPanel(playerLoaders.values().toArray(new PlayerLoader[]{}), "Player 2");

        JPanel gamePanel = new JPanel();

        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.LINE_AXIS));
        gamePanel.add(playerPanel1);
        gamePanel.add(borderPanel);
        gamePanel.add(playerPanel2);

        PlayerRunner playerRunner = new PlayerRunner(this);
        Thread thread = new Thread(playerRunner, "player-thread");
        thread.start();

        return gamePanel;
    }


    private void wakeUpAutoPlayersThread() {
        synchronized (wakeUpFlag) {
            wakeUpFlag.notify();
        }
    }


    private File getPlayersDirectory() {
        return new File(System.getProperty("playersDir", DFLT_PLAYERS_DIR));
    }

    private void initPlayers() {
        PlayerLoadersLoader playerLoadersLoader = new PlayerLoadersLoader(getPlayersDirectory());

        try {
            playerLoaders = playerLoadersLoader.loadAllPlayers();
        } catch (PlayerLoadingException e) {
            //TODO show error dialog
            throw new RuntimeException("Cannot load players.", e);
        }
        if (!tournamentMode) {
            PlayerLoader humanPlayerLoader = new PlayerLoader("Human", HumanPlayer.class, "");
            playerLoaders.put("Human", humanPlayerLoader);
        }
    }

    public void prepareMatch() {
        if (tournamentMode) {
            if (!tournament.isRunning()) {
                tournament.start();
            }
            match = tournament.pollNextMatch();
            playerPanel1.updateSelectedPlayerLoader(playerLoaders.get(match.getPlayer1().getName()));
            playerPanel2.updateSelectedPlayerLoader(playerLoaders.get(match.getPlayer2().getName()));
        } else {
            match = new Match(playerPanel1.getSelectedPlayerLoader().loadPlayer(), playerPanel2.getSelectedPlayerLoader().loadPlayer(), 5000);
        }
        playerPanel1.updateCounterColor(match.getCounterColorPlayer1());
        playerPanel2.updateCounterColor(match.getCounterColorPlayer2());
        borderPanel.resetBoard();
        matchPresentationPanel.setPlayersForNextMatch(playerLoaders.get(match.getPlayer1().getName()), playerLoaders.get(match.getPlayer2().getName()));

        showCard(MATCH_PRES_CARD_NAME);
        //TODO constant ?
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMatch();
                backToGame();
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    public void startMatch() {
        if (!(match.getNextPlayer() instanceof HumanPlayer)) {
            wakeUpAutoPlayersThread();
        }
    }

    public void noHumanturn() {
        Player player = match.getNextPlayer();

        if (!(player instanceof HumanPlayer)) {
            Move move = match.playNextTurn();
            setMove(move);

            if (move.isWinningMove() && tournamentMode) {
                tournament.setMatchResult(player, match);
                if (!tournament.isRunning()) {
                    rankingPanel.setClassement(tournament.getScores());
                    showCard(SCORES_CARD_NAME);
                }
            }
        }
    }

    public void humanTurn() {
        Player player = match.getNextPlayer();

        if (player instanceof HumanPlayer) {
            Move move = match.playNextTurn(borderPanel.getSelectedColumn());

            if (!(match.getNextPlayer() instanceof HumanPlayer)) {
                wakeUpAutoPlayersThread();
            }
            setMove(move);
        }

    }

    private void setMove(Move move) {
        borderPanel.setMove(move);
        playerPanel1.setMove(move);
        playerPanel2.setMove(move);
    }

    public Object getWakeUpFlag() {
        return wakeUpFlag;
    }

    public Match getMatch() {
        return match;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public boolean isTournamentMode() {
        return tournamentMode;
    }

    public void backToGame() {
        showCard(ConnectFourGame.GAME_CARD_NAME);
    }
}