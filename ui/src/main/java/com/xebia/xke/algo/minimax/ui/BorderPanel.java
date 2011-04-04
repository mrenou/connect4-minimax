package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.Move;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BorderPanel extends JPanel {

    private boolean started = false;

    private JTable jtable;

    private DefaultTableModel defaultTableModel;

    private JLabel infoLabel = new JLabel();

    private ConnectFourGame connectFourGame;

    public BorderPanel(LayoutManager layoutManager, boolean b, ConnectFourGame connectFourGame) {
        super(layoutManager, b);
        this.connectFourGame = connectFourGame;
        init();
    }

    public BorderPanel(LayoutManager layoutManager, ConnectFourGame connectFourGame) {
        super(layoutManager);
        this.connectFourGame = connectFourGame;
        init();
    }

    public BorderPanel(boolean b, ConnectFourGame connectFourGame) {
        super(b);
        this.connectFourGame = connectFourGame;
        init();
    }

    public BorderPanel(ConnectFourGame connectFourGame) {
        this.connectFourGame = connectFourGame;
        init();
    }

    private void init() {
        //TODO check
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        defaultTableModel = buildTableModel();
        jtable = buildJTable(defaultTableModel);
        this.add(jtable);
        this.add(infoLabel);
    }

    public void resetBoard() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 6; y++) {
                defaultTableModel.setValueAt(ImageRessources.getInstance().getEmptyIcon(), y, x);
            }
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

    private JTable buildJTable(DefaultTableModel defaultTableModel) {
        jtable = new JTable();

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

        resetBoard();

        //TODO check
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jtable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!started) {
                    started = true;
                    connectFourGame.start();
                } else {
                    connectFourGame.humanTurn();
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

    public void setMove(Move move) {
        if (!move.isTimeoutMove()) {
            if (!move.isValidMove()) {
                //TODO show error dialog ?
                System.out.println("Not valid");
            } else {
                defaultTableModel.setValueAt(ImageRessources.getInstance().getImageIconByCounterColor(move.getCounterColor()), 5 - move.getVerticalIndex(), move.getColumnIndex());
            }
        }
        if (move.isWinningMove()) {
            infoLabel.setText(move.getCounterColor().name() + "  win ! Click to restart");
            started = false;
        } else if (move.isTimeoutMove()) {
            infoLabel.setText(move.getCounterColor().getOtherCounterColor().name() + "  win ! Click to restart");
            started = false;
        } else {
            infoLabel.setText(move.getCounterColor().getOtherCounterColor() + "   playing ...");
        }
    }

    public int getSelectedColumn() {
        return jtable.getSelectedColumn();
    }

    public void displayInfo(String message) {
        infoLabel.setText(message);
    }
}
