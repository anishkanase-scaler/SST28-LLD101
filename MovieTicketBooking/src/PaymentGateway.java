import java.util.UUID;

public class PaymentGateway {
    private final DataStore store;

    public PaymentGateway(DataStore store) {
        this.store = store;
    }

    public Payment makePayment(PayMethod method, String patronId, double amount) {
        String payId = "PMT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        PayHandler handler = PayHandlerFactory.create(method);
        Payment payment = handler.process(payId, patronId, amount);
        store.addPayment(payment);
        return payment;
    }

    public void refund(String paymentId) {
        Payment payment = store.getPayment(paymentId);
        if (payment == null) throw new IllegalArgumentException("Payment not found: " + paymentId);
        PayHandler handler = PayHandlerFactory.create(payment.getMethod());
        handler.refund(payment);
    }
}
