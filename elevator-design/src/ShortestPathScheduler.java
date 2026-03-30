import java.util.List;

public class ShortestPathScheduler implements ElevatorScheduler {

    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, ExternalRequest request) {
        ElevatorCar best = null;
        int minDistance = Integer.MAX_VALUE;

        for (ElevatorCar car : elevators) {
            if (!car.isAvailable()) continue;

            int distance = computeEffectiveDistance(car, request);
            if (distance < minDistance) {
                minDistance = distance;
                best = car;
            }
        }
        return best;
    }

    private int computeEffectiveDistance(ElevatorCar car, ExternalRequest request) {
        int floorDiff = Math.abs(car.getCurrentFloor() - request.getFloor());

        if (car.getState() == ElevatorState.IDLE) {
            return floorDiff;
        }

        boolean movingToward =
                (car.getState() == ElevatorState.MOVING_UP && request.getFloor() > car.getCurrentFloor())
                || (car.getState() == ElevatorState.MOVING_DOWN && request.getFloor() < car.getCurrentFloor());

        return movingToward ? floorDiff : floorDiff + 10;
    }
}
