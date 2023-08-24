package org.example;

public class AiPlayer {
    private NeuralNetworkPlayer neuralNetworkPlayer;
    private char symbol;

    public AiPlayer(char symbol) {
        neuralNetworkPlayer = new NeuralNetworkPlayer();
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public Board makeMove(Board board, char player) {
        int moveIndex = neuralNetworkPlayer.makeMove(board); // Get move index from neural network
        System.out.println("Move index: " + moveIndex);
        int row = moveIndex / 3;
        int col = moveIndex % 3;

        boolean validMove = false;
        while (!validMove) {
            if (board.isValidMove(board.getBoard(), row, col)) {
                board.getBoard()[row][col] = player; // Apply the move
                validMove = true;
            } else {
                // Handle invalid move by getting a new move from the neural network
                moveIndex = neuralNetworkPlayer.makeMove(board);
                row = moveIndex / 3;
                col = moveIndex % 3;
            }
        }

        return board;
    }
}
