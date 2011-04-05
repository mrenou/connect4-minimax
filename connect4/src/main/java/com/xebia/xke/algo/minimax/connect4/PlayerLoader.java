package com.xebia.xke.algo.minimax.connect4;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PlayerLoader {

    private String name;

    private String gravatarHash;

    private Class<? extends Player> playerClass;

    public PlayerLoader(String name, Class<? extends Player> playerClass, String gravatarMail) {
        this.name = name;
        this.playerClass = playerClass;
        if (gravatarMail != null) {
            this.gravatarHash = MD5Util.md5Hex(gravatarMail);
        }
    }

    public Player loadPlayer() {
        try {
            Player player = playerClass.newInstance();
            player.setName(name);
            return player;
        } catch (InstantiationException e) {
            throw new IllegalStateException("Cannot create instance from class : " + playerClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot create instance from class : " + playerClass.getName(), e);
        }
    }

    public String getName() {
        return name;
    }

    public String getGravatarHash() {
        return gravatarHash;
    }
}
