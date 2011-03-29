package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.CounterColor;

import javax.swing.*;

public class ImageRessources {

    private ImageIcon redIcon;
    private ImageIcon yellowIcon;
    private ImageIcon emptyIcon;

    private static final ImageRessources INSTANCE = new ImageRessources();

    private ImageRessources() {
        redIcon = new ImageIcon(this.getClass().getResource("/images/counter-red.png"));
        yellowIcon = new ImageIcon(this.getClass().getResource("/images/counter-yellow.png"));
        emptyIcon = new ImageIcon(this.getClass().getResource("/images/counter-empty.png"));
    }

    public static ImageRessources getInstance() {
        return INSTANCE;
    }

    public ImageIcon getImageIconByCounterColor(CounterColor counterColor) {
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

    public ImageIcon getRedIcon() {
        return redIcon;
    }

    public ImageIcon getYellowIcon() {
        return yellowIcon;
    }

    public ImageIcon getEmptyIcon() {
        return emptyIcon;
    }
}
