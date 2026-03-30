import java.util.*;

public final class SeatGridFactory {
    private SeatGridFactory() {}

    public static List<Seat> createSimpleLayout(Map<SeatTier, Integer> tierCounts) {
        List<Seat> seats = new ArrayList<>();
        for (Map.Entry<SeatTier, Integer> entry : tierCounts.entrySet()) {
            SeatTier tier = entry.getKey();
            int count = entry.getValue();
            String prefix = tier.name().substring(0, 1);
            for (int i = 1; i <= count; i++) {
                seats.add(new Seat(prefix + i, tier));
            }
        }
        return seats;
    }
}
