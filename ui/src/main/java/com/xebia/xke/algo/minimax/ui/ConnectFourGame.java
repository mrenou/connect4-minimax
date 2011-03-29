package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Map;

public class ConnectFourGame {

    private static final String PLAYERS_DIR = "ui/players/";

    private Map<String, Player> players;

    //private ConnectFour connectFour;
    private DefaultTableModel defaultTableModel;
    private ImageIcon redIcon;
    private ImageIcon yellowIcon;
    private ImageIcon emptyIcon;
    private JLabel infoLabel = new JLabel();

    private Match match = new Match(new RealPlayer(), new RealPlayer());

    private JTable jtable;

    private Object wakeUpFlag = new Object();

    private boolean started = false;

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
        //match = new Match(players.get("Medium-2"), players.get("Medium-2"));
        match = new Match(new RealPlayer(), players.get("Medium-2"));

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        // TODO check
        panel.setLayout(new FlowLayout());
        defaultTableModel = buildTableModel();
        jtable = buildJTable(defaultTableModel);
        panel.add(jtable);
        panel.add(infoLabel);
        resetGame();

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

                        if (!(player instanceof RealPlayer)) {
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

        //TODO check
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jtable.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (started == false) {
                    started = true;
                    if (match.isEndMatch()) {
                        //match = new Match(players.get("Medium-2"), players.get("Medium-2"));
                        match = new Match(new RealPlayer(), players.get("Medium-2"));
                    }
                    infoLabel.setText(match.getCurrentCounterColor() + "   playing ...");
                    resetGame();
                    if (!(match.getNextPlayer() instanceof RealPlayer)) {
                        wakeUpAutoPlayersThread();
                    }
                } else if (match.getNextPlayer() instanceof RealPlayer) {
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

    private void initPlayers() {
        PlayerLoader playerLoader = new PlayerLoader(new File(PLAYERS_DIR));

        try {
            players = playerLoader.loadAllPlayers();
        } catch (PlayerLoadingException e) {
            //TODO show error dialog
            throw new RuntimeException("BOOM");
        }
    }

    private void setMove(Move move) {

        if (!move.isValidMove()) {
            //TODO show error dialog ?
            System.out.println("Not valid");
        } else {
            if (move.getCounterColor() == null) {
                defaultTableModel.setValueAt(emptyIcon, 5 - move.getVerticalIndex(), move.getColumnIndex());
            }
            if (CounterColor.RED.equals(move.getCounterColor())) {
                defaultTableModel.setValueAt(redIcon, 5 - move.getVerticalIndex(), move.getColumnIndex());
            }
            if (CounterColor.YELLOW.equals(move.getCounterColor())) {
                defaultTableModel.setValueAt(yellowIcon, 5 - move.getVerticalIndex(), move.getColumnIndex());
            }
        }
        if (move.isWinningMove()) {
            infoLabel.setText(move.getCounterColor().name() + "  win !");
            started = false;
        } else {
            infoLabel.setText(move.getCounterColor().getOtherCounterColor() + "   playing ...");
        }
    }
}