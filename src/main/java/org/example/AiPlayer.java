package org.example;

public class AiPlayer {
    public AiPlayer() {}

    public Board makeMove(Board board, char player) {
        int row = (int) (Math.random() * 3);
        int col = (int) (Math.random() * 3);
        if (board.isValidMove(board.getBoard(), row, col)) {
            board.board[row][col] = player;
        } else {
            makeMove(board, player);
        }
        return board;
    }
}