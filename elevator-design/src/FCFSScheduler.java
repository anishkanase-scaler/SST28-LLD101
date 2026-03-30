import java.util.List;

public class FCFSScheduler implements ElevatorScheduler {

    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, ExternalRequest request) {
        for (ElevatorCar car : elevators) {
            if (car.isAvailable() && car.getState() == ElevatorState.IDLE) {
                return car;
            }
        }
        for (ElevatorCar car : elevators) {
            if (car.isAvailable()) {
                return car;
            }
        }
        return null;
    }
}
