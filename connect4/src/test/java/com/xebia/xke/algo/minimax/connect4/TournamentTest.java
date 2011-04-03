package com.xebia.xke.algo.minimax.connect4;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TournamentTest {
    @Test
    public void testStart2() throws Exception {
        PlayerLoader playerLoader = new PlayerLoader(new File(PlayerLoaderTest.class.getResource("/players").getFile()));

        Map<String, Player> players = playerLoader.loadAllPlayers();


        Tournament tournament = new Tournament();

        for (Player player : players.values()) {
            tournament.addPlayer(player);
        }
        tournament.start();
        tournament.playAllMatches();

        System.out.println(tournament.getStringtScores());
    }

    @Test
    public void init_matches_should_create_all_matches() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        Player player4 = mock(Player.class);
        Tournament tournament = new Tournament();

        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.addPlayer(player3);
        tournament.addPlayer(player4);

        tournament.start();

        assertThat(tournament.matches.size()).isEqualTo(6);

        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player1, player2))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player1, player3))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player1, player4))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player2, player3))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player2, player4))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player3, player4))).isTrue();
    }

    @Test
    public void each_player_win_one_point_if_draw() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");

        Match match = mock(Match.class);
        when(match.play()).thenReturn(null);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);



        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.matches.add(match);

        tournament.running = true;
        tournament.playNextMatch();

        assertThat(tournament.getScore(player1)).isEqualTo(1);
        assertThat(tournament.getScore(player2)).isEqualTo(1);
    }

    @Test
    public void red_player_win_one_point_if_win() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");

        Match match = mock(Match.class);
        when(match.play()).thenReturn(player1);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);

        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.matches.add(match);
        tournament.running = true;
        tournament.playNextMatch();

        assertThat(tournament.getScore(player1)).isEqualTo(3);
        assertThat(tournament.getScore(player2)).isEqualTo(0);
    }


    @Test
    public void yellow_player_win_one_point_if_win() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");

        Match match = mock(Match.class);
        when(match.play()).thenReturn(player2);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);

        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.matches.add(match);
        tournament.running = true;
        tournament.playNextMatch();

        assertThat(tournament.getScore(player1)).isEqualTo(0);
        assertThat(tournament.getScore(player2)).isEqualTo(3);
    }

    @Test
    public void should_return_string_scores() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        Player player3 = getFakePlayer("player3");
        Player player4 = getFakePlayer("player4");

        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.addPlayer(player3);
        tournament.addPlayer(player4);


        Match match = mock(Match.class);
        when(match.play()).thenReturn(player2);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player4);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player4);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player2);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(null);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player2);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player4);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player3);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player4);
        tournament.matches.add(match);

        tournament.running = true;
        tournament.playAllMatches();

        assertThat(tournament.getStringtScores()).isEqualTo("1 player3 9\n" +
                "2 player2 4\n" +
                "3 player4 4\n" +
                "4 player1 0\n");
    }

    @Test
    public void should_sort_players_by_scores() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        Player player3 = getFakePlayer("player3");
        Player player4 = getFakePlayer("player4");

        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.addPlayer(player3);
        tournament.addPlayer(player4);


        Match match = mock(Match.class);
        when(match.play()).thenReturn(player2);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player4);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player4);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player2);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(null);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player2);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player4);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player3);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player4);
        tournament.matches.add(match);

        tournament.running = true;
        tournament.playAllMatches();

        assertThat(tournament.getScores().size()).isEqualTo(4);

        assertThat(tournament.getScores().get(0).getScore()).isEqualTo(9);
        assertThat(tournament.getScores().get(0).getPlayer()).isSameAs(player3);

        assertThat(tournament.getScores().get(1).getScore()).isEqualTo(4);
        assertThat(tournament.getScores().get(1).getPlayer()).isSameAs(player2);

        assertThat(tournament.getScores().get(2).getScore()).isEqualTo(4);
        assertThat(tournament.getScores().get(2).getPlayer()).isSameAs(player4);

        assertThat(tournament.getScores().get(3).getScore()).isEqualTo(0);
        assertThat(tournament.getScores().get(3).getPlayer()).isSameAs(player1);

    }


    @Test
    public void should_play_the_next_match() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        Player player3 = getFakePlayer("player3");

        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.addPlayer(player3);

        Match nextMatch = mock(Match.class);
        when(nextMatch.play()).thenReturn(player2);
        when(nextMatch.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(nextMatch.getPlayer(CounterColor.YELLOW)).thenReturn(player2);
        tournament.matches.add(nextMatch);

        Match match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player2);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);

        tournament.running = true;
        tournament.playNextMatch();

        Mockito.verify(nextMatch, Mockito.times(1)).play();
    }

    @Test
    public void should_play_all_matches() {
        List<Match> matches = new ArrayList<Match>();

        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        Player player3 = getFakePlayer("player3");

        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.addPlayer(player3);

        Match match = mock(Match.class);
        when(match.play()).thenReturn(player2);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);
        tournament.matches.add(match);
        matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);
        matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player2);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);
        matches.add(match);

        tournament.running = true;
        tournament.playAllMatches();

        Mockito.verify(matches.get(0), Mockito.times(1)).play();
        Mockito.verify(matches.get(1), Mockito.times(1)).play();
        Mockito.verify(matches.get(2), Mockito.times(1)).play();

        assertThat(tournament.matches.size()).isEqualTo(0);
    }

    @Test
    public void should_reinit_scores() {
        List<Match> matches = new ArrayList<Match>();

        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        Player player3 = getFakePlayer("player3");

        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.addPlayer(player3);

        Match match = mock(Match.class);
        when(match.play()).thenReturn(player2);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);
        tournament.matches.add(match);
        matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);
        matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player2);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);
        matches.add(match);

        tournament.running = true;
        tournament.playAllMatches();


        assertThat(tournament.getScores().get(0).getScore()).isEqualTo(6);
        assertThat(tournament.getScores().get(1).getScore()).isEqualTo(3);
        assertThat(tournament.getScores().get(2).getScore()).isEqualTo(0);

        tournament.start();

        assertThat(tournament.getScores().get(0).getScore()).isEqualTo(0);
        assertThat(tournament.getScores().get(1).getScore()).isEqualTo(0);
        assertThat(tournament.getScores().get(2).getScore()).isEqualTo(0);
    }

    @Test(expected = IllegalStateException.class)
    public void should_throw_exception_if_running() {
        Tournament tournament = new Tournament();


        tournament.running = true;

        tournament.checkIfNotRunning();
    }

    @Test(expected = IllegalStateException.class)
    public void should_throw_exception_if_not_running() {
        Tournament tournament = new Tournament();


        tournament.running = false;

        tournament.checkIfRunning();
    }

    public Player getFakePlayer(final String playerName) {
        AtomicReference<Player> player = new AtomicReference<Player>(new Player() {
            @Override
            public int play(CounterColor counterColor, Board board) {
                return 0;
            }

            @Override
            public String getStats() {
                return null;
            }

            @Override
            public String getName() {
                return playerName;
            }
        });
        return player.get();
    }

    @Test(expected = IllegalStateException.class)
    public void should_check_if_not_running_when_add_player() {
        Tournament tournament = new Tournament();

        tournament.running = true;

        tournament.addPlayer(Mockito.mock(Player.class));
        tournament.start();
    }

    @Test(expected = IllegalStateException.class)
    public void should_check_if_not_running_when_start() {
        Tournament tournament = new Tournament();

        tournament.running = true;

        tournament.start();
    }

    @Test(expected = IllegalStateException.class)
    public void should_check_if_not_running_when_play_next_match() {
        Tournament tournament = new Tournament();

        tournament.running = false;

        tournament.playNextMatch();
    }

    @Test(expected = IllegalStateException.class)
    public void should_check_if_not_running_when_play_all_match() {
        Tournament tournament = new Tournament();

        tournament.running = false;

        tournament.playAllMatches();
    }

    @Test(expected = IllegalStateException.class)
    public void should_check_if_not_running_when_poll_next_match() {
        Tournament tournament = new Tournament();

        tournament.running = false;

        tournament.pollNextMatch();
    }

    @Test
    public void set_running_true_when_start() {
        Tournament tournament = new Tournament();

        tournament.running = false;

        tournament.start();

        assertThat(tournament.running).isEqualTo(true);
    }

    @Test
    public void set_running_false_when_set_match_result() {
        List<Match> matches = new ArrayList<Match>();

        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");

        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);

        Match match = mock(Match.class);
        when(match.play()).thenReturn(player2);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);
        tournament.matches.add(match);
        matches.add(match);

        tournament.running = true;


        Match currentMatch = tournament.pollNextMatch();

        Player winner = currentMatch.play();
        tournament.setMatchResult(winner, match);

        assertThat(tournament.running).isEqualTo(false);
    }

    @Test
    public void should_set_match_result() {
        List<Match> matches = new ArrayList<Match>();

        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        Player player3 = getFakePlayer("player3");

        Tournament tournament = new Tournament();
        tournament.addPlayer(player1);
        tournament.addPlayer(player2);
        tournament.addPlayer(player3);

        Match match = mock(Match.class);
        when(match.play()).thenReturn(player2);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);
        tournament.matches.add(match);
        matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);
        matches.add(match);

        match = mock(Match.class);
        when(match.play()).thenReturn(player3);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player2);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player3);
        tournament.matches.add(match);
        matches.add(match);

        tournament.running = true;

        while (tournament.getNextMatch() != null) {
            Match currentMatch = tournament.pollNextMatch();

            Player winner = currentMatch.play();
            tournament.setMatchResult(winner, match);
        }



        assertThat(tournament.getScores().get(0).getScore()).isEqualTo(6);
        assertThat(tournament.getScores().get(1).getScore()).isEqualTo(3);
        assertThat(tournament.getScores().get(2).getScore()).isEqualTo(0);
    }
}
