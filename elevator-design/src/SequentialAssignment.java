import java.util.List;

public class SequentialAssignment implements AssignmentAlgorithm {

    @Override
    public ElevatorCab selectOptimalCab(PassengerCall call, List<ElevatorCab> availableCabs) {
        for (ElevatorCab cab : availableCabs) {
            if (cab.getOperatingState() != CabState.OUT_OF_SERVICE) {
                return cab;
            }
        }
        return null;
    }

    @Override
    public String getAlgorithmName() {
        return "Sequential";
    }
}
