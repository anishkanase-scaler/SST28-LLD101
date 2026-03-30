import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private final List<BookingListener> listeners = new ArrayList<>();

    public void register(BookingListener listener) {
        listeners.add(listener);
    }

    public void notifyBooked(Booking booking) {
        for (BookingListener l : listeners) l.onBooked(booking);
    }

    public void notifyCancelled(Booking booking) {
        for (BookingListener l : listeners) l.onCancelled(booking);
    }
}
