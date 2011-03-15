package com.xebia.xke.algo.minimax.connect4;

import com.xebia.xke.algo.minimax.connect4.ConnectFourConfig;
import com.xebia.xke.algo.minimax.connect4.Match;
import com.xebia.xke.algo.minimax.connect4.Player;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MatchTest {

    //TODO REVIEW
    @Test
    public void should_play_a_game() {
        ConnectFourConfig connectFourConfig = ConnectFourConfig.create().withNbColumns(4).withColumnSize(4);
        //Match match = new Match(connectFourConfig, new PlayerEasy(), new PlayerEasy());

        //Player firstPlayer = match.getNextPlayer();

        //assertThat(match.play()).isEqualTo(firstPlayer);
    }

}
