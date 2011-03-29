package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class ConnectFourGame {

    private static final String DFLT_PLAYERS_DIR = "players/";

    private Map<String, Player> players;

    //private ConnectFour connectFour;
    private DefaultTableModel defaultTableModel;
    private ImageIcon redIcon;
    private ImageIcon yellowIcon;
    private ImageIcon emptyIcon;
    private JLabel infoLabel = new JLabel();

    private Match match = new Match(new HumanPlayer(), new HumanPlayer());

    private JTable jtable;

    private Object wakeUpFlag = new Object();

    private boolean started = false;
    private JComboBox player2List;
    private JLabel player2CounterLabel;
    private JComboBox player1List;
    private JLabel player1CounterLabel;

    private void resetGame() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 6; y++) {
                defaultTableModel.setValueAt(emptyIcon, y, x);
            }
        }
    }

    public Component createComponents() {
        //TODO where, when ?
        initPlayers();

        Thread thread = new Thread() {

            private void sleepAutoPlayersThread() {
                synchronized (wakeUpFlag) {
                    try {
                        synchronized (wakeUpFlag) {
                            wakeUpFlag.wait();
                        }
                    }catch (InterruptedException e) {
                        //
                    }
                }
            }

            @Override
            public void run() {
                while (true) {
                    sleepAutoPlayersThread();

                    while (!match.isEndMatch()) {
                        Player player = match.getNextPlayer();

                        if (!(player instanceof HumanPlayer)) {
                            Move move = match.playNextTurn();
                            setMove(move);
                        } else {
                            sleepAutoPlayersThread();
                        }
                    }
                }
            }
        };
        thread.start();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(getLeftPlayerPanel());
        panel.add(getBoardPanel());
        panel.add(getRightPlayerPanel());

        return panel;
    }

    private Component getLeftPlayerPanel() {
        JPanel playerPanel = new JPanel();

        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));

        java.util.List<String> playerNames = new ArrayList<String>();
        for (Player player : players.values()) {
            playerNames.add(player.getName());
        }
        player1List = new JComboBox(playerNames.toArray());

        player1CounterLabel = new JLabel();

        playerPanel.add(new JLabel("Player 1"));
        playerPanel.add(player1List);
        playerPanel.add(player1CounterLabel);

        return playerPanel;
    }

    private Component getRightPlayerPanel() {
        JPanel playerPanel = new JPanel();

        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));

        java.util.List<String> playerNames = new ArrayList<String>();
        for (Player player : players.values()) {
            playerNames.add(player.getName());
        }
        player2List = new JComboBox(playerNames.toArray());

        player2CounterLabel = new JLabel();

        playerPanel.add(new JLabel("Player 2"));
        playerPanel.add(player2List);
        playerPanel.add(player2CounterLabel);

        return playerPanel;
    }

    private JPanel getBoardPanel() {
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        // TODO check
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        defaultTableModel = buildTableModel();
        jtable = buildJTable(defaultTableModel);
        panel.add(jtable);
        panel.add(infoLabel);
        return panel;
    }

    private JTable buildJTable(DefaultTableModel defaultTableModel) {
        jtable = new JTable();

        redIcon = new ImageIcon(this.getClass().getResource("/images/counter-red.png"));
        yellowIcon = new ImageIcon(this.getClass().getResource("/images/counter-yellow.png"));
        emptyIcon = new ImageIcon(this.getClass().getResource("/images/counter-empty.png"));

        //TODO check
        jtable.setShowGrid(true);
        jtable.setSize(700, 700);


        jtable.setModel(defaultTableModel);

        jtable.setRowHeight(100);
        jtable.getColumnModel().getColumn(0).setMinWidth(100);
        jtable.getColumnModel().getColumn(0).setMaxWidth(100);
        jtable.getColumnModel().getColumn(1).setMinWidth(100);
        jtable.getColumnModel().getColumn(1).setMaxWidth(100);
        jtable.getColumnModel().getColumn(2).setMinWidth(100);
        jtable.getColumnModel().getColumn(2).setMaxWidth(100);
        jtable.getColumnModel().getColumn(3).setMinWidth(100);
        jtable.getColumnModel().getColumn(3).setMaxWidth(100);
        jtable.getColumnModel().getColumn(4).setMinWidth(100);
        jtable.getColumnModel().getColumn(4).setMaxWidth(100);
        jtable.getColumnModel().getColumn(5).setMinWidth(100);
        jtable.getColumnModel().getColumn(5).setMaxWidth(100);
        jtable.getColumnModel().getColumn(6).setMinWidth(100);
        jtable.getColumnModel().getColumn(6).setMaxWidth(100);

        resetGame();

        //TODO check
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jtable.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (started == false) {
                    started = true;

                    match = new Match(players.get(player1List.getSelectedItem()), players.get(player2List.getSelectedItem()));
                    player1CounterLabel.setIcon(getImageIconByCounterColor(match.getCounterColorPlayer1()));
                    player2CounterLabel.setIcon(getImageIconByCounterColor(match.getCounterColorPlayer2()));
                    infoLabel.setText(match.getCurrentCounterColor() + "   playing ...");
                    resetGame();
                    if (!(match.getNextPlayer() instanceof HumanPlayer)) {
                        wakeUpAutoPlayersThread();
                    }
                } else if (match.getNextPlayer() instanceof HumanPlayer) {
                    Move move = match.playNextTurn(jtable.getSelectedColumn());
                    setMove(move);
                    wakeUpAutoPlayersThread();
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

        return jtable;
    }

    private ImageIcon getImageIconByCounterColor(CounterColor counterColor) {
        if (counterColor == null) {
            return emptyIcon;
        }
        if (counterColor == CounterColor.RED) {
            return redIcon;
        }
        if (counterColor == CounterColor.YELLOW) {
            return yellowIcon;
        }
        return null;
    }

    private void wakeUpAutoPlayersThread() {
        synchronized (wakeUpFlag) {
            wakeUpFlag.notify();
        }
    }

    private DefaultTableModel buildTableModel() {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int i) {
                return Icon.class;
            }
        };
        defaultTableModel.setColumnCount(7);
        defaultTableModel.setRowCount(6);
        return defaultTableModel;
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

    private void setMove(Move move) {

        if (!move.isValidMove()) {
            //TODO show error dialog ?
            System.out.println("Not valid");
        } else {
            defaultTableModel.setValueAt(getImageIconByCounterColor(move.getCounterColor()), 5 - move.getVerticalIndex(), move.getColumnIndex());
        }
        if (move.isWinningMove()) {
            infoLabel.setText(move.getCounterColor().name() + "  win !");
            started = false;
        } else {
            infoLabel.setText(move.getCounterColor().getOtherCounterColor() + "   playing ...");
        }
    }
}