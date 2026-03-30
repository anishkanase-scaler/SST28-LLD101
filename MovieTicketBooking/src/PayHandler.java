public interface PayHandler {
    Payment process(String paymentId, String patronId, double amount);
    PayState refund(Payment payment);
}
