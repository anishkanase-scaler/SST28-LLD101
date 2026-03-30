import java.util.*;

public class Cinema {
    private final String id;
    private final String name;
    private final String city;
    private final EnumMap<SeatTier, Double> basePrices;
    private final List<Hall> halls;

    public Cinema(String id, String name, String city, EnumMap<SeatTier, Double> basePrices) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.city = Objects.requireNonNull(city);
        this.basePrices = new EnumMap<>(basePrices);
        this.halls = new ArrayList<>();
    }

    public void addHall(Hall hall) {
        halls.add(Objects.requireNonNull(hall));
    }

    public Hall findHall(String hallId) {
        return halls.stream()
                .filter(h -> h.getId().equals(hallId))
                .findFirst().orElse(null);
    }

    public double basePrice(SeatTier tier) {
        return basePrices.getOrDefault(tier, 100.0);
    }

    public String getId()                           { return id; }
    public String getName()                         { return name; }
    public String getCity()                         { return city; }
    public Map<SeatTier, Double> getBasePrices()    { return Collections.unmodifiableMap(basePrices); }
    public List<Hall> getHalls()                    { return Collections.unmodifiableList(halls); }

    @Override
    public String toString() {
        return name + " [" + city + "], " + halls.size() + " hall(s)";
    }
}
