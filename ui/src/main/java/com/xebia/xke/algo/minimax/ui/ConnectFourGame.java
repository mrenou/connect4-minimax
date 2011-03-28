package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.*;
import sun.awt.VerticalBagLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Map;

public class ConnectFourGame {

    private static final String PLAYERS_DIR = "players/";

    private Map<String, Player> players;

    private ConnectFour connectFour;
    private DefaultTableModel defaultTableModel;
    private ImageIcon redIcon;
    private ImageIcon yellowIcon;
    private ImageIcon emptyIcon;
    private JLabel winnerLabel = new JLabel();

    private Match match = new Match(new RealPlayer(), new RealPlayer());

    private void resetGame() {
        //TODO reset connect four ?
        connectFour = new ConnectFour();
        Board board = connectFour.getBoard();
        for (int x = 0; x < board.getNbColumns(); x++) {
            for (int y = 0; y < board.getColumnSize(); y++) {
                CounterColor counterColor = board.getCounterColor(x, board.getColumnSize() - 1 - y);

                if (counterColor == null) {
                    defaultTableModel.setValueAt(emptyIcon, y, x);
                }
            }
        }
    }

    public Component createComponents() {
        //TODO where, when ?
        initPlayers();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        // TODO check
        panel.setLayout(new FlowLayout());
        defaultTableModel = buildTableModel();
        JTable jtable = buildJTable(defaultTableModel);
        panel.add(jtable);
        panel.add(winnerLabel);
        resetGame();

        return panel;
    }

    private JTable buildJTable(DefaultTableModel defaultTableModel) {
        final JTable jtable = new JTable();

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
                putCounter(jtable.getSelectedColumn());
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        return jtable;
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

    private void turn() {
        Player player = match.getNextPlayer();

        if (player instanceof RealPlayer) {
            match.play();    
        }
    }

    private void putCounter(int columnIndex) {
        CounterColor counterColor = connectFour.getCurrentCounterColor();

        Move move = connectFour.putCounter(columnIndex);
        if (!move.isValidMove()) {
            //TODO show error dialog ?
            System.out.println("Not valid");
        } else {
            if (counterColor == null) {
                defaultTableModel.setValueAt(emptyIcon, connectFour.getBoard().getColumnSize() - 1 - move.getVerticalIndex(), columnIndex);
            }
            if (CounterColor.RED.equals(counterColor)) {
                defaultTableModel.setValueAt(redIcon, connectFour.getBoard().getColumnSize() - 1 - move.getVerticalIndex(), columnIndex);
            }
            if (CounterColor.YELLOW.equals(counterColor)) {
                defaultTableModel.setValueAt(yellowIcon, connectFour.getBoard().getColumnSize() - 1 - move.getVerticalIndex(), columnIndex);
            }
        }
        if (move.isWinningMove()) {
            winnerLabel.setText(move.getCounterColor().name() + "win!");
            resetGame();
        }
    }
}