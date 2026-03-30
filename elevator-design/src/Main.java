public class Main {

    public static void main(String[] args) {
        System.out.println("====== LIFT CONTROL SIMULATION ======\n");

        ElevatorSystem system = new ElevatorSystem(new ProximityAssignment());

        ElevatorCab cab1 = new ElevatorCab("Lift-A", 1);
        ElevatorCab cab2 = new ElevatorCab("Lift-B", 1);
        system.registerCab(cab1);
        system.registerCab(cab2);

        for (int lvl = 1; lvl <= 10; lvl++) {
            system.registerFloor(new FloorTerminal(lvl, system));
        }

        system.displayStatus();

        System.out.println("--- Call from Floor 3 (Ascending) ---");
        system.getFloorTerminal(3).activateButton(ControlButton.ASCEND, 75.0);
        system.displayStatus();

        for (int i = 0; i < 3; i++) system.advanceAllCabs();
        system.displayStatus();

        System.out.println("--- Passenger in Lift-A Selects Floor 7 ---");
        system.getCabTerminal("Lift-A").selectFloor(7, 75.0);

        for (int i = 0; i < 5; i++) system.advanceAllCabs();
        system.displayStatus();

        System.out.println("--- Call from Floor 5 (Descending) ---");
        system.getFloorTerminal(5).activateButton(ControlButton.DESCEND, 85.0);

        for (int i = 0; i < 4; i++) system.advanceAllCabs();
        system.displayStatus();

        System.out.println("--- Overload Prevention Test ---");
        system.getFloorTerminal(4).activateButton(ControlButton.ASCEND, 800.0);
        system.displayStatus();

        System.out.println("--- Service Suspension ---");
        cab1.takeOffService();
        system.getFloorTerminal(2).activateButton(ControlButton.ASCEND, 60.0);
        system.displayStatus();

        System.out.println("--- Service Restoration ---");
        cab1.restoreService();
        system.displayStatus();

        System.out.println("--- Emergency Situation ---");
        system.getCabTerminal("Lift-B").selectFloor(8, 50.0);
        for (int i = 0; i < 2; i++) system.advanceAllCabs();
        system.getCabTerminal("Lift-B").activateHalt();
        system.displayStatus();

        System.out.println("--- Switch Assignment Algorithm ---");
        system.setAssignmentAlgorithm(new SequentialAssignment());
        system.getFloorTerminal(6).activateButton(ControlButton.ASCEND, 65.0);
        system.displayStatus();

        System.out.println("====== SIMULATION END ======");
    }
}
