package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();
        AiPlayer aiPlayer0 = new AiPlayer('O', 1);
        AiPlayer aiPlayer1 = new AiPlayer('O', 1);
        AiPlayer aiPlayer2 = new AiPlayer('O', 2);
        AiPlayer aiPlayer3 = new AiPlayer('X', 3); // Overpowered


        aiPlayer3.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(250000, 3);

        int numGames= 100;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startOnlyAiGameWithoutDisplaying(aiPlayer3, aiPlayer0, 0.1);
        }

        System.out.println("X wins: " + board.getXWins() + "\nO wins: " + board.getOWins() + "\nTies: " + board.getTies());

        Board board1 = new Board();
        for (int i = 0; i < numGames; i++) {
            board1.clearBoard();
            board1.startOnlyAiGameWithoutDisplaying(aiPlayer0, aiPlayer3, 0.1);
        }

        System.out.println("X wins: " + board1.getXWins() + "\nO wins: " + board1.getOWins() + "\nTies: " + board1.getTies());
    }
}