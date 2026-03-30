import java.time.LocalDateTime;
import java.util.*;

public class SetupService {
    private final DataStore store;
    private final FareCalculator fareCalc;

    public SetupService(DataStore store, FareCalculator fareCalc) {
        this.store = store;
        this.fareCalc = fareCalc;
    }

    public Film addFilm(String title, String language, int durationMins) {
        Film film = new Film(SequenceGenerator.filmId(), title, language, durationMins);
        store.addFilm(film);
        return film;
    }

    public Cinema addCinema(String name, String city, EnumMap<SeatTier, Double> basePrices,
                            List<Hall> halls) {
        Cinema cinema = new Cinema(SequenceGenerator.cinemaId(), name, city, basePrices);
        for (Hall h : halls) cinema.addHall(h);
        store.addCinema(cinema);
        return cinema;
    }

    public Showtime addShowtime(String filmId, String cinemaId, String hallId,
                                LocalDateTime startTime,
                                double filmMultiplier, double slotMultiplier) {
        Film film = store.getFilm(filmId);
        if (film == null) throw new IllegalArgumentException("Film not found: " + filmId);
        Cinema cinema = store.getCinema(cinemaId);
        if (cinema == null) throw new IllegalArgumentException("Cinema not found: " + cinemaId);
        Hall hall = cinema.findHall(hallId);
        if (hall == null) throw new IllegalArgumentException("Hall not found: " + hallId);

        Showtime show = new Showtime(SequenceGenerator.showtimeId(), filmId, cinemaId, hallId,
                startTime, filmMultiplier, slotMultiplier);
        show.initialiseSeatMap(hall.getSeats());
        store.addShowtime(show);
        return show;
    }

    public void setRuntimeFareRules(List<FareRule> rules) {
        fareCalc.setActiveRules(rules);
    }
}
