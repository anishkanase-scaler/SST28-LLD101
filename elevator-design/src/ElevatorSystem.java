import java.util.*;

public class ElevatorSystem {

    private final List<ElevatorCab> cabs;
    private final Map<Integer, FloorTerminal> floorTerminals;
    private final Map<String, CabTerminal> cabTerminals;
    private AssignmentAlgorithm assignmentAlgo;

    public ElevatorSystem(AssignmentAlgorithm algo) {
        this.cabs = new ArrayList<>();
        this.floorTerminals = new HashMap<>();
        this.cabTerminals = new HashMap<>();
        this.assignmentAlgo = algo;
    }

    public synchronized void registerCab(ElevatorCab cab) {
        cabs.add(cab);
        cabTerminals.put(cab.getCabId(), new CabTerminal(cab.getCabId(), this));
    }

    public synchronized void registerFloor(FloorTerminal terminal) {
        floorTerminals.put(terminal.getFloorLevel(), terminal);
    }

    public synchronized void setAssignmentAlgorithm(AssignmentAlgorithm newAlgo) {
        this.assignmentAlgo = newAlgo;
        System.out.println("Algorithm switched to: " + assignmentAlgo.getAlgorithmName());
    }

    public synchronized void processCall(PassengerCall call) {
        List<ElevatorCab> suitable = new ArrayList<>();
        for (ElevatorCab cab : cabs) {
            if (cab.canAccommodate(call.getPassengerMass())) {
                suitable.add(cab);
            }
        }

        if (suitable.isEmpty()) {
            System.out.println("[System] No suitable cab for " + call);
            return;
        }

        ElevatorCab chosen = assignmentAlgo.selectOptimalCab(call, suitable);
        if (chosen == null) {
            System.out.println("[System] Assignment failed for " + call);
            return;
        }

        System.out.println("[System] Assigned " + chosen.getCabId() + " to floor " + call.getOriginFloor());
        chosen.enqueueFloor(call.getOriginFloor());
        chosen.addLoad(call.getPassengerMass());
    }

    public synchronized void addFloorToCab(String cabId, int floorNum) {
        for (ElevatorCab cab : cabs) {
            if (cab.getCabId().equals(cabId)) {
                if (cab.getOperatingState() == CabState.OUT_OF_SERVICE) {
                    System.out.println("[System] " + cabId + " is out of service");
                    return;
                }
                cab.enqueueFloor(floorNum);
                System.out.println("[System] " + cabId + " queued floor " + floorNum);
                return;
            }
        }
    }

    public synchronized void emergencyStop(String cabId) {
        for (ElevatorCab cab : cabs) {
            if (cab.getCabId().equals(cabId)) {
                cab.activateEmergency();
                return;
            }
        }
    }

    public synchronized void advanceAllCabs() {
        for (ElevatorCab cab : cabs) {
            cab.moveOneLevel();
        }
    }

    public synchronized void displayStatus() {
        System.out.println("\n[SYSTEM STATUS]");
        for (ElevatorCab cab : cabs) {
            System.out.println("  " + cab);
        }
        System.out.println();
    }

    public List<ElevatorCab> getCabs() { return new ArrayList<>(cabs); }
    public CabTerminal getCabTerminal(String cabId) { return cabTerminals.get(cabId); }
    public FloorTerminal getFloorTerminal(int level) { return floorTerminals.get(level); }
}
