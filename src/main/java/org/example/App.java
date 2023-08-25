package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        AiPlayer aiPlayerX = new AiPlayer('X', 0);
        AiPlayer aiPlayerO = new AiPlayer('O', 1);

        aiPlayerX.getNeuralNetworkPlayer().trainSelfPlay(100000, 0);

        int numGames= 5;
        for (int i = 0; i <= numGames; i++) {
            Board board = new Board();
            board.startOnlyAiGame(aiPlayerX, aiPlayerO);
        }
    }
}
