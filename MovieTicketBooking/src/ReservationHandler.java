import java.util.*;
import java.util.stream.Collectors;

public class ReservationHandler {
    private final DataStore store;
    private final FareCalculator fareCalc;
    private final PaymentGateway payGateway;
    private final EventBus eventBus;

    public ReservationHandler(DataStore store, FareCalculator fareCalc,
                              PaymentGateway payGateway, EventBus eventBus) {
        this.store = store;
        this.fareCalc = fareCalc;
        this.payGateway = payGateway;
        this.eventBus = eventBus;
    }

    public Booking bookSeats(String patronId, String showtimeId,
                             List<String> seatIds, PayMethod payMethod) {
        Showtime show = store.getShowtime(showtimeId);
        if (show == null) throw new IllegalArgumentException("Showtime not found: " + showtimeId);
        Cinema cinema = store.getCinema(show.getCinemaId());
        Hall hall = cinema.findHall(show.getHallId());

        show.lockSeats(seatIds, patronId);

        try {
            List<Seat> seats = seatIds.stream()
                    .map(hall::getSeatById)
                    .collect(Collectors.toList());

            double total = fareCalc.calculateTotal(show, seats, cinema);

            Payment payment = payGateway.makePayment(payMethod, patronId, total);

            if (payment.getStatus() == PayState.FAILED) {
                show.releaseSeats(seatIds);
                throw new RuntimeException("Payment failed for booking attempt");
            }

            show.confirmBooking(seatIds);

            Booking booking = new Booking.Builder()
                    .id(SequenceGenerator.bookingId())
                    .patronId(patronId)
                    .showtimeId(showtimeId)
                    .seatIds(seatIds)
                    .paymentId(payment.getId())
                    .amount(total)
                    .payMethod(payMethod)
                    .status(BookingStatus.CONFIRMED)
                    .build();

            store.addBooking(booking);
            eventBus.notifyBooked(booking);
            return booking;

        } catch (Exception e) {
            show.releaseSeats(seatIds);
            throw e;
        }
    }

    public void cancelBooking(String bookingId) {
        Booking booking = store.getBooking(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found: " + bookingId);

        Showtime show = store.getShowtime(booking.getShowtimeId());
        show.releaseSeats(booking.getSeatIds());

        if (booking.getPaymentId() != null) {
            payGateway.refund(booking.getPaymentId());
        }

        booking.markCancelled();
        eventBus.notifyCancelled(booking);
    }

    public double estimateTotal(String showtimeId, List<String> seatIds) {
        Showtime show = store.getShowtime(showtimeId);
        if (show == null) throw new IllegalArgumentException("Showtime not found: " + showtimeId);
        Cinema cinema = store.getCinema(show.getCinemaId());
        Hall hall = cinema.findHall(show.getHallId());
        List<Seat> seats = seatIds.stream().map(hall::getSeatById).collect(Collectors.toList());
        return fareCalc.calculateTotal(show, seats, cinema);
    }

    public Map<String, SeatStatus> showSeatMap(String showtimeId) {
        Showtime show = store.getShowtime(showtimeId);
        if (show == null) throw new IllegalArgumentException("Showtime not found: " + showtimeId);
        return show.getSeatMapSnapshot();
    }
}
