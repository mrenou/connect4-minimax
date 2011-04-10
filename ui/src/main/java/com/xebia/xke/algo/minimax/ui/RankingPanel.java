package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.PlayerScored;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RankingPanel extends JPanel {


    private ConnectFourGame connectFourGame;

    public RankingPanel(LayoutManager layout, boolean isDoubleBuffered, ConnectFourGame connectFourGame) {
        super(layout, isDoubleBuffered);
        this.connectFourGame = connectFourGame;
        init();
    }

    public RankingPanel(LayoutManager layout, ConnectFourGame connectFourGame) {
        super(layout);
        this.connectFourGame = connectFourGame;
        init();
    }

    public RankingPanel(boolean isDoubleBuffered, ConnectFourGame connectFourGame) {
        super(isDoubleBuffered);
        this.connectFourGame = connectFourGame;
        init();
    }

    public RankingPanel(ConnectFourGame connectFourGame) {
        this.connectFourGame = connectFourGame;
        init();
    }

    public void setClassement(List<PlayerScored> playerScoredList) {

        // TEMP
        playerScoredList = new ArrayList<PlayerScored>();
        for (int i = 0; i < 10; i++) {
            for (PlayerScored playerScored : connectFourGame.getTournament().getScores()) {
                playerScoredList.add(playerScored);
            }
        }
        Iterator<PlayerScored> playerScoredIt = playerScoredList.iterator();

        Integer positon = 1;
        //Iterator<PlayerScored> playerScoredIt = connectFourGame.getTournament().getScores().iterator();

        //first scores
        Box firstScoresBox = Box.createHorizontalBox();
        for (int i = 0; playerScoredIt.hasNext() && i < 3; i++) {
            Box cellBox = Box.createHorizontalBox();
            PlayerScored playerScored = playerScoredIt.next();

            JLabel posLabel = new JLabel(positon.toString() + "  ");
            posLabel.setFont(new Font("Verdana", Font.BOLD, 26));

            JLabel label = new JLabel(playerScored.getPlayerLoader().getName() + " (" + playerScored.getScore() + ")");
            label.setIcon(ImageRessources.getInstance().getRedIcon());
            label.setIcon(ImageRessources.getInstance().getGravatar(playerScored.getPlayerLoader().getGravatarHash() , 80));
            label.setFont(new Font("Verdana", Font.BOLD, 24));

            positon++;
            cellBox.add(posLabel);
            cellBox.add(label);
            cellBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            firstScoresBox.add(cellBox);
        }
        firstScoresBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // others scores
        Box scoresBox = Box.createVerticalBox();
        while (playerScoredIt.hasNext()) {
            Box rowBox = Box.createHorizontalBox();

            for (int i = 0; playerScoredIt.hasNext() && i < 5; i++) {
                Box cellBox = Box.createHorizontalBox();
                PlayerScored playerScored = playerScoredIt.next();

                JLabel posLabel = new JLabel(positon.toString() + "  ");
                posLabel.setFont(new Font("Verdana", Font.BOLD, 16));

                JLabel label = new JLabel(playerScored.getPlayerLoader().getName() + " (" + playerScored.getScore() + ")");
                label.setIcon(ImageRessources.getInstance().getRedIcon());
                label.setIcon(ImageRessources.getInstance().getGravatar(playerScored.getPlayerLoader().getGravatarHash(), 40));
                label.setFont(new Font("Verdana", Font.ITALIC, 14));


                positon++;
                cellBox.add(posLabel);
                cellBox.add(label);
                cellBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                rowBox.add(cellBox);
                rowBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 30));
            }
            scoresBox.add(rowBox);
        }
        scoresBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.remove(1);
        this.remove(2);
        this.add(firstScoresBox, 1);
        this.add(scoresBox, 2);
    }

    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel titleLabel = new JLabel("Ranking");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Box firstScoresBox = Box.createHorizontalBox();
        Box scoresBox = Box.createVerticalBox();

        JButton button = new JButton("Back");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectFourGame.backToGame();
            }
        });

        this.add(titleLabel);
        this.add(firstScoresBox);
        this.add(scoresBox);
        this.add(button);
    }
}
