import java.time.LocalDateTime;
import java.util.*;

public class Booking {
    private final String id;
    private final String patronId;
    private final String showtimeId;
    private final List<String> seatIds;
    private String paymentId;
    private double amount;
    private PayMethod payMethod;
    private BookingStatus status;
    private final LocalDateTime bookedAt;
    private LocalDateTime cancelledAt;

    private Booking(Builder b) {
        this.id = b.id;
        this.patronId = b.patronId;
        this.showtimeId = b.showtimeId;
        this.seatIds = Collections.unmodifiableList(new ArrayList<>(b.seatIds));
        this.paymentId = b.paymentId;
        this.amount = b.amount;
        this.payMethod = b.payMethod;
        this.status = b.status;
        this.bookedAt = b.bookedAt;
        this.cancelledAt = b.cancelledAt;
    }

    public void markCancelled() {
        this.status = BookingStatus.CANCELLED;
        this.cancelledAt = LocalDateTime.now();
    }

    public String getId()                   { return id; }
    public String getPatronId()             { return patronId; }
    public String getShowtimeId()           { return showtimeId; }
    public List<String> getSeatIds()        { return seatIds; }
    public String getPaymentId()            { return paymentId; }
    public double getAmount()               { return amount; }
    public PayMethod getPayMethod()         { return payMethod; }
    public BookingStatus getStatus()        { return status; }
    public LocalDateTime getBookedAt()      { return bookedAt; }
    public LocalDateTime getCancelledAt()   { return cancelledAt; }

    @Override
    public String toString() {
        return "Booking " + id + " [" + status + "] seats=" + seatIds + " Rs." + String.format("%.2f", amount);
    }

    public static class Builder {
        private String id;
        private String patronId;
        private String showtimeId;
        private List<String> seatIds = new ArrayList<>();
        private String paymentId;
        private double amount;
        private PayMethod payMethod;
        private BookingStatus status = BookingStatus.CONFIRMED;
        private LocalDateTime bookedAt = LocalDateTime.now();
        private LocalDateTime cancelledAt;

        public Builder id(String id)                    { this.id = id; return this; }
        public Builder patronId(String patronId)        { this.patronId = patronId; return this; }
        public Builder showtimeId(String showtimeId)    { this.showtimeId = showtimeId; return this; }
        public Builder seatIds(List<String> seatIds)    { this.seatIds = seatIds; return this; }
        public Builder paymentId(String paymentId)      { this.paymentId = paymentId; return this; }
        public Builder amount(double amount)             { this.amount = amount; return this; }
        public Builder payMethod(PayMethod payMethod)   { this.payMethod = payMethod; return this; }
        public Builder status(BookingStatus status)     { this.status = status; return this; }

        public Booking build() {
            Objects.requireNonNull(id);
            Objects.requireNonNull(patronId);
            Objects.requireNonNull(showtimeId);
            return new Booking(this);
        }
    }
}
