import java.util.*;

public class Hall {
    private final String id;
    private final String name;
    private final List<Seat> seats;
    private final Map<String, Seat> seatById;

    public Hall(String id, String name, List<Seat> seats) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.seats = Collections.unmodifiableList(new ArrayList<>(seats));
        Map<String, Seat> map = new LinkedHashMap<>();
        for (Seat s : seats) map.put(s.getId(), s);
        this.seatById = Collections.unmodifiableMap(map);
    }

    public Seat getSeatById(String seatId) {
        return seatById.get(seatId);
    }

    public String getId()           { return id; }
    public String getName()         { return name; }
    public List<Seat> getSeats()    { return seats; }
    public int capacity()           { return seats.size(); }

    @Override
    public String toString() {
        return name + " (" + seats.size() + " seats)";
    }
}
