public final class PayHandlerFactory {
    private PayHandlerFactory() {}

    public static PayHandler create(PayMethod method) {
        switch (method) {
            case UPI:     return new UpiPayHandler();
            case CARD:    return new CardPayHandler();
            case WALLET:  return new WalletPayHandler();
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }
}
