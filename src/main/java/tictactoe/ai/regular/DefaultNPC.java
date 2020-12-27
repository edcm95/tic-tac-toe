package tictactoe.ai.regular;

import tictactoe.game.Board;

public class DefaultNPC {

    private final Board board;

    private final int threat = 2;
    private final int matrixSize = 3;

    /**
     * Implemented in rush and with the objective of
     * fulfilling very basic gameplay decisions without
     * using any <code>java.util.Collection</code>
     */
    public DefaultNPC(Board board) {
        this.board = board;
    }

    public int[] getPlay() {
        //TODO: Implement diagonal plays
        int[] horizontalWeights = getHorizontalLineWeights();
        int[] verticalWeights = getVerticalLineWeights();

        boolean isWinnable = isWin(verticalWeights, horizontalWeights);

        // if defense
        if (evaluateIfDefensiveMoveIsRequired(verticalWeights) && !isWinnable) {
            // get what line needs defending
            int threatenedLineIndex = getIndexOfThreatenedLine(verticalWeights); // this is the X

            // find free spot on said line
            int y = findFreeSpotOnLine(board.getVerticalLine(threatenedLineIndex));
            return new int[]{threatenedLineIndex, y};
        }

        if (evaluateIfDefensiveMoveIsRequired(horizontalWeights) && !isWinnable) {
            // get what line needs defending
            int threatenedLineIndex = getIndexOfThreatenedLine(horizontalWeights); // this is the Y

            // find free spot on said line
            int x = findFreeSpotOnLine(board.getHorizontalLine(threatenedLineIndex));
            return new int[]{x, threatenedLineIndex};
        }

        // find the most promissing index to play by value
        int[] bestHorizontalIndexesOrdered = new int[3];
        int[] bestVerticalIndexesOrdered = new int[3];
        int runningIndexHor = matrixSize - 1;
        int runningIndexVert = matrixSize - 1;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (horizontalWeights[i] < horizontalWeights[j]) {
                    bestHorizontalIndexesOrdered[runningIndexHor] = i;
                    runningIndexHor--;
                }

                if (verticalWeights[i] < verticalWeights[j]) {
                    bestVerticalIndexesOrdered[runningIndexVert] = i;
                    runningIndexVert--;
                }
            }
        }


        int i = 0;
        while (i < matrixSize) {
            //decide what best play is horizontal or vertical
            if (horizontalWeights[bestHorizontalIndexesOrdered[i]] < verticalWeights[bestVerticalIndexesOrdered[i]]) {
                //play horizontal
                int y = bestHorizontalIndexesOrdered[i];
                int x = findFreeSpotOnLine(board.getBoard()[y]);

                //if not good -> continue
                if (!board.validatePlay(x, y)) {
                    i++;
                    continue;
                }

                return new int[]{x, y};
            }

            //play vertical
            int x = bestVerticalIndexesOrdered[i];
            int y = findFreeSpotOnLine(board.getVerticalLine(x));

            if (!board.validatePlay(x, y)) {
                i++;
                continue;
            }
            // if not good, continue
            return new int[]{x, y};
        }

        return null;
    }

    private boolean isWin(int[] vertWeights, int[] horWeights) {
        for (int i = 0; i < matrixSize; i++) {
            int val1 = vertWeights[i];
            int val2 = horWeights[i];

            if (val1 == -2 && findFreeSpotOnLine(board.getVerticalLine(i)) != -1) {
                return true;
            }

            if (val2 == -2 && findFreeSpotOnLine(board.getHorizontalLine(i)) != -1) {
                return true;
            }
        }
        return false;
    }


    private int findFreeSpotOnLine(int[] line) {
        for (int i = 0; i < matrixSize; i++) {
            if (line[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    private boolean evaluateIfDefensiveMoveIsRequired(int[] lineWeights) {
        for (int i = 0; i < matrixSize; i++) {
            if (lineWeights[i] == threat) {
                return true;
            }
        }
        return false;
    }

    private int getIndexOfThreatenedLine(int[] lineWeights) {
        for (int i = 0; i < matrixSize; i++) {
            if (lineWeights[i] == threat) {
                return i;
            }
        }
        return -1;
    }

    private int[] getHorizontalLineWeights() {
        int[] horizontalWeights = new int[3];

        for (int i = 0; i < matrixSize; i++) {
            int sum = 0;
            for (int j = 0; j < matrixSize; j++) {
                sum += board.getBoard()[i][j];
            }
            horizontalWeights[i] = sum;
        }

        return horizontalWeights;
    }

    private int[] getVerticalLineWeights() {
        int[] verticalWeights = new int[3];

        for (int j = 0; j < matrixSize; j++) {
            int sum = 0;
            for (int i = 0; i < matrixSize; i++) {
                sum += board.getBoard()[i][j];
            }
            verticalWeights[j] = sum;
        }

        return verticalWeights;
    }
}
