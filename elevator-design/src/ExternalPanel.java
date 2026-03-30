public class ExternalPanel {

    private final int floorNumber;
    private final ElevatorController controller;

    public ExternalPanel(int floorNumber, ElevatorController controller) {
        this.floorNumber = floorNumber;
        this.controller = controller;
    }

    public void pressUp() {
        System.out.println("[Floor " + floorNumber + " Panel] UP pressed");
        controller.handleExternalRequest(new ExternalRequest(floorNumber, Direction.UP));
    }

    public void pressDown() {
        System.out.println("[Floor " + floorNumber + " Panel] DOWN pressed");
        controller.handleExternalRequest(new ExternalRequest(floorNumber, Direction.DOWN));
    }

    public int getFloorNumber() { return floorNumber; }
}
