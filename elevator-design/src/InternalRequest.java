public class InternalRequest {

    private final String elevatorId;
    private final int destinationFloor;

    public InternalRequest(String elevatorId, int destinationFloor) {
        this.elevatorId = elevatorId;
        this.destinationFloor = destinationFloor;
    }

    public String getElevatorId() { return elevatorId; }
    public int getDestinationFloor() { return destinationFloor; }

    @Override
    public String toString() {
        return "InternalRequest[elevator=" + elevatorId + ", dest=" + destinationFloor + "]";
    }
}
