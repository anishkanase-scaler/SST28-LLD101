import java.util.List;

public class NearestSlotStrategy implements SlotAssignmentStrategy {

    @Override
    public ParkingSlot assignSlot(List<ParkingSlot> candidates, Gate entryGate) {
        ParkingSlot nearest = null;
        int minDist = Integer.MAX_VALUE;

        for (ParkingSlot slot : candidates) {
            int dist = computeDistance(slot, entryGate);
            if (dist < minDist) {
                minDist = dist;
                nearest = slot;
            }
        }
        return nearest;
    }

    private int computeDistance(ParkingSlot slot, Gate gate) {
        int floorDiff = Math.abs(slot.getFloor() - gate.getFloor());
        int posDiff = Math.abs(slot.getPosition() - gate.getPosition());
        return floorDiff * 100 + posDiff;
    }
}
