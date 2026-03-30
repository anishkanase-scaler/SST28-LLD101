public class DemandSurcharge implements FareRule {
    private final double threshold;
    private final double multiplier;

    public DemandSurcharge(double threshold, double multiplier) {
        this.threshold = threshold;
        this.multiplier = multiplier;
    }

    @Override
    public double apply(double currentPrice, FareContext context) {
        if (context.getFillRatio() >= threshold) {
            return currentPrice * multiplier;
        }
        return currentPrice;
    }

    @Override
    public String name() { return "DemandSurcharge"; }
}
