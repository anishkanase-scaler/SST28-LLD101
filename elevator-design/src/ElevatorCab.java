import java.util.*;

public class ElevatorCab {

    private final String cabId;
    private CabState operatingState;
    private int presentFloor;
    private double loadMass;
    private static final double LOAD_CAPACITY = 700.0;
    private final Set<Integer> targetFloors;

    public ElevatorCab(String cabId, int startFloor) {
        this.cabId = cabId;
        this.operatingState = CabState.STATIONARY;
        this.presentFloor = startFloor;
        this.loadMass = 0;
        this.targetFloors = new HashSet<>();
    }

    public synchronized void enqueueFloor(int floorNum) {
        if (operatingState != CabState.OUT_OF_SERVICE && floorNum != presentFloor) {
            targetFloors.add(floorNum);
        }
    }

    public synchronized void moveOneLevel() {
        if (targetFloors.isEmpty() || operatingState == CabState.OUT_OF_SERVICE) {
            operatingState = CabState.STATIONARY;
            return;
        }

        int nextTarget = getNextFloor();
        if (nextTarget > presentFloor) {
            operatingState = CabState.ASCENDING;
            presentFloor++;
        } else if (nextTarget < presentFloor) {
            operatingState = CabState.DESCENDING;
            presentFloor--;
        }

        if (targetFloors.contains(presentFloor)) {
            targetFloors.remove(presentFloor);
            System.out.println("[" + cabId + "] Arrived: Floor " + presentFloor);
        }
    }

    private int getNextFloor() {
        return targetFloors.stream()
                .min(Comparator.comparingInt(f -> Math.abs(f - presentFloor)))
                .orElse(presentFloor);
    }

    public synchronized void activateEmergency() {
        System.out.println("*** EMERGENCY ACTIVATED IN " + cabId + " AT FLOOR " + presentFloor + " ***");
        operatingState = CabState.OUT_OF_SERVICE;
        targetFloors.clear();
    }

    public synchronized void takeOffService() {
        operatingState = CabState.OUT_OF_SERVICE;
        targetFloors.clear();
    }

    public synchronized void restoreService() {
        operatingState = CabState.STATIONARY;
    }

    public synchronized boolean canAccommodate(double additionalMass) {
        return operatingState != CabState.OUT_OF_SERVICE && (loadMass + additionalMass) <= LOAD_CAPACITY;
    }

    public synchronized void addLoad(double mass) {
        this.loadMass = Math.min(loadMass + mass, LOAD_CAPACITY);
    }

    public String getCabId() { return cabId; }
    public CabState getOperatingState() { return operatingState; }
    public int getPresentFloor() { return presentFloor; }
    public double getLoadMass() { return loadMass; }
    public double getLoadCapacity() { return LOAD_CAPACITY; }

    @Override
    public String toString() {
        return cabId + "(floor=" + presentFloor + ", state=" + operatingState
                + ", load=" + String.format("%.0f", loadMass) + "/" + (int)LOAD_CAPACITY + "kg)";
    }
}
