package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.PlayerLoader;

import javax.swing.*;
import java.awt.*;

public class MatchPresentationPanel extends JPanel {

    private Box mainBox;

    public MatchPresentationPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        init();
    }

    public MatchPresentationPanel(LayoutManager layout) {
        super(layout);
        init();
    }

    public MatchPresentationPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        init();
    }

    public MatchPresentationPanel() {
        init();
    }

    public void setPlayersForNextMatch(PlayerLoader playerLoader1, PlayerLoader playerLoader2) {
        setPlayerBox(playerLoader1, (Box) mainBox.getComponent(0));
        setPlayerBox(playerLoader2, (Box) mainBox.getComponent(2));
    }



    private void init() {
        mainBox = Box.createHorizontalBox();

        mainBox.add(getEmptyPlayerBox());
        JLabel label = new JLabel(" VS ");
        label.setFont(new Font("Verdana", Font.BOLD, 50));
        mainBox.add(label);
        mainBox.add(getEmptyPlayerBox());
        this.add(mainBox);
    }

    private Box getEmptyPlayerBox() {
        Box box = Box.createVerticalBox();

        JLabel gravatarLabel = new JLabel();
        gravatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel playerLabel = new JLabel();
        playerLabel.setFont(new Font("Verdana", Font.BOLD, 50));
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(gravatarLabel);
        box.add(playerLabel);

        return box;
    }


    private void setPlayerBox(PlayerLoader playerLoader, Box playerBox) {
        JLabel gravatarLabel = (JLabel) playerBox.getComponent(0);
        gravatarLabel.setIcon(ImageRessources.getInstance().getGravatar(playerLoader.getGravatarHash(), 200));

        JLabel playerLabel = (JLabel) playerBox.getComponent(1);
        playerLabel.setText(playerLoader.getName());
    }

}
