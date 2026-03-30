public class InternalPanel {

    private final String elevatorId;
    private final ElevatorController controller;

    public InternalPanel(String elevatorId, ElevatorController controller) {
        this.elevatorId = elevatorId;
        this.controller = controller;
    }

    public void pressFloor(int floor) {
        System.out.println("[Elevator " + elevatorId + " Panel] Floor " + floor + " pressed");
        controller.handleInternalRequest(new InternalRequest(elevatorId, floor));
    }

    public void pressOpen() {
        System.out.println("[Elevator " + elevatorId + " Panel] OPEN door");
    }

    public void pressClose() {
        System.out.println("[Elevator " + elevatorId + " Panel] CLOSE door");
    }

    public void pressAlarm() {
        System.out.println("[Elevator " + elevatorId + " Panel] ALARM pressed!");
        controller.handleAlarm(elevatorId);
    }
}
