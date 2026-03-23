public class ParkingSlot {

    private final int slotId;
    private final SlotType slotType;
    private final int floor;
    private final int position;
    private boolean occupied;

    public ParkingSlot(int slotId, SlotType slotType, int floor, int position) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.floor = floor;
        this.position = position;
        this.occupied = false;
    }

    public int getSlotId() {
        return slotId;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public int getFloor() {
        return floor;
    }

    public int getPosition() {
        return position;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void markOccupied() {
        occupied = true;
    }

    public void markFree() {
        occupied = false;
    }

    @Override
    public String toString() {
        return "Slot-" + slotId + " (" + slotType + ", Floor-" + floor + ")";
    }
}
