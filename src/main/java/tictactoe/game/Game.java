package tictactoe.game;

import tictactoe.ai.minimax.MinimaxIterativeNpc;
import tictactoe.ai.minimax.MinimaxNpc;

import java.util.Scanner;

public class Game {

    private MinimaxIterativeNpc iterativeAi;
    private MinimaxNpc recursiveAi;
    private Board board;
    private Scanner scanner;
    private boolean playersTurn;

    public static final int playerValue = -1;
    public static final int npcValue = 1;

    public Game() {
        this.board = new Board();
        this.iterativeAi = new MinimaxIterativeNpc();
        this.recursiveAi = new MinimaxNpc();
        init();
    }

    private void init() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        // check who plays first
        playersTurn = true;

        while (true) {
            System.out.println("Game starting!");
            loop();
            board.clear();
        }
    }

    private void loop() {
        while (true) {
            //Display screen
            System.out.println(board.toString());

            sleep(1000);

            // check win
            int result = Board.evaluateBoard(board.getBoard());
            if (result != 0) {
                if (result == playerValue) {
                    System.out.println("Player wins!");
                }

                if (result == npcValue) {
                    System.out.println("Computer wins.");
                }
                break;
            }

            if (board.isFull()) {
                System.out.println("Draw.");
                break;
            }

            // play
            int[] play;
            if (playersTurn) {
                play = getPlayerChoice();
                //play = iterativeAi.getPlay(board);
                board.checkBoard(play[0], play[1], playerValue);
                playersTurn = false;

            } else {
                System.out.println("Computer playing.");
                play = recursiveAi.getPlay(board);
                board.checkBoard(play[0], play[1], npcValue);
                playersTurn = true;
            }
        }
    }

    private int[] getPlayerChoice() {
        System.out.println("Enter xy: \n");
        String in = scanner.nextLine();

        if (in.length() != 2) {
            System.out.println("Wrong input.");
            return getPlayerChoice();
        }

        try {
            int x = Integer.parseInt(in.charAt(0) + "");
            int y = Integer.parseInt(in.charAt(1) + "");

            if (!board.validatePlay(x, y)) {
                System.out.println("Invalid choice.");
                return getPlayerChoice();
            }

            return new int[]{x, y};

        } catch (Exception e) {
            System.out.println("Error parsing input: " + e.getMessage());
            return getPlayerChoice();
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
