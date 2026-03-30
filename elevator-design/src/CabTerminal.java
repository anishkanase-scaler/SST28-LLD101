public class CabTerminal {

    private final String cabId;
    private final ElevatorSystem system;

    public CabTerminal(String cabId, ElevatorSystem system) {
        this.cabId = cabId;
        this.system = system;
    }

    public synchronized void selectFloor(int targetFloor, double mass) {
        System.out.println("[Cab " + cabId + "] Floor " + targetFloor + " selected");
        system.addFloorToCab(cabId, targetFloor);
    }

    public void openDoor() {
        System.out.println("[Cab " + cabId + "] OPEN DOORS");
    }

    public void closeDoor() {
        System.out.println("[Cab " + cabId + "] CLOSE DOORS");
    }

    public synchronized void activateHalt() {
        System.out.println("[Cab " + cabId + "] HALT ACTIVATED!");
        system.emergencyStop(cabId);
    }
}
