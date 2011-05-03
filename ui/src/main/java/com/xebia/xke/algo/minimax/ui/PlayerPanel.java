package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.Move;
import com.xebia.xke.algo.minimax.connect4.PlayerLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPanel extends JPanel {

    private JComboBox playerList;
    private JLabel playerCounterLabel;
    private JLabel gravatarLabel;
    private JLabel playerStatusLabel;
    private CounterColor counterColor;
    private static final int WIDTH_PANEL = 225;
    private ConnectFourGame connectFourGame;

    public PlayerPanel(LayoutManager layoutManager, boolean b, ConnectFourGame connectFourGame, PlayerLoader[] players, String playerLabel) {
        super(layoutManager, b);
        init(players, playerLabel);
        this.connectFourGame = connectFourGame;
    }

    public PlayerPanel(LayoutManager layoutManager, ConnectFourGame connectFourGame, PlayerLoader[] players, String playerLabel) {
        super(layoutManager);
        init(players, playerLabel);
        this.connectFourGame = connectFourGame;
    }

    public PlayerPanel(boolean b, ConnectFourGame connectFourGame, PlayerLoader[] players, String playerLabel) {
        super(b);
        init(players, playerLabel);
        this.connectFourGame = connectFourGame;
    }

    public PlayerPanel(ConnectFourGame connectFourGame, PlayerLoader[] players, String playerLabel) {
        init(players, playerLabel);
        this.connectFourGame = connectFourGame;
    }

    private void init(PlayerLoader[] playerLoaders, String playerName) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        playerList = new JComboBox(playerLoaders);
        playerList.setFont(new Font("Verdana", Font.BOLD, 26));
        playerList.setRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
                JLabel label = new JLabel(((PlayerLoader)o).getName());
                label.setFont(new Font("Verdana", Font.BOLD, 26));
                return label;
            }
        });
        setAllComponentSizes(playerList, WIDTH_PANEL, 30);
        setDefaultAlignment(playerList);
        playerList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ("comboBoxChanged".equals(actionEvent.getActionCommand())) {
                    loadGravatar();
                }
            }
        });


        playerCounterLabel = new JLabel();
        playerCounterLabel.setIcon(ImageRessources.getInstance().getEmptyIcon());
        setDefaultAlignment(playerCounterLabel);

        JLabel playerLabel = new JLabel(playerName);
        setDefaultAlignment(playerLabel);

        gravatarLabel = new JLabel();
        gravatarLabel.setIcon(ImageRessources.getInstance().getDefaultGravatar());
        setDefaultAlignment(gravatarLabel);

        playerStatusLabel = new JLabel();
        playerStatusLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        setDefaultAlignment(playerStatusLabel);
        setAllComponentSizes(playerStatusLabel, WIDTH_PANEL, 30);

        this.add(playerLabel);
        this.add(playerList);
        this.add(gravatarLabel);
        this.add(playerCounterLabel);
        this.add(playerStatusLabel);
        //this.add(Box.createVerticalGlue());

        loadGravatar();
    }

    private void setAllComponentSizes(JComponent component, int width, int height) {
        component.setPreferredSize(new Dimension(width, height));
        component.setMinimumSize(new Dimension(width, height));
        component.setMaximumSize(new Dimension(width, height));
    }

    private void setDefaultAlignment(JComponent component) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        component.setAlignmentY(Component.TOP_ALIGNMENT);
    }

    public void loadGravatar() {
        PlayerLoader playerLoader = (PlayerLoader) playerList.getSelectedItem();
        gravatarLabel.setIcon(ImageRessources.getInstance().getGravatar(playerLoader.getGravatarHash(), WIDTH_PANEL));
    }

    public PlayerLoader getSelectedPlayerLoader() {
        return (PlayerLoader) playerList.getSelectedItem();
    }

    public void reset(CounterColor counterColor) {
        ImageIcon icon = ImageRessources.getInstance().getImageIconByCounterColor(counterColor);
        this.counterColor = counterColor;
        playerCounterLabel.setIcon(icon);
        clearStatus();
    }

    public void updateSelectedPlayerLoader(PlayerLoader playerLoader) {
        playerList.setSelectedItem(playerLoader);
    }

    private void statusIsTitmeout() {
        playerStatusLabel.setText("Timeout !");
    }

    private void statusIsPlaying() {
        playerStatusLabel.setText("Playing...");
    }

    private void statusIsWinner() {
        playerStatusLabel.setText("Winner !");
    }

    private void statusIsLooser() {
        playerStatusLabel.setText("Looser !");
    }

    private void statusIsDraw() {
        playerStatusLabel.setText("Draw !");
    }

    private void statusIsInvalid() {
        playerStatusLabel.setText("Invalid !");
    }

    private void clearStatus() {
        playerStatusLabel.setText("");
    }

    public void setMove(Move move) {
        if (move.isWinningMove()) {
            if (counterColor.equals(move.getCounterColor())) {
                statusIsWinner();
            } else {
                statusIsLooser();
            }
        } else if (move.isTimeoutMove()) {
            if (move.getCounterColor().equals(counterColor)) {
                statusIsTitmeout();
            } else {
                statusIsWinner();
            }
        } else if (!move.isValidMove()) {
            if (move.getCounterColor().equals(counterColor)) {
                statusIsInvalid();
            } else {
                statusIsWinner();
            }
        } else if (connectFourGame.getMatch().isEndMatch()) {
            statusIsDraw();
        } else if (move.isValidMove()) {
            if (move.getCounterColor().equals(counterColor)) {
                clearStatus();
            } else {
                statusIsPlaying();
            }
        }
    }
}