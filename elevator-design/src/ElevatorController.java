import java.util.List;

public class ElevatorController {

    private final List<ElevatorCar> elevators;
    private ElevatorScheduler scheduler;

    public ElevatorController(List<ElevatorCar> elevators, ElevatorScheduler scheduler) {
        this.elevators = elevators;
        this.scheduler = scheduler;
    }

    public void setScheduler(ElevatorScheduler scheduler) {
        this.scheduler = scheduler;
        System.out.println("Scheduler switched to: " + scheduler.getClass().getSimpleName());
    }

    public void handleExternalRequest(ExternalRequest request) {
        ElevatorCar selected = scheduler.selectElevator(elevators, request);
        if (selected == null) {
            System.out.println("[Controller] No elevator available for " + request);
            return;
        }
        System.out.println("[Controller] Dispatching " + selected.getId()
                + " to floor " + request.getFloor() + " (" + request.getDirection() + ")");
        selected.addDestination(request.getFloor());
    }

    public void handleInternalRequest(InternalRequest request) {
        for (ElevatorCar car : elevators) {
            if (!car.getId().equals(request.getElevatorId())) continue;

            if (!car.isAvailable()) {
                System.out.println("[Controller] " + car.getId() + " is under maintenance — request ignored");
                return;
            }
            if (car.isOverweight()) {
                System.out.println("[Controller] " + car.getId() + " is overweight (" + car.getCurrentWeight()
                        + "kg / " + car.getWeightLimit() + "kg) — request ignored");
                return;
            }
            System.out.println("[Controller] " + car.getId() + " queued floor " + request.getDestinationFloor());
            car.addDestination(request.getDestinationFloor());
            return;
        }
        System.out.println("[Controller] Elevator " + request.getElevatorId() + " not found");
    }

    public void handleAlarm(String elevatorId) {
        for (ElevatorCar car : elevators) {
            if (car.getId().equals(elevatorId)) {
                car.triggerEmergency();
                return;
            }
        }
    }

    public void setMaintenance(String elevatorId) {
        for (ElevatorCar car : elevators) {
            if (car.getId().equals(elevatorId)) {
                car.setUnderMaintenance();
                return;
            }
        }
    }

    public void setOperational(String elevatorId) {
        for (ElevatorCar car : elevators) {
            if (car.getId().equals(elevatorId)) {
                car.setOperational();
                return;
            }
        }
    }

    public void stepAll() {
        for (ElevatorCar car : elevators) {
            car.step();
        }
    }

    public void printStatus() {
        System.out.println("\n--- Elevator Status ---");
        for (ElevatorCar car : elevators) {
            System.out.println("  " + car);
        }
        System.out.println("-----------------------\n");
    }

    public List<ElevatorCar> getElevators() { return elevators; }
}
