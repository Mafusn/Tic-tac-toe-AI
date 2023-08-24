package org.example;

public class AiPlayer {
    private NeuralNetworkPlayer neuralNetworkPlayer;

    public AiPlayer() {
        neuralNetworkPlayer = new NeuralNetworkPlayer();
    }

    public Board makeMove(Board board, char player) {
        int moveIndex = neuralNetworkPlayer.makeMove(board);
        int row = moveIndex / 3;
        int col = moveIndex % 3;

        if (board.isValidMove(board.getBoard(), row, col)) {
            board.board[row][col] = player;
        } else {
            makeMove(board, player);
        }
        return board;
    }
}
