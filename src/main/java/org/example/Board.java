package org.example;

import java.util.Scanner;

public class Board {
    char[][] board;
    public Board () {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public void displayBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if ((i == 1 || i == 2) && j == 0) {
                    System.out.println();
                }
                System.out.print(board[i][j] + " ");
            }
        }
        System.out.println();
    }

    public void startGame() {
        boolean gameOver = false;
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("Here's the current board:");

        Scanner scanner = new Scanner(System.in);
        char currentPlayer = 'X';

        while(!gameOver) {
            displayBoard();

            System.out.println("Player " + currentPlayer + ", enter row (1-3): ");
            int col = scanner.nextInt() - 1;

            System.out.println("Player " + currentPlayer + ", enter column (1-3): ");
            int row = scanner.nextInt() - 1;

            if (isValidMove(board, row, col)) {
                board[row][col] = currentPlayer;

                // Checks for win or tie before assigning next player
                if(checkWin(currentPlayer)) {
                    System.out.println("Player " + currentPlayer + " wins!");
                    gameOver = true;
                } else if (checkTie()) {
                    System.out.println("It's a tie!");
                    gameOver = true;
                }

                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        scanner.close();
        displayBoard();
    }

    public void startOnlyAiGame() {
        boolean gameOver = false;
        AiPlayer aiPlayer = new AiPlayer();

        char currentPlayer = 'X';

        while(!gameOver) {
            displayBoard();

            aiPlayer.makeMove(this, currentPlayer);
            if (checkWin(currentPlayer)) {
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            } else if (checkTie()) {
                System.out.println("It's a tie!");
                break;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        displayBoard();
    }

    private boolean checkWin(char player) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == player) {
                return true;
            }
            if (board[0][row] == player && board[1][row] == player && board[2][row] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    private boolean checkTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidMove(char[][] board, int row, int col) {
        return board[row][col] == '-';
    }
}
