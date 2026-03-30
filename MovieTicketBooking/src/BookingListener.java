public interface BookingListener {
    void onBooked(Booking booking);
    void onCancelled(Booking booking);
}
