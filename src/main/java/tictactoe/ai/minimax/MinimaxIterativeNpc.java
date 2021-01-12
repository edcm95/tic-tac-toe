package tictactoe.ai.minimax;

import sun.awt.image.ImageWatched;
import tictactoe.game.Board;

import java.util.LinkedList;
import java.util.List;

public class MinimaxIterativeNpc {

    private BoardStateNode nextMove;
    private int steps;
    //TODO -> Finish this later, NOTE to self: "Do not break things that are working, you're tired go relax."
    public int[] getPlay(final Board board) {
        final boolean maximizing = false;
        BoardStateNode origin = new BoardStateNode(Board.copyState(board.getBoard()), null);

        List<BoardStateNode> nextMoves = origin.getChildren(!maximizing);

        double value = Double.NEGATIVE_INFINITY;
        steps = 0;
        for (BoardStateNode possibility : nextMoves) {
            double result = getResult(possibility, maximizing);
            if (result > value) {
                value = result;
                nextMove = possibility;
            }
        }

        //System.out.println("Nodes visited: " + steps);
        return nextMove.getPlay();
    }

    private double getResult(BoardStateNode currentNode, boolean maximizing) {

        double value = 0.0;
        double a = Double.NEGATIVE_INFINITY;
        double b = Double.POSITIVE_INFINITY;
        LinkedList<BoardStateNode> stack = new LinkedList<>();
        stack.push(currentNode);

        while (!currentNode.getChildren(maximizing).isEmpty() || currentNode.getValue() == 0) {
            currentNode = stack.pop();
            LinkedList<BoardStateNode> children = currentNode.getChildren(maximizing);
            if (maximizing) {
                double innerValue = Double.NEGATIVE_INFINITY;
                for (BoardStateNode child : children) {
                    double childScore = child.getValue();
                    if (childScore > innerValue) {
                        innerValue = childScore;
                        stack.push(child);
                    }

                    b = Math.max(value, b);
                    if (b <= a) {
                        //break;
                    }
                }

                value = innerValue;
                maximizing = false;
                continue;
            }

            double innerValue = Double.POSITIVE_INFINITY;
            for (BoardStateNode child : children) {
                double childScore = child.getValue();
                if (childScore < innerValue) {
                    innerValue = childScore;
                    stack.push(child);
                }

                b = Math.min(value, b);
                if (a <= b) {
                    //break;
                }
            }

            value = innerValue;
            maximizing = true;
        }

        return value;
    }
}
