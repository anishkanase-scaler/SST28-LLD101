import java.util.*;

public class Game {

    private final Board board;
    private final Dice dice;
    private final List<Player> players;
    private final List<Player> finishOrder;

    public Game(Board board, List<Player> players) {
        this.board = board;
        this.dice = new Dice(6);
        this.players = players;
        this.finishOrder = new ArrayList<>();
    }

    public void start() {
        printBoardSetup();

        int remaining = players.size();

        while (remaining > 1) {
            for (Player p : players) {
                if (p.hasWon()) continue;

                int rolled = dice.roll();
                int curPos = p.getPosition();
                int nextPos = curPos + rolled;

                if (nextPos > board.getTotalCells()) {
                    System.out.println(p.getName() + " rolled " + rolled
                            + " at " + curPos + " — cannot move, exceeds board");
                    continue;
                }

                int finalPos = board.resolvePosition(nextPos);
                p.setPosition(finalPos);

                if (board.hasSnakeAt(nextPos)) {
                    System.out.println(p.getName() + " rolled " + rolled
                            + " — " + curPos + " -> " + nextPos
                            + " — SNAKE BITE! slides down to " + finalPos);
                } else if (board.hasLadderAt(nextPos)) {
                    System.out.println(p.getName() + " rolled " + rolled
                            + " — " + curPos + " -> " + nextPos
                            + " — LADDER! climbs up to " + finalPos);
                } else {
                    System.out.println(p.getName() + " rolled " + rolled
                            + " — " + curPos + " -> " + finalPos);
                }

                if (finalPos == board.getTotalCells()) {
                    p.markWon();
                    finishOrder.add(p);
                    remaining--;
                    System.out.println(">>> " + p.getName() + " WINS! (Rank #" + finishOrder.size() + ") <<<");

                    if (remaining <= 1) break;
                }
            }
        }

        printResults();
    }

    private void printBoardSetup() {
        System.out.println("========================================");
        System.out.println("       SNAKE AND LADDER GAME");
        System.out.println("========================================");
        System.out.println("Board: " + board.getDimension() + "x" + board.getDimension()
                + " (" + board.getTotalCells() + " cells)");
        System.out.println("Players: " + players.size());
        System.out.println();
        System.out.println("Snakes:");
        for (Snake s : board.getSnakes()) {
            System.out.println("  " + s);
        }
        System.out.println("Ladders:");
        for (Ladder l : board.getLadders()) {
            System.out.println("  " + l);
        }
        System.out.println("========================================\n");
    }

    private void printResults() {
        System.out.println("\n========================================");
        System.out.println("          FINAL STANDINGS");
        System.out.println("========================================");
        for (int i = 0; i < finishOrder.size(); i++) {
            System.out.println("  #" + (i + 1) + " " + finishOrder.get(i).getName());
        }
        for (Player p : players) {
            if (!p.hasWon()) {
                System.out.println("  -- " + p.getName() + " (still at " + p.getPosition() + ")");
            }
        }
        System.out.println("========================================");
    }
}
