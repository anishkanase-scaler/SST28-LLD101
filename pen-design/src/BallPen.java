// import java.util.*;
public class BallPen extends Pen {

    public BallPen(String inkColor, RefillStrategy refillBehavior, StartStrategy startBehavior) {
        super(inkColor, refillBehavior, startBehavior);
    }

    @Override
    public void write(String content) {
        if (!activated) {
            throw new RuntimeException("Pen is not activated yet!");
        }
        System.out.println("BallPen writes: " + content);
    }
}
