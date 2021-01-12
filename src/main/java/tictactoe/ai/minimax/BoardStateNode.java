package tictactoe.ai.minimax;

import tictactoe.game.Board;

public class BoardStateNode {

    private final int[][] state;
    private final int[] play;
    private double score;

    public BoardStateNode(int[][] newState, int[] play) {
        this.play = play;
        this.state = newState;
    }

    public double getValue() {
        return Board.evaluateBoard(state);
    }

    public int[][] getState() {
        return state;
    }

    public int[] getPlay() {
        return play;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}
