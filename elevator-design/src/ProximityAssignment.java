import java.util.List;

public class ProximityAssignment implements AssignmentAlgorithm {

    @Override
    public ElevatorCab selectOptimalCab(PassengerCall call, List<ElevatorCab> availableCabs) {
        ElevatorCab optimal = null;
        int closestDistance = Integer.MAX_VALUE;

        for (ElevatorCab cab : availableCabs) {
            if (cab.getOperatingState() == CabState.OUT_OF_SERVICE) continue;

            int distance = Math.abs(cab.getPresentFloor() - call.getOriginFloor());
            if (distance < closestDistance) {
                closestDistance = distance;
                optimal = cab;
            }
        }
        return optimal;
    }

    @Override
    public String getAlgorithmName() {
        return "Proximity";
    }
}
