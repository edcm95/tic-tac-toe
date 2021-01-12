package tictactoe.ai.minimax;

import tictactoe.game.Board;
import tictactoe.game.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class MinimaxNpc {

    private BoardStateNode nextMove;
    private int steps;

    public int[] getPlay(final Board board) {
        BoardStateNode origin = new BoardStateNode(copyState(board.getBoard()), null);

        List<BoardStateNode> nextMoves = getChildren(origin, true);

        double value = Double.NEGATIVE_INFINITY;
        for (BoardStateNode possibility : nextMoves) {
            double result = minimax(possibility, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
            if (result > value) {
                value = result;
                nextMove = possibility;
            }
        }

        //minimax(origin, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);


        System.out.println("Minimax steps calculated: " + steps);

        return nextMove.getPlay();
    }

    private double minimax(BoardStateNode currentNode, double a, double b, boolean maximizing) {
        steps++;
        LinkedList<BoardStateNode> childrenNodes = getChildren(currentNode, maximizing);

        if (childrenNodes.isEmpty() || currentNode.getValue() != 0) {
            return currentNode.getValue();
        }

        double value;
        if (maximizing) {
            value = Double.NEGATIVE_INFINITY;
            for (BoardStateNode children : childrenNodes) {
                value = Math.max(value, minimax(children, a, b, false));
                a = Math.max(a, value);
                if (b <= a) {
                    break;
                }
            }

        } else {
            value = Double.POSITIVE_INFINITY;
            for (BoardStateNode children : childrenNodes) {
                value = Math.min(value, minimax(children, a, b, true));
                b = Math.min(value, b);
                if (b <= a) {
                    break;
                }
            }

        }
        return value;
    }

    private LinkedList<BoardStateNode> getChildren(BoardStateNode node, boolean maximizing) {
        LinkedList<BoardStateNode> possibilities = new LinkedList<>();
        int[][] currentState = node.getState();

        //process new state
        for (int y = 0; y < Board.boardSize; y++) {
            for (int x = 0; x < Board.boardSize; x++) {
                if (currentState[y][x] != 0) {
                    continue;
                }

                int[][] childState = copyState(currentState);
                childState[y][x] = (maximizing) ? Game.npcValue : Game.playerValue; //
                BoardStateNode newPossibility = new BoardStateNode(childState, new int[]{x, y}); // xy
                possibilities.add(newPossibility);
            }
        }

        return possibilities;
    }

    private int[][] copyState(int[][] state) {
        int[][] newState = new int[Board.boardSize][Board.boardSize];
        for (int i = 0; i < Board.boardSize; i++) {
            for (int j = 0; j < Board.boardSize; j++) {
                newState[i][j] = state[i][j];
            }
        }
        return newState;
    }
}
