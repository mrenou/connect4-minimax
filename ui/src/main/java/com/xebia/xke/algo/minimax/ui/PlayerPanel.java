package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {

    private JComboBox playerList;

    private JLabel playerCounterLabel;

    public PlayerPanel(LayoutManager layoutManager, boolean b, Player[] players, String playerLabel) {
        super(layoutManager, b);
        init(players, playerLabel);
    }

    public PlayerPanel(LayoutManager layoutManager, Player[] players, String playerLabel) {
        super(layoutManager);
        init(players, playerLabel);
    }

    public PlayerPanel(boolean b, Player[] players, String playerLabel) {
        super(b);
        init(players, playerLabel);
    }

    public PlayerPanel(Player[] players, String playerLabel) {
        init(players, playerLabel);
    }

    private void init(Player[] players, String playerLabel) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        playerList = new JComboBox(players);
        playerCounterLabel = new JLabel();

        this.add(new JLabel(playerLabel));
        this.add(playerList);
        this.add(playerCounterLabel);
    }

    public Player getSelectedPlayer() {
        return (Player) playerList.getSelectedItem();
    }

    public void updateCounterLabel(Icon icon) {
        playerCounterLabel.setIcon(icon);
    }
}
