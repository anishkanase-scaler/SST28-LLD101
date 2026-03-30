public class LoggingListener implements BookingListener {
    @Override
    public void onBooked(Booking booking) {
        System.out.println("[Observer] Booking confirmed: " + booking.getId()
                + ", seats=" + booking.getSeatIds());
    }

    @Override
    public void onCancelled(Booking booking) {
        System.out.println("[Observer] Booking cancelled: " + booking.getId()
                + ", refund initiated.");
    }
}
