package com.xebia.xke.algo.minimax.ui;

import com.xebia.xke.algo.minimax.connect4.CounterColor;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageRessources {

    private ImageIcon redIcon;
    private ImageIcon yellowIcon;
    private ImageIcon emptyIcon;
    private ImageIcon defaultGravatar;

    private static final ImageRessources INSTANCE = new ImageRessources();

    private ImageRessources() {
        redIcon = new ImageIcon(this.getClass().getResource("/images/counter-red.png"));
        yellowIcon = new ImageIcon(this.getClass().getResource("/images/counter-yellow.png"));
        emptyIcon = new ImageIcon(this.getClass().getResource("/images/counter-empty.png"));
        defaultGravatar = new ImageIcon(this.getClass().getResource("/images/default-gravatar.png"));
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

    public ImageIcon getDefaultGravatar() {
        return defaultGravatar;
    }

    public ImageIcon getGravatar(String gravatarHash, int size) {
        try {
            String urlString = "http://www.gravatar.com/avatar/" + gravatarHash + "?s=" + size;

            URL url = new URL(urlString);
            //TODO
            return getDefaultGravatar();
            //return new ImageIcon(url);
        } catch (MalformedURLException e) {
            return getDefaultGravatar();
        } catch (IOException e) {
            return getDefaultGravatar();
        }
    }
}
