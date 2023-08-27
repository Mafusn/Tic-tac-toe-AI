package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();
        AiPlayer aiPlayer0 = new AiPlayer('X', 0);
        AiPlayer aiPlayer1 = new AiPlayer('X', 1);
        AiPlayer aiPlayer2 = new AiPlayer('X', 2);
        AiPlayer aiPlayer3 = new AiPlayer('X', 3);
        AiPlayer aiPlayer4 = new AiPlayer('X', 4);
        AiPlayer aiPlayer5 = new AiPlayer('X', 5);
        AiPlayer aiPlayer6 = new AiPlayer('X', 6);
        AiPlayer aiPlayer7 = new AiPlayer('X', 7);
        AiPlayer aiPlayer8 = new AiPlayer('X', 8);

        aiPlayer1.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100000, 1, 10);
        aiPlayer3.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100000, 3, 100);
        aiPlayer4.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100000, 4, 1000);
        aiPlayer5.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100000, 5, 10000);

        aiPlayer2.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100000, 2, 10);
        aiPlayer6.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100000, 6, 100);
        aiPlayer7.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100000, 7, 1000);
        aiPlayer8.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100000, 8, 10000);


        /*int numGames= 1000;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startOnlyAiGameWithoutDisplaying(aiPlayer2, aiPlayer0, 0.1);
        }

        System.out.println("X wins: " + board.getXWins() + "\nO wins: " + board.getOWins() + "\nTies: " + board.getTies());

        Board board1 = new Board();
        for (int i = 0; i < numGames; i++) {
            board1.clearBoard();
            board1.startOnlyAiGameWithoutDisplaying(aiPlayer0, aiPlayer2, 0.1);
        }

        System.out.println("X wins: " + board1.getXWins() + "\nO wins: " + board1.getOWins() + "\nTies: " + board1.getTies());*/
    }
}