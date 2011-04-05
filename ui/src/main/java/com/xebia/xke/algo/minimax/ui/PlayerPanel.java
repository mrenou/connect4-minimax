package com.xebia.xke.algo.minimax.ui;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.xebia.xke.algo.minimax.connect4.Player;
import com.xebia.xke.algo.minimax.connect4.PlayerLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PlayerPanel extends JPanel {

    private JComboBox playerList;

    private JLabel playerCounterLabel;
    private JLabel gravatarLabel;

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

        this.add(playerLabel);
        this.add(playerList);
        this.add(playerCounterLabel);
        this.add(gravatarLabel);
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

    public void updateCounterLabel(Icon icon) {
        playerCounterLabel.setIcon(icon);
    }

    public void updateSelectedPlayerLoader(PlayerLoader playerLoader) {
        playerList.setSelectedItem(playerLoader);
    }
}
