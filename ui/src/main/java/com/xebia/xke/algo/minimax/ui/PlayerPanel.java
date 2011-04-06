package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.CounterColor;
import com.xebia.xke.algo.minimax.connect4.Move;
import com.xebia.xke.algo.minimax.connect4.PlayerLoader;
import sun.plugin.dom.css.Counter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PlayerPanel extends JPanel {

    private JComboBox playerList;
    private JLabel playerCounterLabel;
    private JLabel gravatarLabel;
    private JLabel playerStatusLabel;
    private CounterColor counterColor;

    public PlayerPanel(LayoutManager layoutManager, boolean b, PlayerLoader[] players, String playerLabel) {
        super(layoutManager, b);
        init(players, playerLabel);
    }

    public PlayerPanel(LayoutManager layoutManager, PlayerLoader[] players, String playerLabel) {
        super(layoutManager);
        init(players, playerLabel);
    }

    public PlayerPanel(boolean b, PlayerLoader[] players, String playerLabel) {
        super(b);
        init(players, playerLabel);
    }

    public PlayerPanel(PlayerLoader[] players, String playerLabel) {
        init(players, playerLabel);
    }

    private void init(PlayerLoader[] playerLoaders, String playerName) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        playerList = new JComboBox(playerLoaders);
        playerList.setRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
                return new JLabel(((PlayerLoader)o).getName());
            }
        });
        playerList.setPreferredSize(new Dimension(100, 50));
        playerList.setMinimumSize(new Dimension(100, 50));
        playerList.setMaximumSize(new Dimension(100, 50));
        playerList.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerList.setAlignmentY(Component.TOP_ALIGNMENT);
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
        playerCounterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerCounterLabel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel playerLabel = new JLabel(playerName);
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel.setAlignmentY(Component.TOP_ALIGNMENT);

        gravatarLabel = new JLabel();
        gravatarLabel.setIcon(ImageRessources.getInstance().getDefaultGravatar());
        gravatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gravatarLabel.setAlignmentY(Component.TOP_ALIGNMENT);

        playerStatusLabel = new JLabel();

        this.add(playerLabel);
        this.add(playerList);
        this.add(gravatarLabel);
        this.add(playerStatusLabel);
        this.add(playerCounterLabel);
        //this.add(Box.createVerticalGlue());

        loadGravatar();
    }

    public void loadGravatar() {
        PlayerLoader playerLoader = (PlayerLoader) playerList.getSelectedItem();
        String urlString = "http://www.gravatar.com/avatar/" + playerLoader.getGravatarHash() + "?s=200";

        try {
            URL url = new URL(urlString);
            ImageIcon imageIcon = new ImageIcon(url);
            gravatarLabel.setIcon(imageIcon);
        } catch (MalformedURLException e) {
            gravatarLabel.setIcon(ImageRessources.getInstance().getDefaultGravatar());
        } catch (IOException e) {
            gravatarLabel.setIcon(ImageRessources.getInstance().getDefaultGravatar());
        }
    }

    public PlayerLoader getSelectedPlayerLoader() {
        return (PlayerLoader) playerList.getSelectedItem();
    }

    public void updateCounterColor(CounterColor counterColor) {
        ImageIcon icon = ImageRessources.getInstance().getImageIconByCounterColor(counterColor);
        this.counterColor = counterColor;
        playerCounterLabel.setIcon(icon);
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
            statusIsTitmeout();
        } else if (move.isValidMove()) {
            if (move.getCounterColor().equals(counterColor)) {
                clearStatus();
            } else {
                statusIsPlaying();
            }
        }
    }
}
