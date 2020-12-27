package tictactoe.ai.minimax;

import tictactoe.game.Board;
import tictactoe.game.Game;

import java.util.LinkedList;

public class MinimaxNpc {

    private int[] nextMove;
    private int steps;

    public int[] getPlay(final Board board) {
        BoardStateNode origin = new BoardStateNode(board.getBoard(), null);
        steps = 0;
        minimax(origin, 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
        System.out.println("Minimax steps calculated: " + steps);

        return nextMove;
    }

    private double minimax(BoardStateNode origin, int depth, double a, double b, boolean maximizing) {
        steps++;
        LinkedList<BoardStateNode> possibilities = getPossibilities(origin);

        if (possibilities.isEmpty()) {
            nextMove = origin.getPlay();
            return origin.getValue();
        }

        double value;
        if (maximizing) {
            value = Double.NEGATIVE_INFINITY;
            for (BoardStateNode possibility : possibilities) {
                value = Math.max(value, minimax(possibility, depth - 1, a, b, false));
                a = Math.max(a, value);
                if (a >= b) {
                    return a;
                }
            }

        } else {
            value = Double.POSITIVE_INFINITY;
            for (BoardStateNode possibility : possibilities) {
                value = Math.min(value, minimax(possibility, depth - 1, a, b, true));
                b = Math.min(value, b);
                if (b <= a) {
                    return b;
                }
            }
        }
        return value;
    }

    private LinkedList<BoardStateNode> getPossibilities(BoardStateNode currentNode) {
        LinkedList<BoardStateNode> possibilities = new LinkedList<>();
        int[][] currentState = currentNode.getState();

        //process new state
        for (int i = 0; i < Board.boardSize; i++) {
            for (int j = 0; j < Board.boardSize; j++) {
                if (currentState[i][j] != 0) {
                    continue;
                }

                int[][] newState = copyState(currentState);
                newState[i][j] = Game.npcValue; // computer play value
                BoardStateNode newPossibility = new BoardStateNode(newState, new int[]{j, i}); // xy
                possibilities.add(newPossibility);
            }
        }

        return possibilities;
    }

    private int[][] copyState(int[][] state) {
        int[][] newState = new int[Board.boardSize][Board.boardSize];
        for (int i = 0; i < Board.boardSize; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, Board.boardSize);
        }
        return newState;
    }
}
