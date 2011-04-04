package com.xebia.xke.algo.minimax.connect4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class PlayerLoadersLoaderTest {

    private PlayerLoadersLoader playerLoadersLoader;

    @Before
    public void init() {
        playerLoadersLoader = new PlayerLoadersLoader(new File(PlayerLoadersLoaderTest.class.getResource("/players").getFile()));
    }

    @Test
    public void should_load_all_player_loaders() throws PlayerLoadingException {
        //given

        //when
        Map<String, PlayerLoader> players = playerLoadersLoader.loadAllPlayers();

        //then
        assertThat(players.size()).isEqualTo(2);
        assertThat(players.get("Idiot")).isInstanceOf(PlayerLoader.class);
        assertThat(players.get("Idiot").loadPlayer().getClass().getName()).isEqualTo("com.xebia.xke.algo.minimax.player.idiot.PlayerIdiot");
        assertThat(players.get("Easy")).isInstanceOf(PlayerLoader.class);
        assertThat(players.get("Easy").loadPlayer().getClass().getName()).isEqualTo("com.xebia.xke.algo.minimax.player.easy.PlayerAlphabetaWithStateV1");
    }
}
