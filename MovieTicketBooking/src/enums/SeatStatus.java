package enums;

public enum SeatStatus {
    AVAILABLE,  // visible and selectable by any user
    LOCKED,     // held temporarily during payment window (TTL-based)
    BOOKED      // permanently reserved after successful payment
}
