import java.time.LocalDateTime;
import java.util.*;

public class CinemaFacade {
    private final DataStore store;
    private final FareCalculator fareCalc;
    private final SetupService setup;
    private final BrowseService browse;
    private final ReservationHandler reservations;
    private final PaymentGateway payGateway;
    private final EventBus eventBus;

    public CinemaFacade() {
        this.store      = DataStore.getInstance();
        this.fareCalc   = new FareCalculator();
        this.payGateway = new PaymentGateway(store);
        this.eventBus   = new EventBus();
        this.setup      = new SetupService(store, fareCalc);
        this.browse     = new BrowseService(store);
        this.reservations = new ReservationHandler(store, fareCalc, payGateway, eventBus);

        Patron admin = new Patron(SequenceGenerator.patronId(), "System Admin",
                "admin@cinebook.io", PatronRole.MANAGER);
        store.addPatron(admin);
    }

    // ── Admin APIs ───────────────────────────────────────────
    public Film addFilm(String title, String language, int durationMins) {
        return setup.addFilm(title, language, durationMins);
    }

    public Cinema addCinema(String name, String city, EnumMap<SeatTier, Double> basePrices,
                            List<Hall> halls) {
        return setup.addCinema(name, city, basePrices, halls);
    }

    public Showtime addShowtime(String filmId, String cinemaId, String hallId,
                                LocalDateTime startTime, double filmMult, double slotMult) {
        return setup.addShowtime(filmId, cinemaId, hallId, startTime, filmMult, slotMult);
    }

    public void setFareRules(List<FareRule> rules) {
        setup.setRuntimeFareRules(rules);
    }

    // ── Browse APIs ──────────────────────────────────────────
    public List<Cinema> showCinemas(String city)        { return browse.showCinemas(city); }
    public List<Film> showFilms(String city)            { return browse.showFilms(city); }

    public Map<Cinema, List<Showtime>> showCinemasForFilm(String city, String filmId) {
        return browse.showCinemasForFilm(city, filmId);
    }

    public Map<Film, List<Showtime>> showFilmsAtCinema(String cinemaId) {
        return browse.showFilmsAtCinema(cinemaId);
    }

    public Map<String, SeatStatus> showSeatMap(String showtimeId) {
        return browse.showSeatMap(showtimeId);
    }

    // ── Booking APIs ─────────────────────────────────────────
    public double estimateTotal(String showtimeId, List<String> seatIds) {
        return reservations.estimateTotal(showtimeId, seatIds);
    }

    public Booking bookSeats(String patronId, String showtimeId,
                             List<String> seatIds, PayMethod payMethod) {
        return reservations.bookSeats(patronId, showtimeId, seatIds, payMethod);
    }

    public void cancelBooking(String bookingId) {
        reservations.cancelBooking(bookingId);
    }

    // ── Patron ───────────────────────────────────────────────
    public Patron registerPatron(String name, String email) {
        Patron p = new Patron(SequenceGenerator.patronId(), name, email, PatronRole.VIEWER);
        store.addPatron(p);
        return p;
    }

    // ── Observer ─────────────────────────────────────────────
    public void addBookingListener(BookingListener listener) {
        eventBus.register(listener);
    }
}
