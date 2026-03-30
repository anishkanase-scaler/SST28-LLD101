import java.util.ArrayList;
import java.util.List;

public class Building {

    private final int totalFloors;
    private final List<ElevatorCar> elevators;
    private final List<ExternalPanel> externalPanels;
    private final ElevatorController controller;

    public Building(int totalFloors, int numElevators, ElevatorScheduler scheduler) {
        this.totalFloors = totalFloors;
        this.elevators = new ArrayList<>();
        this.externalPanels = new ArrayList<>();

        for (int i = 1; i <= numElevators; i++) {
            elevators.add(new ElevatorCar("E" + i, 1));
        }

        this.controller = new ElevatorController(elevators, scheduler);

        for (ElevatorCar car : elevators) {
            car.setInternalPanel(new InternalPanel(car.getId(), controller));
        }

        for (int f = 1; f <= totalFloors; f++) {
            externalPanels.add(new ExternalPanel(f, controller));
        }
    }

    public ExternalPanel getFloorPanel(int floor) {
        return externalPanels.get(floor - 1);
    }

    public InternalPanel getCarPanel(String elevatorId) {
        for (ElevatorCar car : elevators) {
            if (car.getId().equals(elevatorId)) {
                return car.getInternalPanel();
            }
        }
        return null;
    }

    public ElevatorController getController() { return controller; }
    public int getTotalFloors() { return totalFloors; }
}
