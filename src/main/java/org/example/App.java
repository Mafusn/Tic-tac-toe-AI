package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();
        AiPlayer aiPlayer0 = new AiPlayer('O', 1);
        AiPlayer aiPlayer1 = new AiPlayer('O', 1);
        AiPlayer aiPlayer2 = new AiPlayer('O', 1);
        AiPlayer aiPlayer3 = new AiPlayer('X', 3);
        AiPlayer aiPlayer4 = new AiPlayer('X', 4);

        aiPlayer4.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100000, 4);

        int numGames= 1000;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startOnlyAiGameWithoutDisplaying(aiPlayer3, aiPlayer1, 0.1);
        }

        System.out.println("X wins: " + board.getXWins() + "\nO wins: " + board.getOWins() + "\nTies: " + board.getTies());

        Board board1 = new Board();
        for (int i = 0; i < numGames; i++) {
            board1.clearBoard();
            board1.startOnlyAiGameWithoutDisplaying(aiPlayer4, aiPlayer1, 0.1);
        }

        System.out.println("X wins: " + board1.getXWins() + "\nO wins: " + board1.getOWins() + "\nTies: " + board1.getTies());
    }
}