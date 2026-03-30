public class WalletPayHandler implements PayHandler {
    @Override
    public Payment process(String paymentId, String patronId, double amount) {
        return new Payment(paymentId, patronId, amount, PayMethod.WALLET, PayState.DONE);
    }

    @Override
    public PayState refund(Payment payment) {
        payment.setStatus(PayState.REVERSED);
        return PayState.REVERSED;
    }
}
