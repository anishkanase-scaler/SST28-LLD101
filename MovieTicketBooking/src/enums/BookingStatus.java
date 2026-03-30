package enums;

public enum BookingStatus {
    PENDING,    // seats locked, payment not yet done
    CONFIRMED,  // payment successful, ticket generated
    CANCELLED,  // user cancelled; refund initiated
    EXPIRED     // payment window elapsed, seats auto-released
}
