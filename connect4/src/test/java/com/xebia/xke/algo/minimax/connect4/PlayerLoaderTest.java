package com.xebia.xke.algo.minimax.connect4;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class PlayerLoaderTest {

    private PlayerLoader playerLoader;

    @Before
    public void init() {
        playerLoader = new PlayerLoader(new File(PlayerLoaderTest.class.getResource("/players").getFile()));
    }

    @Test
    public void should_load_all_players() throws PlayerLoadingException {
        //given

        //when
        Map<String, Player> players = playerLoader.loadAllPlayers();

        //then
        assertThat(players.size()).isEqualTo(2);
        assertThat(players.get("Idiot")).isInstanceOf(Player.class);
        assertThat(players.get("Idiot").getClass().getName()).isEqualTo("com.xebia.xke.algo.minimax.player.idiot.PlayerIdiot");
        assertThat(players.get("Medium-2")).isInstanceOf(Player.class);
        assertThat(players.get("Medium-2").getClass().getName()).isEqualTo("com.xebia.xke.algo.minimax.player.medium.PlayerAlphabetaWithStateV3");
    }
}
