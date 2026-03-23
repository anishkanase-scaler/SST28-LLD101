import java.time.LocalDateTime;

public class ParkingTicket {

    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot allocatedSlot;
    private final LocalDateTime entryTime;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSlot allocatedSlot, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.allocatedSlot = allocatedSlot;
        this.entryTime = entryTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getAllocatedSlot() {
        return allocatedSlot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    @Override
    public String toString() {
        return "Ticket[" + ticketId + "] " + vehicle + " -> " + allocatedSlot + " at " + entryTime;
    }
}
