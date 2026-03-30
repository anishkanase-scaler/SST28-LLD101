import java.util.Objects;

public class Seat {
    private final String id;
    private final SeatTier tier;

    public Seat(String id, SeatTier tier) {
        this.id = Objects.requireNonNull(id);
        this.tier = Objects.requireNonNull(tier);
    }

    public String getId()       { return id; }
    public SeatTier getTier()   { return tier; }

    @Override
    public String toString() {
        return id + "(" + tier + ")";
    }
}
