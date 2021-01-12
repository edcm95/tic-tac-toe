package tictactoe.ai.minimax;

import com.sun.istack.internal.NotNull;
import tictactoe.game.Board;
import tictactoe.game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

//TODO -> Make an iterative version of this
public class MinimaxNpc {

    private BoardStateNode nextMove;
    private int steps;

    public int[] getPlay(final Board board) {
        BoardStateNode origin = new BoardStateNode(Board.copyState(board.getBoard()), null);

        List<BoardStateNode> nextMoves = origin.getChildren(true);

        double value = Double.NEGATIVE_INFINITY;
        steps = 0;
        for (BoardStateNode possibility : nextMoves) {
            double result = minimax(possibility, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
            if (result > value) {
                value = result;
                nextMove = possibility;
            }
        }

        System.out.println("Nodes visited: " + steps);
        return nextMove.getPlay();
    }

    private double minimax(BoardStateNode currentNode, double a, double b, boolean maximizing) {
        steps++;
        LinkedList<BoardStateNode> childrenNodes = currentNode.getChildren(maximizing);

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
}
