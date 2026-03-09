package com.example.payments;

import java.util.Objects;

public class SafeCashAdapter implements PaymentGateway {

    private final SafeCashClient safeCashClient;

    public SafeCashAdapter(SafeCashClient safeCashClient) {
        this.safeCashClient = Objects.requireNonNull(safeCashClient);
    }

    @Override
    public String charge(String customerId, int amountCents) {
        SafeCashPayment payment = safeCashClient.createPayment(amountCents, customerId);
        return payment.confirm();
    }
}