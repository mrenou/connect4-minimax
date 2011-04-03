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
            sleepAutoPlayersThread(0);

            while (!connectFourGame.getMatch().isEndMatch()) {

                connectFourGame.noHumanturn();

                if (connectFourGame.getMatch().getNextPlayer() instanceof HumanPlayer) {
                    sleepAutoPlayersThread(0);
                }
            }
        }
    }
}
