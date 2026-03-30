import java.time.LocalDateTime;

public class FareContext {
    private final Showtime showtime;
    private final Seat seat;
    private final double basePrice;
    private final int bookedSeatCount;
    private final int totalSeatCount;
    private final LocalDateTime now;

    public FareContext(Showtime showtime, Seat seat, double basePrice,
                      int bookedSeatCount, int totalSeatCount) {
        this.showtime = showtime;
        this.seat = seat;
        this.basePrice = basePrice;
        this.bookedSeatCount = bookedSeatCount;
        this.totalSeatCount = totalSeatCount;
        this.now = LocalDateTime.now();
    }

    public double getFillRatio() {
        return totalSeatCount == 0 ? 0.0 : (double) bookedSeatCount / totalSeatCount;
    }

    public Showtime getShowtime()   { return showtime; }
    public Seat getSeat()           { return seat; }
    public double getBasePrice()    { return basePrice; }
    public int getBookedSeatCount() { return bookedSeatCount; }
    public int getTotalSeatCount()  { return totalSeatCount; }
    public LocalDateTime getNow()   { return now; }
}
