import java.util.Random;

public class Dice {

    private final Random rng;
    private final int faces;

    public Dice(int faces) {
        this.rng = new Random();
        this.faces = faces;
    }

    public int roll() {
        return rng.nextInt(faces) + 1;
    }
}
