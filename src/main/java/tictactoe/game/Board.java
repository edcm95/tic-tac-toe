package tictactoe.game;

import java.util.Arrays;

public class Board {

    public static final int boardSize = 3;
    private final int[][] board;

    public Board() {
        this.board = new int[boardSize][boardSize]; // [y][x]
    }

    public void checkBoard(int x, int y, int value) {
        board[y][x] = value;
    }

    public void clear() {
        for (int[] values : board) {
            Arrays.fill(values, 0);
        }
    }

    public static int evaluateBoard(int[][] matrix) {
        int horizontalSum = 0;
        int verticalSum = 0;
        for (int y = 0; y < matrix.length; y++) { // Y
            for (int x = 0; x < matrix[y].length; x++) { // X
                horizontalSum += matrix[y][x];
                verticalSum += matrix[x][y]; //inverted

                if (horizontalSum == (boardSize * Game.playerValue) || verticalSum == (boardSize * Game.playerValue)) {
                    return Game.playerValue;
                }

                if (horizontalSum == (boardSize * Game.npcValue) || verticalSum == (boardSize * Game.npcValue)) {
                    return Game.npcValue;
                }
            }
        }

        int x = 0;
        int x2 = 2;
        int y = 0;
        int diagSumLeft = 0;
        int diagSumRight = 0;
        while (x <= 2) {
            diagSumLeft += matrix[y][x];
            diagSumRight += matrix[y][x2];

            x++;
            x2--;
            y++;
        }

        if (diagSumLeft == (boardSize * Game.playerValue) || diagSumRight == (boardSize * Game.playerValue)) {
            return Game.playerValue;

        }

        if (diagSumLeft == (boardSize * Game.npcValue) || diagSumRight == (boardSize * Game.npcValue)) {
            return Game.npcValue;
        }
        return 0;
    }

    public boolean validatePlay(int x, int y) {
        if (x > 2 || x < 0) {
            return false;
        }

        if (y > 2 || y < 0) {
            return false;
        }

        int value = board[y][x];
        return (value == 0);
    }

    public int[][] getBoard() {
        int[][] copy = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    public int[] getHorizontalLine(int y) {
        return board[y];
    }

    public int[] getVerticalLine(int x) {
        int[] line = new int[boardSize];
        for (int i = 0; i < boardSize; i++) {
            line[i] = board[i][x];
        }
        return line;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] ints : board) {
            for (int x = 0; x < ints.length; x++) {
                sb.append("|").append(mapSymbol(ints[x]));
            }
            sb.append("|\n");
        }
        return sb.toString();
    }

    private String mapSymbol(int value) {
        switch (value) {
            case 0:
                return "-";
            case Game.npcValue:
                return "X";
            case Game.playerValue:
                return "O";
            default:
                return "E";
        }
    }

    public boolean isFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
