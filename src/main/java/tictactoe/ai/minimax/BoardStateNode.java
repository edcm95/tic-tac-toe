package tictactoe.ai.minimax;

import tictactoe.game.Board;
import tictactoe.game.Game;

import java.util.LinkedList;

public class BoardStateNode {

    private final int[][] state;
    private final int[] play;

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

    public LinkedList<BoardStateNode> getChildren(boolean maximizing) {
        LinkedList<BoardStateNode> possibilities = new LinkedList<>();
        int[][] currentState = this.getState();

        //process new state
        for (int y = 0; y < Board.boardSize; y++) {
            for (int x = 0; x < Board.boardSize; x++) {
                if (currentState[y][x] != 0) {
                    continue;
                }

                int[][] childState = Board.copyState(currentState);
                childState[y][x] = (maximizing) ? Game.npcValue : Game.playerValue;
                BoardStateNode newPossibility = new BoardStateNode(childState, new int[]{x, y});
                possibilities.add(newPossibility);
            }
        }
        return possibilities;
    }
}
