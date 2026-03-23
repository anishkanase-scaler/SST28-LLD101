public class GripDecorator extends PenDecorator {

    public GripDecorator(Pen wrappedPen) {
        super(wrappedPen);
    }

    @Override
    public void open() {
        wrappedPen.open();
    }

    @Override
    public void write(String content) {
        System.out.println("Enhanced grip enabled...");
        wrappedPen.write(content);
    }
}
