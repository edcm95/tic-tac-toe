package tictactoe.ai.minimax;

import tictactoe.game.Board;

public class BoardStateNode {

    private final int[][] state;
    private final int[] play;
    private final double value;

    public BoardStateNode(int[][] newState, int[] play) {
        this.play = play;
        this.state = newState;
        this.value = Board.evaluateBoard(this.state);
    }

    public double getValue() {
        return value;
    }

    public int[][] getState() {
        return state;
    }

    public int[] getPlay() {
        return play;
    }
}
