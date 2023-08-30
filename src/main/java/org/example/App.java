package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();

        // Random mod random = 59% winrate for startende spiller

        AiPlayer aiPlayer0 = new AiPlayer(0);
        AiPlayer aiPlayer1 = new AiPlayer(1); // Bedst trænet med at 'O' bliver sat random - 0.01 epsilon omkring 75% winrate
        AiPlayer aiPlayer2 = new AiPlayer(2); // trænet på samme måde som 3 vist - 0.01 epsilon omkring 75% winrate
        AiPlayer aiPlayer3 = new AiPlayer(3); // 0.01 epsilon omkring 76% winrate
        AiPlayer aiPlayer4 = new AiPlayer(4);
        AiPlayer aiPlayer5 = new AiPlayer(5);
        AiPlayer aiPlayer6 = new AiPlayer(6);
        AiPlayer aiPlayer7 = new AiPlayer(7);
        AiPlayer aiPlayer8 = new AiPlayer(8);
        AiPlayer aiPlayer9 = new AiPlayer(9);
        AiPlayer aiPlayer10 = new AiPlayer(10);
        AiPlayer aiPlayer11 = new AiPlayer(11);
        AiPlayer aiPlayer12 = new AiPlayer(12);
        AiPlayer aiPlayer14 = new AiPlayer(14);
        AiPlayer aiPlayer15 = new AiPlayer(15);
        AiPlayer aiPlayer16 = new AiPlayer(16);
        AiPlayer aiPlayer17 = new AiPlayer(17);
        AiPlayer aiPlayer18 = new AiPlayer(18);

        aiPlayer4.getNeuralNetworkPlayer().trainSelfPlay(100000, 100, 'X');
        aiPlayer5.getNeuralNetworkPlayer().trainSelfPlay(100000, 1000, 'X');
        aiPlayer6.getNeuralNetworkPlayer().trainSelfPlay(250000, 100, 'X');
        aiPlayer7.getNeuralNetworkPlayer().trainSelfPlay(250000, 1000, 'X');
        aiPlayer8.getNeuralNetworkPlayer().trainSelfPlay(500000, 100, 'X');
        aiPlayer9.getNeuralNetworkPlayer().trainSelfPlay(500000, 1000, 'X');
        aiPlayer10.getNeuralNetworkPlayer().trainSelfPlay(1000000, 100, 'X');
        aiPlayer11.getNeuralNetworkPlayer().trainSelfPlay(1000000, 1000, 'X');

        /*aiPlayer15.getNeuralNetworkPlayer().trainSelfPlay(500000, 100, 'X');
        aiPlayer16.getNeuralNetworkPlayer().trainSelfPlay(500000, 1000, 'X');
        aiPlayer17.getNeuralNetworkPlayer().trainSelfPlay(500000, 100, 'X');
        aiPlayer18.getNeuralNetworkPlayer().trainSelfPlay(500000, 1000, 'X');*/

        int numGames= 10000;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startOnlyAiGameWithoutDisplaying(aiPlayer4, 0.01);
            //board.startOnlyAiGame(aiPlayer1, aiPlayer2, 0.1);
        }
        board.showWins(numGames);

        Board board1 = new Board();
        for (int i = 0; i < numGames; i++) {
            board1.clearBoard();
            board1.startOnlyAiGameWithoutDisplaying(aiPlayer5, 0.01);
        }
        board1.showWins(numGames);

        Board board2 = new Board();
        for (int i = 0; i < numGames; i++) {
            board2.clearBoard();
            board2.startOnlyAiGameWithoutDisplaying(aiPlayer6, 0.01);
        }
        board2.showWins(numGames);

        Board board3 = new Board();
        for (int i = 0; i < numGames; i++) {
            board3.clearBoard();
            board3.startOnlyAiGameWithoutDisplaying(aiPlayer7, 0.01);
        }
        board3.showWins(numGames);

        Board board4 = new Board();
        for (int i = 0; i < numGames; i++) {
            board4.clearBoard();
            board4.startOnlyAiGameWithoutDisplaying(aiPlayer8, 0.01);
        }
        board4.showWins(numGames);

        Board board5 = new Board();
        for (int i = 0; i < numGames; i++) {
            board5.clearBoard();
            board5.startOnlyAiGameWithoutDisplaying(aiPlayer9, 0.01);
        }
        board5.showWins(numGames);

        Board board6 = new Board();
        for (int i = 0; i < numGames; i++) {
            board6.clearBoard();
            board6.startOnlyAiGameWithoutDisplaying(aiPlayer10, 0.01);
        }
        board6.showWins(numGames);

        Board board7 = new Board();
        for (int i = 0; i < numGames; i++) {
            board7.clearBoard();
            board7.startOnlyAiGameWithoutDisplaying(aiPlayer11, 0.01);
        }
        board7.showWins(numGames);

        /*Board board8 = new Board();
        for (int i = 0; i < numGames; i++) {
            board8.clearBoard();
            board8.startOnlyAiGameWithoutDisplaying(aiPlayer10, 0.1);
        }
        board8.showWins(numGames);

        Board board9 = new Board();
        for (int i = 0; i < numGames; i++) {
            board9.clearBoard();
            board9.startOnlyAiGameWithoutDisplaying(aiPlayer11, 0.1);
        }
        board9.showWins(numGames);

        /*Board board10 = new Board();
        for (int i = 0; i < numGames; i++) {
            board10.clearBoard();
            board10.startOnlyAiGameWithoutDisplaying(aiPlayer12, 0.1);
        }
        board10.showWins(numGames);

        Board board11 = new Board();
        for (int i = 0; i < numGames; i++) {
            board11.clearBoard();
            board11.startOnlyAiGameWithoutDisplaying(aiPlayer14, 0.1);
        }
        board11.showWins(numGames);*/
    }
}