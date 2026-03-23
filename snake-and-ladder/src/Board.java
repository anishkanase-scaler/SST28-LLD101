import java.util.*;

public class Board {

    private final int dimension;
    private final int totalCells;
    private final List<Snake> snakes;
    private final List<Ladder> ladders;
    private final Map<Integer, Integer> transitions;

    public Board(int dimension, List<Snake> snakes, List<Ladder> ladders) {
        this.dimension = dimension;
        this.totalCells = dimension * dimension;
        this.snakes = snakes;
        this.ladders = ladders;
        this.transitions = new HashMap<>();

        for (Snake s : snakes) {
            transitions.put(s.getHead(), s.getTail());
        }
        for (Ladder l : ladders) {
            transitions.put(l.getBottom(), l.getTop());
        }
    }

    public int getTotalCells() {
        return totalCells;
    }

    public int getDimension() {
        return dimension;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }

    public int resolvePosition(int cell) {
        return transitions.getOrDefault(cell, cell);
    }

    public boolean hasSnakeAt(int cell) {
        for (Snake s : snakes) {
            if (s.getHead() == cell) return true;
        }
        return false;
    }

    public boolean hasLadderAt(int cell) {
        for (Ladder l : ladders) {
            if (l.getBottom() == cell) return true;
        }
        return false;
    }
}
