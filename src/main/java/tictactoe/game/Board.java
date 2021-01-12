package tictactoe.game;

import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
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
        for (int y = 0; y < boardSize; y++) {
            int horizontalSum = 0;
            for (int x = 0; x < boardSize; x++) {
                horizontalSum += matrix[y][x];

                if (horizontalSum == (boardSize * Game.playerValue)) {
                    return Game.playerValue;
                }

                if (horizontalSum == (boardSize * Game.npcValue)) {
                    return Game.npcValue;
                }
            }
        }

        for (int x = 0; x < boardSize; x++) {
            int verticalSum = 0;
            for (int y = 0; y < boardSize; y++) {
                verticalSum += matrix[y][x];

                if (verticalSum == (boardSize * Game.playerValue)) {
                    return Game.playerValue;
                }

                if (verticalSum == (boardSize * Game.npcValue)) {
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

    public static int[][] copyState(int[][] state) {
        int[][] newState = new int[Board.boardSize][Board.boardSize];
        for (int i = 0; i < Board.boardSize; i++) {
            for (int j = 0; j < Board.boardSize; j++) {
                newState[i][j] = state[i][j];
            }
        }
        return newState;
    }

    private void printInHeader(String text) {
        final int height = 200;
        final int width = 600;

        //placement
        int headerX = 1;
        int headerY = 1;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        graphics2D.drawString(text, headerX, headerY);

        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int x = 0; x < width; x++) {
                boolean currentIsVoid = isVoid(image, x, y);
                String character = currentIsVoid ? " " : "#";

                if (!currentIsVoid && isVoid(image, (x + 1), y)) { // se for cheio mas à direita for vazio
                    //    character = "/";
                }

                stringBuilder.append(character);
            }

            if (stringBuilder.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(stringBuilder.toString());
        }
    }

    private boolean isVoid(@NotNull BufferedImage image, int x, int y) {
        return image.getRGB(x, y) == -16777216;
    }
}
