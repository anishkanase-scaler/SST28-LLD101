import java.util.List;

public interface ElevatorScheduler {
    ElevatorCar selectElevator(List<ElevatorCar> elevators, ExternalRequest request);
}
