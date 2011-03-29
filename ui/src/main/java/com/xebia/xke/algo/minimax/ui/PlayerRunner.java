package com.xebia.xke.algo.minimax.ui;

public class PlayerRunner implements Runnable {

    private ConnectFourGame connectFourGame;

    public PlayerRunner(ConnectFourGame connectFourGame) {
        this.connectFourGame = connectFourGame;
    }

    private void sleepAutoPlayersThread() {
        synchronized (connectFourGame.getWakeUpFlag()) {
            try {
                synchronized (connectFourGame.getWakeUpFlag()) {
                    connectFourGame.getWakeUpFlag().wait();
                }
            }catch (InterruptedException e) {
                //
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            sleepAutoPlayersThread();

            while (!connectFourGame.getMatch().isEndMatch()) {
                connectFourGame.noHumanturn();
                if (connectFourGame.getMatch().getNextPlayer() instanceof HumanPlayer) {
                    sleepAutoPlayersThread();
                }
            }
        }
    }
}
