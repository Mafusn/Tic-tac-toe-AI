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

        //aiPlayer0.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(100, 0, 100);
        //aiPlayer0.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(100, 0, 1000);

        /*aiPlayer1.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(150000, 1, 1000);
        aiPlayer2.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(250000, 2, 1000);

        aiPlayer1.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(250000, 1, 1000);
        aiPlayer2.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(150000, 2, 1000);*/



        int numGames= 1000;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startOnlyAiGameWithoutDisplaying(aiPlayer0, aiPlayer1, 0.1);
        }
        System.out.println("X wins: " + board.getXWins() + "\nO wins: " + board.getOWins() + "\nTies: " + board.getTies());

        Board board1 = new Board();
        for (int i = 0; i < numGames; i++) {
            board1.clearBoard();
            board1.startOnlyAiGameWithoutDisplaying(aiPlayer1, aiPlayer0, 0.1);
        }
        System.out.println("X wins: " + board1.getXWins() + "\nO wins: " + board1.getOWins() + "\nTies: " + board1.getTies());

        Board board2 = new Board();
        for (int i = 0; i < numGames; i++) {
            board2.clearBoard();
            board2.startOnlyAiGameWithoutDisplaying(aiPlayer0, aiPlayer2, 0.1);
        }
        System.out.println("X wins: " + board2.getXWins() + "\nO wins: " + board2.getOWins() + "\nTies: " + board2.getTies());

        Board board3 = new Board();
        for (int i = 0; i < numGames; i++) {
            board3.clearBoard();
            board3.startOnlyAiGameWithoutDisplaying(aiPlayer2, aiPlayer0, 0.1);
        }
        System.out.println("X wins: " + board3.getXWins() + "\nO wins: " + board3.getOWins() + "\nTies: " + board3.getTies());

        /*Board board4 = new Board();
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