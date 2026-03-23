import java.time.LocalDateTime;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(new NearestSlotStrategy());

        ParkingFloor floor1 = new ParkingFloor(1);
        int id = 1;
        for (int i = 1; i <= 3; i++) floor1.addSlot(new ParkingSlot(id++, SlotType.SMALL, 1, i));
        for (int i = 4; i <= 5; i++) floor1.addSlot(new ParkingSlot(id++, SlotType.MEDIUM, 1, i));
        floor1.addSlot(new ParkingSlot(id++, SlotType.LARGE, 1, 6));

        ParkingFloor floor2 = new ParkingFloor(2);
        for (int i = 1; i <= 2; i++) floor2.addSlot(new ParkingSlot(id++, SlotType.SMALL, 2, i));
        for (int i = 3; i <= 4; i++) floor2.addSlot(new ParkingSlot(id++, SlotType.MEDIUM, 2, i));
        for (int i = 5; i <= 6; i++) floor2.addSlot(new ParkingSlot(id++, SlotType.LARGE, 2, i));

        lot.addFloor(floor1);
        lot.addFloor(floor2);

        lot.addGate(new Gate("G1", 1, 1));
        lot.addGate(new Gate("G2", 1, 6));
        lot.addGate(new Gate("G3", 2, 1));

        System.out.println("=== Initial Availability ===");
        printStatus(lot.status());

        Vehicle bike = new Vehicle("KA-01-1234", VehicleType.TWO_WHEELER);
        ParkingTicket t1 = lot.park(bike, LocalDateTime.of(2026, 3, 23, 10, 0), SlotType.SMALL, "G1");
        System.out.println("\nParked: " + t1);

        Vehicle car = new Vehicle("MH-02-5678", VehicleType.CAR);
        ParkingTicket t2 = lot.park(car, LocalDateTime.of(2026, 3, 23, 10, 30), SlotType.MEDIUM, "G2");
        System.out.println("Parked: " + t2);

        Vehicle bus = new Vehicle("DL-03-9999", VehicleType.BUS);
        ParkingTicket t3 = lot.park(bus, LocalDateTime.of(2026, 3, 23, 11, 0), SlotType.LARGE, "G3");
        System.out.println("Parked: " + t3);

        System.out.println("\n=== After Parking ===");
        printStatus(lot.status());

        Bill b1 = lot.exit(t1, LocalDateTime.of(2026, 3, 23, 13, 0));
        System.out.println("\n" + b1);

        Bill b2 = lot.exit(t2, LocalDateTime.of(2026, 3, 23, 15, 30));
        System.out.println(b2);

        Bill b3 = lot.exit(t3, LocalDateTime.of(2026, 3, 23, 13, 0));
        System.out.println(b3);

        System.out.println("\n=== After All Exits ===");
        printStatus(lot.status());
    }

    private static void printStatus(Map<SlotType, Integer> availability) {
        for (Map.Entry<SlotType, Integer> entry : availability.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}
