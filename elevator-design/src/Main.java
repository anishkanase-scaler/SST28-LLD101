public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== ELEVATOR SYSTEM DEMO ==========\n");

        Building building = new Building(10, 2, new ShortestPathScheduler());
        ElevatorController ctrl = building.getController();

        ctrl.printStatus();

        // --- Scenario 1: External call from floor 3 going UP ---
        System.out.println("=== Scenario 1: User on floor 3 calls elevator UP ===");
        building.getFloorPanel(3).pressUp();
        ctrl.printStatus();

        // Simulate elevator moving to floor 3
        for (int i = 0; i < 3; i++) ctrl.stepAll();
        ctrl.printStatus();

        // --- Scenario 2: User inside E1 selects floor 7 ---
        System.out.println("=== Scenario 2: User boards E1, selects floor 7 ===");
        building.getCarPanel("E1").pressFloor(7);
        ctrl.printStatus();

        for (int i = 0; i < 5; i++) ctrl.stepAll();
        ctrl.printStatus();

        // --- Scenario 3: Second user on floor 5 calls DOWN ---
        System.out.println("=== Scenario 3: User on floor 5 calls elevator DOWN ===");
        building.getFloorPanel(5).pressDown();
        ctrl.printStatus();

        for (int i = 0; i < 4; i++) ctrl.stepAll();
        ctrl.printStatus();

        // --- Scenario 4: Overweight check ---
        System.out.println("=== Scenario 4: E2 is overweight (750kg) ===");
        ctrl.getElevators().get(1).setCurrentWeight(750);
        building.getCarPanel("E2").pressFloor(9);
        ctrl.printStatus();

        // Reset weight
        ctrl.getElevators().get(1).setCurrentWeight(0);

        // --- Scenario 5: Maintenance mode ---
        System.out.println("=== Scenario 5: E1 goes under maintenance ===");
        ctrl.setMaintenance("E1");
        building.getFloorPanel(2).pressUp();
        ctrl.printStatus();

        ctrl.setOperational("E1");
        ctrl.printStatus();

        // --- Scenario 6: Alarm (emergency stop) ---
        System.out.println("=== Scenario 6: Alarm triggered inside E2 ===");
        building.getCarPanel("E2").pressFloor(8);
        for (int i = 0; i < 2; i++) ctrl.stepAll();
        building.getCarPanel("E2").pressAlarm();
        ctrl.printStatus();

        // --- Scenario 7: Switch scheduler at runtime ---
        System.out.println("=== Scenario 7: Switch to FCFS scheduler ===");
        ctrl.setScheduler(new FCFSScheduler());
        building.getFloorPanel(6).pressUp();
        ctrl.printStatus();

        System.out.println("========== END ==========");
    }
}
