package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

public class ConnectFourGame {

    private static final String DFLT_PLAYERS_DIR = "players/";

    private Map<String, Player> players;

    private JLabel infoLabel = new JLabel();

    private Match match;

    private Object wakeUpFlag = new Object();

    private PlayerPanel playerPanel1;
    private PlayerPanel playerPanel2;
    private BorderPanel borderPanel;

    public Component createComponents() {
        //TODO where, when ?
        initPlayers();


        borderPanel = new BorderPanel(this);
        playerPanel1 = new PlayerPanel(players.values().toArray(new Player[]{}), "Player 1");
        playerPanel2 = new PlayerPanel(players.values().toArray(new Player[]{}), "Player 2");


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(playerPanel1);
        panel.add(borderPanel);
        panel.add(playerPanel2);

        PlayerRunner playerRunner = new PlayerRunner(this);
        Thread thread = new Thread(playerRunner);
        thread.start();

        return panel;
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
        PlayerLoader playerLoader = new PlayerLoader(getPlayersDirectory());

        try {
            players = playerLoader.loadAllPlayers();
        } catch (PlayerLoadingException e) {
            //TODO show error dialog
            throw new RuntimeException("Cannot load players.", e);
        }
        players.put("Human", new HumanPlayer());
    }

    public void reset() {
        match = new Match(playerPanel1.getSelectedPlayer(), playerPanel2.getSelectedPlayer());
        playerPanel1.updateCounterLabel(ImageRessources.getInstance().getImageIconByCounterColor(match.getCounterColorPlayer1()));
        playerPanel2.updateCounterLabel(ImageRessources.getInstance().getImageIconByCounterColor(match.getCounterColorPlayer2()));
        infoLabel.setText(match.getCurrentCounterColor() + "   playing ...");
        borderPanel.resetBoard();
        if (!(match.getNextPlayer() instanceof HumanPlayer)) {
            wakeUpAutoPlayersThread();
        }
    }

    public void noHumanturn() {
        Player player = match.getNextPlayer();

        if (!(player instanceof HumanPlayer)) {
            Move move = match.playNextTurn();
            borderPanel.setMove(move);
        }
    }

    public void humanTurn() {
        Player player = match.getNextPlayer();

        if (player instanceof HumanPlayer) {
            Move move = match.playNextTurn(borderPanel.getSelectedColumn());

            if (!(match.getNextPlayer() instanceof HumanPlayer)) {
                wakeUpAutoPlayersThread();
            }
            borderPanel.setMove(move);
        }

    }

    public Object getWakeUpFlag() {
        return wakeUpFlag;
    }

    public Match getMatch() {
        return match;
    }
}