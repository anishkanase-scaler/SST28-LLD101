import java.util.List;

public interface AssignmentAlgorithm {
    ElevatorCab selectOptimalCab(PassengerCall call, List<ElevatorCab> availableCabs);
    String getAlgorithmName();
}
