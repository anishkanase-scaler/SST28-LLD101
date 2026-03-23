public class Main {
    public static void main(String[] args) {
        Pen myPen = PenFactory.createPen("ink-pen", "blue", "with-cap");

        myPen.open();
        myPen.write("Design patterns make code extensible and maintainable");
        myPen.shut();

        myPen.refill("black");

        Pen anotherPen = PenFactory.createPen("ball-pen", "black", "click");
        anotherPen = new GripDecorator(anotherPen);

        anotherPen.open();
        anotherPen.write("Strategy and Decorator patterns in action");
    }
}
