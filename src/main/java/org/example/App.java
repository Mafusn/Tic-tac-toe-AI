package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();

        AiPlayer aiPlayer0 = new AiPlayer(0);
        AiPlayer aiPlayer1 = new AiPlayer(1); // Bedst trænet med at 'O' bliver sat random
        AiPlayer aiPlayer2 = new AiPlayer(2);
        AiPlayer aiPlayer3 = new AiPlayer(3);
        AiPlayer aiPlayer4 = new AiPlayer(4);
        AiPlayer aiPlayer5 = new AiPlayer(5);
        AiPlayer aiPlayer6 = new AiPlayer(6);
        AiPlayer aiPlayer7 = new AiPlayer(7);
        AiPlayer aiPlayer8 = new AiPlayer(8);
        AiPlayer aiPlayer9 = new AiPlayer(9);

        aiPlayer4.getNeuralNetworkPlayer().trainSelfPlayWithLittleEpsilon(1000000, 4, 1000);
        aiPlayer5.getNeuralNetworkPlayer().trainSelfPlayWithLittleAndConstantEpsilon(1000000, 5, 1000);

        int numGames= 10000;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startOnlyAiGameWithoutDisplaying(aiPlayer4, aiPlayer0, 0.1);
            //board.startOnlyAiGame(aiPlayer1, aiPlayer2, 0.1);
        }
        board.showWins(numGames);

        Board board1 = new Board();
        for (int i = 0; i < numGames; i++) {
            board1.clearBoard();
            board1.startOnlyAiGameWithoutDisplaying(aiPlayer5, aiPlayer0, 0.1);
        }
        board1.showWins(numGames);

        Board board2 = new Board();
        for (int i = 0; i < numGames; i++) {
            board2.clearBoard();
            board2.startOnlyAiGameWithoutDisplaying(aiPlayer4, aiPlayer0, 0.01);
        }
        board2.showWins(numGames);

        Board board3 = new Board();
        for (int i = 0; i < numGames; i++) {
            board3.clearBoard();
            board3.startOnlyAiGameWithoutDisplaying(aiPlayer5, aiPlayer0, 0.01);
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