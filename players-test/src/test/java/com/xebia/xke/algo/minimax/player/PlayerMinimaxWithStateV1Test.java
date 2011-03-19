package com.xebia.xke.algo.minimax.player;

import com.xebia.xke.algo.minimax.connect4.Match;
import com.xebia.xke.algo.minimax.connect4.Player;
import com.xebia.xke.algo.minimax.player.easy.PlayerAlphabetaWithStateV1;
import com.xebia.xke.algo.minimax.player.easy.PlayerMinimaxWithStateV1;
import com.xebia.xke.algo.minimax.player.idiot.PlayerIdiot;
import com.xebia.xke.algo.minimax.player.medium.PlayerAlphabetaWithStateV2;
import com.xebia.xke.algo.minimax.player.medium.PlayerAlphabetaWithStateV3;
import org.junit.Ignore;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
@Ignore
public class PlayerMinimaxWithStateV1Test {

    private static final int NB_MATCHES = 50;

    @Test
    public void should_always_win_vs_player_easy() {

        for (int i = 0; i < NB_MATCHES;i++) {
            Player player1 = new PlayerMinimaxWithStateV1();
            Player player2 = new PlayerIdiot();
            Match match = new Match(player1, player2);

            assertThat(match.play()).isEqualTo(player1);
        }
    }

    @Test
    public void should_always_loose_vs_player_alphabeta_with_state_v1() {

        for (int i = 0; i < NB_MATCHES; i++) {
            Player player1 = new PlayerMinimaxWithStateV1();
            Player player2 = new PlayerAlphabetaWithStateV1();
            Match match = new Match(player1, player2);

            assertThat(match.play()).isEqualTo(player2);
        }
    }

    @Test
    public void should_always_loose_vs_player_alphabeta_with_state_v2() {

        for (int i = 0; i < NB_MATCHES; i++) {
            Player player1 = new PlayerMinimaxWithStateV1();
            Player player2 = new PlayerAlphabetaWithStateV2();
            Match match = new Match(player1, player2);

            assertThat(match.play()).isEqualTo(player2);
        }
    }

    @Test
    public void should_always_loose_vs_player_alphabeta_with_state_v3() {

        for (int i = 0; i < NB_MATCHES; i++) {
            Player player1 = new PlayerMinimaxWithStateV1();
            Player player2 = new PlayerAlphabetaWithStateV3();
            Match match = new Match(player1, player2);

            assertThat(match.play()).isEqualTo(player2);
        }
    }
}
