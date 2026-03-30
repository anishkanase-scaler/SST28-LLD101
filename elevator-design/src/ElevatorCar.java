import java.util.*;

public class ElevatorCar {

    private final String id;
    private ElevatorState state;
    private int currentFloor;
    private double currentWeight;
    private final double weightLimit;
    private final List<Integer> destinationQueue;
    private InternalPanel internalPanel;

    public ElevatorCar(String id, int initialFloor) {
        this.id = id;
        this.state = ElevatorState.IDLE;
        this.currentFloor = initialFloor;
        this.currentWeight = 0;
        this.weightLimit = 700;
        this.destinationQueue = new ArrayList<>();
    }

    public boolean isAvailable() {
        return state != ElevatorState.MAINTENANCE;
    }

    public boolean isOverweight() {
        return currentWeight >= weightLimit;
    }

    public void addDestination(int floor) {
        if (!destinationQueue.contains(floor) && floor != currentFloor) {
            destinationQueue.add(floor);
        }
    }

    public void step() {
        if (destinationQueue.isEmpty() || state == ElevatorState.MAINTENANCE) return;

        int target = resolveNextTarget();

        if (target > currentFloor) {
            state = ElevatorState.MOVING_UP;
            currentFloor++;
        } else if (target < currentFloor) {
            state = ElevatorState.MOVING_DOWN;
            currentFloor--;
        }

        if (destinationQueue.contains(currentFloor)) {
            destinationQueue.remove(Integer.valueOf(currentFloor));
            System.out.println("[" + id + "] Arrived at floor " + currentFloor);
            if (destinationQueue.isEmpty()) {
                state = ElevatorState.IDLE;
                System.out.println("[" + id + "] Now IDLE at floor " + currentFloor);
            }
        }
    }

    private int resolveNextTarget() {
        if (state == ElevatorState.MOVING_UP) {
            Optional<Integer> above = destinationQueue.stream()
                    .filter(f -> f >= currentFloor)
                    .min(Integer::compareTo);
            if (above.isPresent()) return above.get();
        }
        if (state == ElevatorState.MOVING_DOWN) {
            Optional<Integer> below = destinationQueue.stream()
                    .filter(f -> f <= currentFloor)
                    .max(Integer::compareTo);
            if (below.isPresent()) return below.get();
        }
        return destinationQueue.stream()
                .min(Comparator.comparingInt(f -> Math.abs(f - currentFloor)))
                .orElse(currentFloor);
    }

    public void triggerEmergency() {
        System.out.println("!!! EMERGENCY STOP — Elevator " + id + " halted at floor " + currentFloor + " !!!");
        state = ElevatorState.MAINTENANCE;
        destinationQueue.clear();
    }

    public void setUnderMaintenance() {
        state = ElevatorState.MAINTENANCE;
        destinationQueue.clear();
        System.out.println("[" + id + "] Set to MAINTENANCE");
    }

    public void setOperational() {
        if (state == ElevatorState.MAINTENANCE) {
            state = ElevatorState.IDLE;
            System.out.println("[" + id + "] Back OPERATIONAL");
        }
    }

    public void setInternalPanel(InternalPanel panel) {
        this.internalPanel = panel;
    }

    public void setCurrentWeight(double weight) {
        this.currentWeight = weight;
    }

    public String getId() { return id; }
    public ElevatorState getState() { return state; }
    public int getCurrentFloor() { return currentFloor; }
    public double getCurrentWeight() { return currentWeight; }
    public double getWeightLimit() { return weightLimit; }
    public InternalPanel getInternalPanel() { return internalPanel; }
    public List<Integer> getDestinationQueue() { return destinationQueue; }

    @Override
    public String toString() {
        return "Elevator[" + id + " | floor=" + currentFloor + " | state=" + state
                + " | weight=" + currentWeight + "kg | queue=" + destinationQueue + "]";
    }
}
