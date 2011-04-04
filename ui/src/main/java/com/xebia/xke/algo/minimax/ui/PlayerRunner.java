package com.xebia.xke.algo.minimax.ui;

public class PlayerRunner implements Runnable {

    private ConnectFourGame connectFourGame;

    public PlayerRunner(ConnectFourGame connectFourGame) {
        this.connectFourGame = connectFourGame;
    }

    private void sleepAutoPlayersThread(long sleepTime) {
        synchronized (connectFourGame.getWakeUpFlag()) {
            try {
                synchronized (connectFourGame.getWakeUpFlag()) {
                    connectFourGame.getWakeUpFlag().wait(0);
                }
            }catch (InterruptedException e) {
                //
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            if (connectFourGame.getTournament().isRunning()) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {

                }
                connectFourGame.start();
            } else {
                sleepAutoPlayersThread(0);
            }

            if (connectFourGame.isTournamentMode()) {
                connectFourGame.displayInfo(connectFourGame.getMatch().getPlayer1().getName() + " VS " + connectFourGame.getMatch().getPlayer2().getName());

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {

                }

            }

            while (!connectFourGame.getMatch().isEndMatch()) {

                connectFourGame.noHumanturn();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }

                if (connectFourGame.getMatch().getNextPlayer() instanceof HumanPlayer) {
                    sleepAutoPlayersThread(0);
                }
            }
        }
    }
}
