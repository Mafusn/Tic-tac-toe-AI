package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();

        // Random mod random = 59% winrate for startende spiller
        // Træningsinterval på 1000 er bedst
        // 64 Hidden nodes har virket bedst indtil videre

        // Prøv det her i morgen
        //Hyperparameter Tuning: Experiment with different hyperparameters such as learning rates, batch sizes,
        // and the number of training iterations. Use techniques like grid search or random search
        // to find the best combination of hyperparameters for your specific problem.

        // Samt at give ties plus reward i stedet for negative reward

        AiPlayer aiPlayer0 = new AiPlayer(0); // 84% win rate - Iterations: 500.000, Training interval: 1000, started - 2 hidden layers - See ConfigPictures/Model0 for configuration
        // aiPlayer0 er også bedre end random når den ikke starter

        AiPlayer aiPlayer1 = new AiPlayer(1); //
        AiPlayer aiPlayer2 = new AiPlayer(2); //
        AiPlayer aiPlayer3 = new AiPlayer(3); //
        AiPlayer aiPlayer4 = new AiPlayer(4); //
        AiPlayer aiPlayer5 = new AiPlayer(5); //
        AiPlayer aiPlayer6 = new AiPlayer(6); //
        AiPlayer aiPlayer7 = new AiPlayer(7); //
        AiPlayer aiPlayer8 = new AiPlayer(8); //
        AiPlayer aiPlayer9 = new AiPlayer(9); //

        // Kør den her og se om det gør modellen bedre
        aiPlayer1.getNeuralNetworkPlayer().trainSelfPlayWithExploration(500000, 1000, 'X', 32);
        aiPlayer2.getNeuralNetworkPlayer().trainSelfPlayWithExploration(500000, 1000, 'X', 86);
        aiPlayer3.getNeuralNetworkPlayer().trainSelfPlayWithExploration(500000, 1000, 'X', 100);
        aiPlayer4.getNeuralNetworkPlayer().trainSelfPlayWithExploration(500000, 1000, 'X', 125);
        aiPlayer5.getNeuralNetworkPlayer().trainSelfPlayWithExploration(500000, 1000, 'X', 150);
        aiPlayer6.getNeuralNetworkPlayer().trainSelfPlayWithExploration(500000, 1000, 'X', 200);
        aiPlayer7.getNeuralNetworkPlayer().trainSelfPlayWithExploration(500000, 1000, 'X', 250);
        aiPlayer8.getNeuralNetworkPlayer().trainSelfPlayWithExploration(500000, 1000, 'X', 500);
        aiPlayer9.getNeuralNetworkPlayer().trainSelfPlayWithExploration(500000, 1000, 'X', 1000);

        int numGames= 10000;
        for (int i = 0; i < numGames; i++) {
            board.clearBoard();
            board.startAiPlayRandomNonVisualised(aiPlayer1, 0.01, true);
            //board.startOnlyAiGame(aiPlayer1, aiPlayer2, 0.1);
        }
        board.showWins(numGames);

        Board board1 = new Board();
        for (int i = 0; i < numGames; i++) {
            board1.clearBoard();
            board1.startAiPlayRandomNonVisualised(aiPlayer2, 0.01, true);
        }
        board1.showWins(numGames);

        Board board2 = new Board();
        for (int i = 0; i < numGames; i++) {
            board2.clearBoard();
            board2.startAiPlayRandomNonVisualised(aiPlayer3, 0.01, true);
        }
        board2.showWins(numGames);

        Board board3 = new Board();
        for (int i = 0; i < numGames; i++) {
            board3.clearBoard();
            board3.startAiPlayRandomNonVisualised(aiPlayer4, 0.01, true);
        }
        board3.showWins(numGames);

        Board board4 = new Board();
        for (int i = 0; i < numGames; i++) {
            board4.clearBoard();
            board4.startAiPlayRandomNonVisualised(aiPlayer5, 0.01, true);
        }
        board4.showWins(numGames);

        Board board5 = new Board();
        for (int i = 0; i < numGames; i++) {
            board5.clearBoard();
            board5.startAiPlayRandomNonVisualised(aiPlayer6, 0.01, true);
        }
        board5.showWins(numGames);

        Board board6 = new Board();
        for (int i = 0; i < numGames; i++) {
            board6.clearBoard();
            board6.startAiPlayRandomNonVisualised(aiPlayer7, 0.01, true);
        }
        board6.showWins(numGames);

        Board board7 = new Board();
        for (int i = 0; i < numGames; i++) {
            board7.clearBoard();
            board7.startAiPlayRandomNonVisualised(aiPlayer8, 0.01, true);
        }
        board7.showWins(numGames);

        Board board8 = new Board();
        for (int i = 0; i < numGames; i++) {
            board8.clearBoard();
            board8.startAiPlayRandomNonVisualised(aiPlayer9, 0.01, true);
        }
        board8.showWins(numGames);

        Board board9 = new Board();
        for (int i = 0; i < numGames; i++) {
            board9.clearBoard();
            board9.startAiPlayRandomNonVisualised(aiPlayer0, 0.01, true);
        }
        board9.showWins(numGames);
    }
}