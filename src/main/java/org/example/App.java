package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();
        AiPlayer aiPlayer0 = new AiPlayer(0);
        AiPlayer aiPlayer1 = new AiPlayer(1);
        AiPlayer aiPlayer2 = new AiPlayer(2);
        AiPlayer aiPlayer3 = new AiPlayer(3);
        AiPlayer aiPlayer4 = new AiPlayer(4);
        AiPlayer aiPlayer5 = new AiPlayer(5);
        AiPlayer aiPlayer6 = new AiPlayer(6);
        AiPlayer aiPlayer7 = new AiPlayer(7);
        AiPlayer aiPlayer8 = new AiPlayer(8);
        AiPlayer aiPlayer9 = new AiPlayer(9);
        AiPlayer aiPlayer10 = new AiPlayer(10);
        AiPlayer aiPlayer11 = new AiPlayer(11);

        //aiPlayer11.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(1000000, 11, 100);
        //aiPlayer11.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(1000000, 11, 1000);

        /*aiPlayer1.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100000, 1, 10);
        aiPlayer3.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100000, 3, 100);
        aiPlayer4.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100000, 4, 1000);
        aiPlayer5.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100000, 5, 10000);

        aiPlayer2.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100000, 2, 10);
        aiPlayer6.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100000, 6, 100);
        aiPlayer7.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100000, 7, 1000);
        aiPlayer8.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100000, 8, 10000);*/


        int numGames= 1000;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startOnlyAiGameWithoutDisplaying(aiPlayer11, aiPlayer2, 0.1);
        }
        System.out.println("X wins: " + board.getXWins() + "\nO wins: " + board.getOWins() + "\nTies: " + board.getTies());

        Board board1 = new Board();
        for (int i = 0; i < numGames; i++) {
            board1.clearBoard();
            board1.startOnlyAiGameWithoutDisplaying(aiPlayer2, aiPlayer11, 0.1);
        }
        System.out.println("X wins: " + board1.getXWins() + "\nO wins: " + board1.getOWins() + "\nTies: " + board1.getTies());

        /*Board board2 = new Board();
        for (int i = 0; i < numGames; i++) {
            board2.clearBoard();
            board2.startOnlyAiGameWithoutDisplaying(aiPlayer3, aiPlayer0, 0.1);
        }
        System.out.println("X wins: " + board2.getXWins() + "\nO wins: " + board2.getOWins() + "\nTies: " + board2.getTies());

        Board board3 = new Board();
        for (int i = 0; i < numGames; i++) {
            board3.clearBoard();
            board3.startOnlyAiGameWithoutDisplaying(aiPlayer4, aiPlayer0, 0.1);
        }
        System.out.println("X wins: " + board3.getXWins() + "\nO wins: " + board3.getOWins() + "\nTies: " + board3.getTies());

        Board board4 = new Board();
        for (int i = 0; i < numGames; i++) {
            board4.clearBoard();
            board4.startOnlyAiGameWithoutDisplaying(aiPlayer5, aiPlayer0, 0.1);
        }
        System.out.println("X wins: " + board4.getXWins() + "\nO wins: " + board4.getOWins() + "\nTies: " + board4.getTies());

        Board board5 = new Board();
        for (int i = 0; i < numGames; i++) {
            board5.clearBoard();
            board5.startOnlyAiGameWithoutDisplaying(aiPlayer6, aiPlayer0, 0.1);
        }
        System.out.println("X wins: " + board5.getXWins() + "\nO wins: " + board5.getOWins() + "\nTies: " + board5.getTies());

        Board board6 = new Board();
        for (int i = 0; i < numGames; i++) {
            board6.clearBoard();
            board6.startOnlyAiGameWithoutDisplaying(aiPlayer7, aiPlayer0, 0.1);
        }
        System.out.println("X wins: " + board6.getXWins() + "\nO wins: " + board6.getOWins() + "\nTies: " + board6.getTies());

        Board board7 = new Board();
        for (int i = 0; i < numGames; i++) {
            board7.clearBoard();
            board7.startOnlyAiGameWithoutDisplaying(aiPlayer8, aiPlayer0, 0.1);
        }
        System.out.println("X wins: " + board7.getXWins() + "\nO wins: " + board7.getOWins() + "\nTies: " + board7.getTies());*/
    }
}