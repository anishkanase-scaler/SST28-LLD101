public abstract class PenDecorator extends Pen {

    protected Pen wrappedPen;

    public PenDecorator(Pen wrappedPen) {
        super(wrappedPen.inkColor, wrappedPen.refillBehavior, wrappedPen.startBehavior);
        this.wrappedPen = wrappedPen;
    }
}
