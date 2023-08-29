package org.example;

import java.io.IOException;
import java.util.ArrayList;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();

        AiPlayer aiPlayer0 = new AiPlayer(0);
        AiPlayer aiPlayer1 = new AiPlayer(1); // klart bedst, starter i midten
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

        //aiPlayer8.getNeuralNetworkPlayer().trainSelfPlayToBeFirst(1000000, 8, 1000);
        //aiPlayer9.getNeuralNetworkPlayer().trainSelfPlayToBeSecond(1000000, 9, 1000); // træner med at 'O' bliver sat random

        int numGames= 10000;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startOnlyAiGameWithoutDisplaying(aiPlayer10, aiPlayer1, 0.1);
            //board.startOnlyAiGame(aiPlayer1, aiPlayer2, 0.1);
        }
        board.showWins(numGames);

        Board board1 = new Board();
        for (int i = 0; i < numGames; i++) {
            board1.clearBoard();
            board1.startOnlyAiGameWithoutDisplaying(aiPlayer9, aiPlayer1, 0.1);
        }
        board1.showWins(numGames);

        Board board2 = new Board();
        for (int i = 0; i < numGames; i++) {
            board2.clearBoard();
            board2.startOnlyAiGameWithoutDisplaying(aiPlayer10, aiPlayer1, 0.0001);
        }
        board2.showWins(numGames);

        Board board3 = new Board();
        for (int i = 0; i < numGames; i++) {
            board3.clearBoard();
            board3.startOnlyAiGameWithoutDisplaying(aiPlayer9, aiPlayer1, 0.0001);
        }
        board3.showWins(numGames);

        /*Board board4 = new Board();
        for (int i = 0; i < numGames; i++) {
            board4.clearBoard();
            board4.startOnlyAiGameWithoutDisplaying(aiPlayer4, aiPlayer1, 0.1);
        }
        board4.showWins(numGames);

        Board board5 = new Board();
        for (int i = 0; i < numGames; i++) {
            board5.clearBoard();
            board5.startOnlyAiGameWithoutDisplaying(aiPlayer5, aiPlayer1, 0.1);
        }
        board5.showWins(numGames);

        /*Board board6 = new Board();
        for (int i = 0; i < numGames; i++) {
            board6.clearBoard();
            board6.startOnlyAiGameWithoutDisplaying(aiPlayer7, aiPlayer0, 0.1);
        }
        board6.showWins(numGames);

        Board board7 = new Board();
        for (int i = 0; i < numGames; i++) {
            board7.clearBoard();
            board7.startOnlyAiGameWithoutDisplaying(aiPlayer8, aiPlayer0, 0.1);
        }
        board7.showWins(numGames);*/
    }
}