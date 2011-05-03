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

    class FakePlayer extends SimplePlayer {

        @Override
        public int play(CounterColor counterColor, Board board) {
            return 0;
        }

        @Override
        public String getStats() {
            return null;
        }
    }

    @Test
    public void testStart2() throws Exception {
        PlayerLoadersLoader playerLoadersLoader = new PlayerLoadersLoader(new File(PlayerLoadersLoaderTest.class.getResource("/players").getFile()));

        Map<String, PlayerLoader> players = playerLoadersLoader.loadAllPlayers();


        Tournament tournament = new Tournament();

        for (PlayerLoader player : players.values()) {
            tournament.addPlayerLoader(player);
        }
        tournament.start();
        tournament.playAllMatches();
    }

    @Test
    public void init_matches_should_create_all_matches() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        Player player3 = getFakePlayer("player3");
        Player player4 = getFakePlayer("player4");
        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);
        PlayerLoader playerLoader3 = getMockPlayerLoader("player3", player3);
        PlayerLoader playerLoader4 = getMockPlayerLoader("player4", player4);

        Tournament tournament = new Tournament();

        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.addPlayerLoader(playerLoader3);
        tournament.addPlayerLoader(playerLoader4);

        tournament.start();

        assertThat(tournament.matches.size()).isEqualTo(6);

        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player1, player2))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player1, player3))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player1, player4))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player2, player3))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player2, player4))).isTrue();
        assertThat(tournament.matches.poll().getPlayers().values().containsAll(Arrays.asList(player3, player4))).isTrue();
    }

    private PlayerLoader getMockPlayerLoader(String name, Player player) {
        PlayerLoader playerLoader = Mockito.mock(PlayerLoader.class);

        when(playerLoader.getName()).thenReturn(name);
        when(playerLoader.loadPlayer()).thenReturn(player);
        return playerLoader;
    }

    @Test
    public void each_player_win_one_point_if_draw() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);

        Match match = mock(Match.class);
        when(match.play()).thenReturn(null);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.matches.add(match);

        tournament.running = true;
        tournament.playNextMatch();

        assertThat(tournament.getScore(playerLoader1)).isEqualTo(1);
        assertThat(tournament.getScore(playerLoader2)).isEqualTo(1);
    }

    @Test
    public void red_player_win_one_point_if_win() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);

        Match match = mock(Match.class);
        when(match.play()).thenReturn(player1);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.matches.add(match);
        tournament.running = true;
        tournament.playNextMatch();

        assertThat(tournament.getScore(playerLoader1)).isEqualTo(3);
        assertThat(tournament.getScore(playerLoader2)).isEqualTo(0);
    }


    @Test
    public void yellow_player_win_one_point_if_win() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);

        Match match = mock(Match.class);
        when(match.play()).thenReturn(player2);
        when(match.getPlayer(CounterColor.RED)).thenReturn(player1);
        when(match.getPlayer(CounterColor.YELLOW)).thenReturn(player2);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.matches.add(match);
        tournament.running = true;
        tournament.playNextMatch();

        assertThat(tournament.getScore(playerLoader1)).isEqualTo(0);
        assertThat(tournament.getScore(playerLoader2)).isEqualTo(3);
    }

    @Test
    public void should_return_string_scores() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        Player player3 = getFakePlayer("player3");
        Player player4 = getFakePlayer("player4");
        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);
        PlayerLoader playerLoader3 = getMockPlayerLoader("player3", player3);
        PlayerLoader playerLoader4 = getMockPlayerLoader("player4", player4);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.addPlayerLoader(playerLoader3);
        tournament.addPlayerLoader(playerLoader4);


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
        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);
        PlayerLoader playerLoader3 = getMockPlayerLoader("player3", player3);
        PlayerLoader playerLoader4 = getMockPlayerLoader("player4", player4);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.addPlayerLoader(playerLoader3);
        tournament.addPlayerLoader(playerLoader4);


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
        assertThat(tournament.getScores().get(0).getPlayerLoader()).isSameAs(playerLoader3);

        assertThat(tournament.getScores().get(1).getScore()).isEqualTo(4);
        assertThat(tournament.getScores().get(1).getPlayerLoader()).isSameAs(playerLoader2);

        assertThat(tournament.getScores().get(2).getScore()).isEqualTo(4);
        assertThat(tournament.getScores().get(2).getPlayerLoader()).isSameAs(playerLoader4);

        assertThat(tournament.getScores().get(3).getScore()).isEqualTo(0);
        assertThat(tournament.getScores().get(3).getPlayerLoader()).isSameAs(playerLoader1);

    }


    @Test
    public void should_play_the_next_match() {
        Player player1 = getFakePlayer("player1");
        Player player2 = getFakePlayer("player2");
        Player player3 = getFakePlayer("player3");
        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);
        PlayerLoader playerLoader3 = getMockPlayerLoader("player3", player3);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.addPlayerLoader(playerLoader3);

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

        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);
        PlayerLoader playerLoader3 = getMockPlayerLoader("player3", player3);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.addPlayerLoader(playerLoader3);

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

        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);
        PlayerLoader playerLoader3 = getMockPlayerLoader("player3", player3);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.addPlayerLoader(playerLoader3);

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
        Player player = new FakePlayer();
        player.setName(playerName);
        return player;
    }

    @Test(expected = IllegalStateException.class)
    public void should_check_if_not_running_when_add_player() {
        Tournament tournament = new Tournament();

        tournament.running = true;

        tournament.addPlayerLoader(Mockito.mock(PlayerLoader.class));
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

        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);


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

        PlayerLoader playerLoader1 = getMockPlayerLoader("player1", player1);
        PlayerLoader playerLoader2 = getMockPlayerLoader("player2", player2);
        PlayerLoader playerLoader3 = getMockPlayerLoader("player3", player3);

        Tournament tournament = new Tournament();
        tournament.addPlayerLoader(playerLoader1);
        tournament.addPlayerLoader(playerLoader2);
        tournament.addPlayerLoader(playerLoader3);

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
