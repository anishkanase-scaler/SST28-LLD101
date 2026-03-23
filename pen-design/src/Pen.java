public abstract class Pen {

    protected String inkColor;

    protected RefillStrategy refillBehavior;
    protected StartStrategy startBehavior;

    protected boolean activated = false;

    public Pen(String inkColor, RefillStrategy refillBehavior, StartStrategy startBehavior) {
        this.inkColor = inkColor;
        this.refillBehavior = refillBehavior;
        this.startBehavior = startBehavior;
    }

    public abstract void write(String content);

    public void refill(String color) {
        refillBehavior.refill(this, color);
    }

    public void open() {
        startBehavior.start(this);
        activated = true;
    }

    public void shut() {
        activated = false;
    }
}
