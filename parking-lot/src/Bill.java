import java.time.LocalDateTime;
import java.time.Duration;

public class Bill {

    private final ParkingTicket ticket;
    private final LocalDateTime exitTime;
    private final long hoursParked;
    private final double totalAmount;

    public Bill(ParkingTicket ticket, LocalDateTime exitTime) {
        this.ticket = ticket;
        this.exitTime = exitTime;
        long rawHours = Duration.between(ticket.getEntryTime(), exitTime).toHours();
        this.hoursParked = Math.max(1, rawHours);
        this.totalAmount = hoursParked * ticket.getAllocatedSlot().getSlotType().getHourlyRate();
    }

    public ParkingTicket getTicket() {
        return ticket;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public long getHoursParked() {
        return hoursParked;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Bill: " + ticket.getVehicle()
                + " | Duration: " + hoursParked + "h"
                + " | SlotType: " + ticket.getAllocatedSlot().getSlotType()
                + " | Amount: Rs." + (int) totalAmount;
    }
}
