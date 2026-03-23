public class SimpleRefill implements RefillStrategy {

    public void refill(Pen pen, String color) {
        pen.inkColor = color;
        System.out.println("Pen refilled with " + color + " ink");
    }
}
