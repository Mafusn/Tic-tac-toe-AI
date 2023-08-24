package org.example;

public class App 
{
    public static void main( String[] args ) {
        AiPlayer aiPlayerX = new AiPlayer('X');
        AiPlayer aiPlayerO = new AiPlayer('O');

        aiPlayerX.getNeuralNetworkPlayer().trainSelfPlay(400000);

        int numGames= 5;
        for (int i = 0; i <= numGames; i++) {
            Board board = new Board();
            board.startOnlyAiGame(aiPlayerX, aiPlayerO);
        }
    }
}
