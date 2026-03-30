import java.time.LocalDateTime;
import java.util.*;

public class Showtime {
    private final String id;
    private final String filmId;
    private final String cinemaId;
    private final String hallId;
    private final LocalDateTime startTime;
    private final double filmMultiplier;
    private final double slotMultiplier;
    private final Map<String, SeatStatus> seatStates;
    private final Map<String, SeatLock> activeLocks;

    private static final long LOCK_DURATION_SEC = 120;

    public Showtime(String id, String filmId, String cinemaId, String hallId,
                    LocalDateTime startTime, double filmMultiplier, double slotMultiplier) {
        this.id = Objects.requireNonNull(id);
        this.filmId = Objects.requireNonNull(filmId);
        this.cinemaId = Objects.requireNonNull(cinemaId);
        this.hallId = Objects.requireNonNull(hallId);
        this.startTime = Objects.requireNonNull(startTime);
        this.filmMultiplier = filmMultiplier;
        this.slotMultiplier = slotMultiplier;
        this.seatStates = new LinkedHashMap<>();
        this.activeLocks = new HashMap<>();
    }

    public synchronized void initialiseSeatMap(List<Seat> seats) {
        for (Seat s : seats) {
            seatStates.put(s.getId(), SeatStatus.FREE);
        }
    }

    public synchronized void lockSeats(List<String> seatIds, String patronId) {
        clearExpiredLocks();
        for (String sid : seatIds) {
            SeatStatus st = seatStates.get(sid);
            if (st == null) throw new IllegalArgumentException("Unknown seat: " + sid);
            if (st != SeatStatus.FREE) throw new RuntimeException("Seat " + sid + " is " + st + ", cannot lock");
        }
        for (String sid : seatIds) {
            seatStates.put(sid, SeatStatus.HELD);
            activeLocks.put(sid, new SeatLock(patronId, System.currentTimeMillis() + LOCK_DURATION_SEC * 1000));
        }
    }

    public synchronized void confirmBooking(List<String> seatIds) {
        for (String sid : seatIds) {
            if (seatStates.get(sid) != SeatStatus.HELD)
                throw new RuntimeException("Seat " + sid + " is not HELD, cannot confirm");
            seatStates.put(sid, SeatStatus.BOOKED);
            activeLocks.remove(sid);
        }
    }

    public synchronized void releaseSeats(List<String> seatIds) {
        for (String sid : seatIds) {
            seatStates.put(sid, SeatStatus.FREE);
            activeLocks.remove(sid);
        }
    }

    public synchronized Map<String, SeatStatus> getSeatMapSnapshot() {
        clearExpiredLocks();
        return Collections.unmodifiableMap(new LinkedHashMap<>(seatStates));
    }

    public synchronized int countBooked() {
        return (int) seatStates.values().stream().filter(s -> s == SeatStatus.BOOKED).count();
    }

    public synchronized int totalSeats() {
        return seatStates.size();
    }

    private void clearExpiredLocks() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, SeatLock>> it = activeLocks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, SeatLock> entry = it.next();
            if (entry.getValue().expiresAtMillis < now) {
                seatStates.put(entry.getKey(), SeatStatus.FREE);
                it.remove();
            }
        }
    }

    public String getId()               { return id; }
    public String getFilmId()           { return filmId; }
    public String getCinemaId()         { return cinemaId; }
    public String getHallId()           { return hallId; }
    public LocalDateTime getStartTime() { return startTime; }
    public double getFilmMultiplier()   { return filmMultiplier; }
    public double getSlotMultiplier()   { return slotMultiplier; }

    @Override
    public String toString() {
        return "Showtime " + id + " @ " + startTime;
    }

    static class SeatLock {
        final String patronId;
        final long expiresAtMillis;

        SeatLock(String patronId, long expiresAtMillis) {
            this.patronId = patronId;
            this.expiresAtMillis = expiresAtMillis;
        }
    }
}
