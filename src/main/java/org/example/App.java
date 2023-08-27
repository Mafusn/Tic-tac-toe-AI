package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();
        AiPlayer aiPlayerX = new AiPlayer('X', 3);
        AiPlayer aiPlayerO = new AiPlayer('O', 1);

        aiPlayerX.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100000, 3);

        int numGames= 100;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startOnlyAiGameWithoutDisplaying(aiPlayerX, aiPlayerO, 0.1);
        }

        System.out.println("X wins: " + board.getXWins() + "\nO wins: " + board.getOWins() + "\nTies: " + board.getTies());
    }
}