import java.util.concurrent.atomic.AtomicInteger;

public final class SequenceGenerator {
    private static final AtomicInteger PATRON_SEQ   = new AtomicInteger(0);
    private static final AtomicInteger FILM_SEQ     = new AtomicInteger(0);
    private static final AtomicInteger CINEMA_SEQ   = new AtomicInteger(0);
    private static final AtomicInteger SHOWTIME_SEQ = new AtomicInteger(0);
    private static final AtomicInteger BOOKING_SEQ  = new AtomicInteger(0);

    private SequenceGenerator() {}

    public static String patronId()     { return "PAT-" + PATRON_SEQ.incrementAndGet(); }
    public static String filmId()       { return "FLM-" + FILM_SEQ.incrementAndGet(); }
    public static String cinemaId()     { return "CIN-" + CINEMA_SEQ.incrementAndGet(); }
    public static String showtimeId()   { return "SHW-" + SHOWTIME_SEQ.incrementAndGet(); }
    public static String bookingId()    { return "BKG-" + BOOKING_SEQ.incrementAndGet(); }
}
