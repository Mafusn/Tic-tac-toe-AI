package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        Board board = new Board();

        // Random mod random = 59% winrate for startende spiller
        // Træningsinterval på 1000 er bedst
        // 64 Hidden nodes har virket bedst indtil videre
        // 64 batch size har givet bedste resultater
        // TieReward gør ikke den store forskel

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
        AiPlayer aiPlayer10 = new AiPlayer(10); //

        board.startPlayerVsAi(aiPlayer0, 0.0001);
        // board.startAiVsPlayer(aiPlayer0, 0.0001);
    }
}