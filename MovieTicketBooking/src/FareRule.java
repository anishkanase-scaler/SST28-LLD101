public interface FareRule {
    double apply(double currentPrice, FareContext context);
    String name();
}
