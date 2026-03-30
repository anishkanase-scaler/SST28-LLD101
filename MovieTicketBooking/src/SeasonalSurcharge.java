import java.time.Month;
import java.util.Map;

public class SeasonalSurcharge implements FareRule {
    private final Map<Month, Double> monthMultipliers;

    public SeasonalSurcharge(Map<Month, Double> monthMultipliers) {
        this.monthMultipliers = Map.copyOf(monthMultipliers);
    }

    @Override
    public double apply(double currentPrice, FareContext context) {
        Month month = context.getShowtime().getStartTime().getMonth();
        double mult = monthMultipliers.getOrDefault(month, 1.0);
        return currentPrice * mult;
    }

    @Override
    public String name() { return "SeasonalSurcharge"; }
}
