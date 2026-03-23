import java.util.List;

public interface SlotAssignmentStrategy {
    ParkingSlot assignSlot(List<ParkingSlot> candidates, Gate entryGate);
}
