import java.util.*;

public class BrowseService {
    private final DataStore store;

    public BrowseService(DataStore store) {
        this.store = store;
    }

    public List<Cinema> showCinemas(String city) {
        return store.getCinemasByCity(city);
    }

    public List<Film> showFilms(String city) {
        return store.getFilmsByCity(city);
    }

    public Map<Cinema, List<Showtime>> showCinemasForFilm(String city, String filmId) {
        List<Showtime> cityShows = store.getShowtimesByFilmInCity(filmId, city);
        Map<Cinema, List<Showtime>> result = new LinkedHashMap<>();
        for (Showtime s : cityShows) {
            Cinema c = store.getCinema(s.getCinemaId());
            if (c != null) result.computeIfAbsent(c, k -> new ArrayList<>()).add(s);
        }
        return result;
    }

    public Map<Film, List<Showtime>> showFilmsAtCinema(String cinemaId) {
        Map<Film, List<Showtime>> result = new LinkedHashMap<>();
        for (Showtime s : store.getShowtimesByCinema(cinemaId)) {
            Film f = store.getFilm(s.getFilmId());
            if (f != null) result.computeIfAbsent(f, k -> new ArrayList<>()).add(s);
        }
        return result;
    }

    public Map<String, SeatStatus> showSeatMap(String showtimeId) {
        Showtime show = store.getShowtime(showtimeId);
        if (show == null) throw new IllegalArgumentException("Showtime not found: " + showtimeId);
        return show.getSeatMapSnapshot();
    }
}
