import java.util.*;
import java.util.stream.Collectors;

public class DataStore {
    private static final DataStore INSTANCE = new DataStore();

    private final Map<String, Patron>   patrons   = new HashMap<>();
    private final Map<String, String>   emailToId = new HashMap<>();
    private final Map<String, Film>     films     = new HashMap<>();
    private final Map<String, Cinema>   cinemas   = new HashMap<>();
    private final Map<String, Showtime> showtimes = new HashMap<>();
    private final Map<String, Booking>  bookings  = new HashMap<>();
    private final Map<String, Payment>  payments  = new HashMap<>();

    private DataStore() {}

    public static DataStore getInstance() { return INSTANCE; }

    // ── Patrons ──────────────────────────────────────────────
    public synchronized void addPatron(Patron p) {
        if (emailToId.containsKey(p.getEmail()))
            throw new RuntimeException("Email already registered: " + p.getEmail());
        patrons.put(p.getId(), p);
        emailToId.put(p.getEmail(), p.getId());
    }
    public Patron getPatron(String id) { return patrons.get(id); }

    // ── Films ────────────────────────────────────────────────
    public void addFilm(Film f)        { films.put(f.getId(), f); }
    public Film getFilm(String id)     { return films.get(id); }

    // ── Cinemas ──────────────────────────────────────────────
    public void addCinema(Cinema c)    { cinemas.put(c.getId(), c); }
    public Cinema getCinema(String id) { return cinemas.get(id); }

    public List<Cinema> getCinemasByCity(String city) {
        return cinemas.values().stream()
                .filter(c -> c.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    // ── Showtimes ────────────────────────────────────────────
    public void addShowtime(Showtime s)    { showtimes.put(s.getId(), s); }
    public Showtime getShowtime(String id) { return showtimes.get(id); }

    public List<Showtime> getShowtimesByFilm(String filmId) {
        return showtimes.values().stream()
                .filter(s -> s.getFilmId().equals(filmId))
                .collect(Collectors.toList());
    }

    public List<Showtime> getShowtimesByCinema(String cinemaId) {
        return showtimes.values().stream()
                .filter(s -> s.getCinemaId().equals(cinemaId))
                .collect(Collectors.toList());
    }

    public List<Film> getFilmsByCity(String city) {
        Set<String> cityIds = getCinemasByCity(city).stream()
                .map(Cinema::getId).collect(Collectors.toSet());
        return showtimes.values().stream()
                .filter(s -> cityIds.contains(s.getCinemaId()))
                .map(s -> films.get(s.getFilmId()))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Showtime> getShowtimesByFilmInCity(String filmId, String city) {
        Set<String> cityIds = getCinemasByCity(city).stream()
                .map(Cinema::getId).collect(Collectors.toSet());
        return showtimes.values().stream()
                .filter(s -> s.getFilmId().equals(filmId) && cityIds.contains(s.getCinemaId()))
                .collect(Collectors.toList());
    }

    // ── Bookings ─────────────────────────────────────────────
    public void addBooking(Booking b)    { bookings.put(b.getId(), b); }
    public Booking getBooking(String id) { return bookings.get(id); }

    // ── Payments ─────────────────────────────────────────────
    public void addPayment(Payment p)    { payments.put(p.getId(), p); }
    public Payment getPayment(String id) { return payments.get(id); }
}
