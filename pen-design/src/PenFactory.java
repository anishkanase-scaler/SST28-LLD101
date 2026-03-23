public class PenFactory {

    public static Pen createPen(String type, String color, String mechanism) {
        RefillStrategy refill = new SimpleRefill();

        StartStrategy starter;
        if (mechanism.equals("with-cap")) {
            starter = new CapStart();
        } else {
            starter = new ClickStart();
        }

        if (type.equals("ink-pen")) {
            return new InkPen(color, refill, starter);
        } else if (type.equals("ball-pen")) {
            return new BallPen(color, refill, starter);
        }

        throw new IllegalArgumentException("Unrecognized pen type: " + type);
    }
}
