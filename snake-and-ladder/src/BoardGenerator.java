import java.util.*;

public class BoardGenerator {

    private static final Random rng = new Random();

    public static Board generate(int dimension, DifficultyLevel difficulty) {
        int totalCells = dimension * dimension;
        int count = dimension;

        Set<Integer> reserved = new HashSet<>();
        reserved.add(1);
        reserved.add(totalCells);

        List<Snake> snakes = placeSnakes(count, totalCells, difficulty, reserved);
        List<Ladder> ladders = placeLadders(count, totalCells, difficulty, reserved);

        return new Board(dimension, snakes, ladders);
    }

    private static List<Snake> placeSnakes(int count, int totalCells, DifficultyLevel difficulty, Set<Integer> reserved) {
        List<Snake> snakes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            for (int attempt = 0; attempt < 1000; attempt++) {
                int head = rng.nextInt(totalCells - 2) + 2;

                int maxDrop;
                if (difficulty == DifficultyLevel.HARD) {
                    maxDrop = Math.max(1, totalCells / 2);
                } else {
                    maxDrop = Math.max(1, totalCells / 4);
                }
                maxDrop = Math.min(maxDrop, head - 1);

                int drop = rng.nextInt(maxDrop) + 1;
                int tail = head - drop;

                if (tail >= 1 && !reserved.contains(head) && !reserved.contains(tail)) {
                    reserved.add(head);
                    reserved.add(tail);
                    snakes.add(new Snake(head, tail));
                    break;
                }
            }
        }
        return snakes;
    }

    private static List<Ladder> placeLadders(int count, int totalCells, DifficultyLevel difficulty, Set<Integer> reserved) {
        List<Ladder> ladders = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            for (int attempt = 0; attempt < 1000; attempt++) {
                int bottom = rng.nextInt(totalCells - 2) + 2;

                int maxClimb;
                if (difficulty == DifficultyLevel.EASY) {
                    maxClimb = Math.max(1, totalCells / 2);
                } else {
                    maxClimb = Math.max(1, totalCells / 4);
                }
                maxClimb = Math.min(maxClimb, totalCells - bottom);

                if (maxClimb <= 0) continue;

                int climb = rng.nextInt(maxClimb) + 1;
                int top = bottom + climb;

                if (top <= totalCells && !reserved.contains(bottom) && !reserved.contains(top)) {
                    reserved.add(bottom);
                    reserved.add(top);
                    ladders.add(new Ladder(bottom, top));
                    break;
                }
            }
        }
        return ladders;
    }
}
