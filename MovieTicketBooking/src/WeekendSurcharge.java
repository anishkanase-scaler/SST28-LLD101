import java.time.DayOfWeek;

public class WeekendSurcharge implements FareRule {
    private final double multiplier;

    public WeekendSurcharge(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public double apply(double currentPrice, FareContext context) {
        DayOfWeek day = context.getShowtime().getStartTime().getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return currentPrice * multiplier;
        }
        return currentPrice;
    }

    @Override
    public String name() { return "WeekendSurcharge"; }
}
