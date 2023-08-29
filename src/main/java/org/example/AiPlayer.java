package org.example;

import java.io.IOException;

public class AiPlayer {
    private NeuralNetworkPlayer neuralNetworkPlayer;

    public AiPlayer(int modelNumber) throws IOException {
        neuralNetworkPlayer = new NeuralNetworkPlayer(modelNumber);
    }

    public NeuralNetworkPlayer getNeuralNetworkPlayer() {
        return neuralNetworkPlayer;
    }

    public Board makeMove(Board board, char player, double epsilon) {
        int moveIndex = this.neuralNetworkPlayer.makeMove(board, epsilon); // Get move index from neural network
        int row = moveIndex / 3;
        int col = moveIndex % 3;

        boolean validMove = false;
        while (!validMove) {
            if (board.isValidMove(board.getBoard(), row, col)) {
                board.getBoard()[row][col] = player; // Apply the move
                validMove = true;
            } else {
                // Handle invalid move by getting a new move from the neural network
                moveIndex = this.neuralNetworkPlayer.makeMove(board, epsilon);
                row = moveIndex / 3;
                col = moveIndex % 3;
            }
        }

        return board;
    }
}
