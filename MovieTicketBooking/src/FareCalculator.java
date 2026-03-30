import java.util.*;

public class FareCalculator {
    private List<FareRule> activeRules = new ArrayList<>();

    public synchronized void setActiveRules(List<FareRule> rules) {
        this.activeRules = new ArrayList<>(rules);
    }

    public synchronized List<FareRule> getActiveRules() {
        return Collections.unmodifiableList(activeRules);
    }

    public double calculatePrice(Showtime showtime, Seat seat, Cinema cinema) {
        double basePrice = cinema.basePrice(seat.getTier());
        basePrice *= showtime.getFilmMultiplier();
        basePrice *= showtime.getSlotMultiplier();

        int booked = showtime.countBooked();
        int total  = showtime.totalSeats();

        FareContext ctx = new FareContext(showtime, seat, basePrice, booked, total);

        double price = basePrice;
        for (FareRule rule : getActiveRules()) {
            price = rule.apply(price, ctx);
        }

        double floor = cinema.basePrice(seat.getTier());
        price = Math.max(price, floor);
        return Math.round(price * 100.0) / 100.0;
    }

    public double calculateTotal(Showtime showtime, List<Seat> seats, Cinema cinema) {
        double total = 0;
        for (Seat s : seats) {
            total += calculatePrice(showtime, s, cinema);
        }
        return total;
    }
}
