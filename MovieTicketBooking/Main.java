import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Film Booking System — Demo");
        System.out.println("========================================\n");

        CinemaFacade system = new CinemaFacade();
        system.addBookingListener(new LoggingListener());

        // ── ADMIN: Register films ────────────────────────────
        System.out.println("[ ADMIN ] Registering films...");
        Film f1 = system.addFilm("Dune: Part Two", "English", 166);
        Film f2 = system.addFilm("Stree 2", "Hindi", 135);
        System.out.println("  + " + f1);
        System.out.println("  + " + f2);

        // ── ADMIN: Register cinema with halls ────────────────
        System.out.println("\n[ ADMIN ] Registering cinema...");
        EnumMap<SeatTier, Double> prices = new EnumMap<>(SeatTier.class);
        prices.put(SeatTier.REGULAR, 150.0);
        prices.put(SeatTier.CLUB, 250.0);
        prices.put(SeatTier.PREMIUM, 400.0);
        prices.put(SeatTier.VIP, 600.0);

        Map<SeatTier, Integer> layout1 = new LinkedHashMap<>();
        layout1.put(SeatTier.REGULAR, 4);
        layout1.put(SeatTier.CLUB, 4);
        layout1.put(SeatTier.PREMIUM, 4);
        layout1.put(SeatTier.VIP, 3);

        Map<SeatTier, Integer> layout2 = new LinkedHashMap<>();
        layout2.put(SeatTier.REGULAR, 3);
        layout2.put(SeatTier.CLUB, 3);
        layout2.put(SeatTier.PREMIUM, 2);

        Hall hall1 = new Hall("H-1", "Screen 1", SeatGridFactory.createSimpleLayout(layout1));
        Hall hall2 = new Hall("H-2", "Screen 2", SeatGridFactory.createSimpleLayout(layout2));

        Cinema cinema = system.addCinema("Star Cineplex", "Mumbai", prices, List.of(hall1, hall2));
        System.out.println("  + " + cinema);
        System.out.println("  + " + hall1);
        System.out.println("  + " + hall2);

        // ── ADMIN: Schedule showtimes ────────────────────────
        System.out.println("\n[ ADMIN ] Scheduling showtimes...");
        LocalDateTime eveningSlot = LocalDateTime.now().plusDays(1).withHour(18).withMinute(30).withSecond(0).withNano(0);
        LocalDateTime nightSlot   = LocalDateTime.now().plusDays(2).withHour(21).withMinute(0).withSecond(0).withNano(0);

        Showtime s1 = system.addShowtime(f1.getId(), cinema.getId(), "H-1",
                eveningSlot, 1.2, 1.1);
        Showtime s2 = system.addShowtime(f2.getId(), cinema.getId(), "H-2",
                nightSlot, 1.0, 1.15);
        System.out.println("  + " + s1);
        System.out.println("  + " + s2);

        // ── ADMIN: Configure pricing rules ───────────────────
        System.out.println("\n[ ADMIN ] Setting fare rules: WEEKEND_20, DEMAND_80_50");
        system.setFareRules(FareRuleFactory.createRules(List.of("WEEKEND_20", "DEMAND_80_50")));

        // ── VIEWER: Browse ───────────────────────────────────
        System.out.println("\n[ VIEWER ] Browsing Mumbai...");
        List<Cinema> cinemas = system.showCinemas("Mumbai");
        System.out.println("  Cinemas: " + cinemas);

        List<Film> films = system.showFilms("Mumbai");
        System.out.println("  Films:   " + films);

        System.out.println("\n[ VIEWER ] Showtimes for '" + f1.getTitle() + "' in Mumbai:");
        system.showCinemasForFilm("Mumbai", f1.getId())
                .forEach((c, shows) -> shows.forEach(sh ->
                        System.out.println("    " + c.getName() + " — " + sh.getStartTime())));

        System.out.println("\n[ VIEWER ] All films at " + cinema.getName() + ":");
        system.showFilmsAtCinema(cinema.getId())
                .forEach((film, shows) -> System.out.println("    " + film.getTitle()
                        + " — " + shows.size() + " show(s)"));

        // ── VIEWER: Seat map ─────────────────────────────────
        System.out.println("\n[ VIEWER ] Seat map for Showtime-1:");
        system.showSeatMap(s1.getId())
                .forEach((seatId, status) -> System.out.printf("  %-5s → %s%n", seatId, status));

        // ── VIEWER: Register patrons ─────────────────────────
        Patron p1 = system.registerPatron("Arjun Mehta", "arjun@mail.com");
        Patron p2 = system.registerPatron("Priya Sharma", "priya@mail.com");

        // ── VIEWER: Estimate + Book ──────────────────────────
        List<String> chosenSeats = List.of("V1", "V2");
        System.out.println("\n[ VIEWER ] " + p1.getName() + " estimates VIP seats " + chosenSeats);
        double est = system.estimateTotal(s1.getId(), chosenSeats);
        System.out.printf("  Estimated: Rs. %.2f%n", est);

        System.out.println("\n[ VIEWER ] " + p1.getName() + " booking VIP seats...");
        Booking b1 = system.bookSeats(p1.getId(), s1.getId(), chosenSeats, PayMethod.UPI);
        System.out.println("  " + b1);

        // ── Concurrency: second patron tries same seats ──────
        System.out.println("\n[ VIEWER ] " + p2.getName() + " attempts same VIP seats...");
        try {
            system.bookSeats(p2.getId(), s1.getId(), chosenSeats, PayMethod.CARD);
        } catch (RuntimeException e) {
            System.out.println("  Blocked — " + e.getMessage());
        }

        // ── Second patron books different seats ──────────────
        List<String> otherSeats = List.of("P1", "P2", "P3");
        System.out.println("\n[ VIEWER ] " + p2.getName() + " booking PREMIUM seats " + otherSeats);
        Booking b2 = system.bookSeats(p2.getId(), s1.getId(), otherSeats, PayMethod.WALLET);
        System.out.println("  " + b2);

        // ── Cancellation ─────────────────────────────────────
        System.out.println("\n[ VIEWER ] " + p2.getName() + " cancels booking " + b2.getId());
        system.cancelBooking(b2.getId());
        System.out.println("  Cancelled. Refund to WALLET initiated.");

        // ── Final seat map ───────────────────────────────────
        System.out.println("\n[ VIEWER ] Final seat map:");
        system.showSeatMap(s1.getId())
                .forEach((seatId, status) -> System.out.printf("  %-5s → %s%n", seatId, status));

        System.out.println("\n========================================");
        System.out.println("  Demo Complete");
        System.out.println("========================================");
    }
}
