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

    private File jarDirectory;

    public PlayerLoader(File jarDirectory) {
        this.jarDirectory = jarDirectory;
    }

    public Map<String, Player> loadAllPlayers() throws PlayerLoadingException {
        Map<String, Player> players = new HashMap<String,Player>();

        File[] jarFiles = jarDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if (s.endsWith(".jar")) {
                    return true;
                }
                return false;
            }

        });
        for (File jarFile : jarFiles) {
            Player player = loadPlayer(jarFile);

            players.put(player.getName(), player);
        }
        return players;
    }

    private Player loadPlayer(File jarFile) throws PlayerLoadingException {
        if (!jarFile.exists()) {
            throw new PlayerLoadingException("Jar file doesn't exist : " + jarFile.getAbsolutePath());
        }
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {jarFile.toURI().toURL()});
            String className = getPlayerClassName(jarFile, classLoader);
            Class<Player> clazz = (Class<Player>) classLoader.loadClass(className);
            Player player = clazz.newInstance();
            return player;
        } catch (MalformedURLException e) {
            throw new PlayerLoadingException("Cannot load player from jar : " + jarFile.getAbsolutePath(), e);
        } catch (ClassNotFoundException e) {
            throw new PlayerLoadingException("Cannot load player from jar : " + jarFile.getAbsolutePath(), e);
        } catch (InstantiationException e) {
            throw new PlayerLoadingException("Cannot load player from jar : " + jarFile.getAbsolutePath(), e);
        } catch (IllegalAccessException e) {
            throw new PlayerLoadingException("Cannot load player from jar : " + jarFile.getAbsolutePath(), e);
        }
    }

    private String getPlayerClassName(File jarFile, URLClassLoader classLoader) throws PlayerLoadingException {
        InputStream playerPropertiesIS = null;
        Properties properties = new Properties();
        try {
            URL playerPropertiesURL = classLoader.findResource("player.properties");

            if (playerPropertiesURL == null) {
                throw new PlayerLoadingException("Cannot load player.properties from jar : " + jarFile.getAbsolutePath());
            }

            playerPropertiesIS = playerPropertiesURL.openStream();
            properties.load(playerPropertiesIS);
        } catch (IOException e) {
            throw new PlayerLoadingException("Cannot load player.properties from jar : " + jarFile.getAbsolutePath(), e);
        } finally {
            if (playerPropertiesIS != null) {
                try {
                    playerPropertiesIS.close();
                } catch (IOException e) {
                    //
                }
            }
        }
        return properties.getProperty("player.class");
    }

}
