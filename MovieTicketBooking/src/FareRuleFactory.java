import java.time.Month;
import java.util.*;

public final class FareRuleFactory {
    private FareRuleFactory() {}

    public static List<FareRule> createRules(List<String> codes) {
        List<FareRule> rules = new ArrayList<>();
        for (String code : codes) {
            switch (code) {
                case "WEEKEND_20":
                    rules.add(new WeekendSurcharge(1.20));
                    break;
                case "DEMAND_80_50":
                    rules.add(new DemandSurcharge(0.80, 1.50));
                    break;
                case "MONTH_PEAK":
                    Map<Month, Double> peaks = new HashMap<>();
                    peaks.put(Month.DECEMBER, 1.25);
                    peaks.put(Month.JANUARY, 1.10);
                    rules.add(new SeasonalSurcharge(peaks));
                    break;
                default:
                    System.out.println("[WARN] Unknown fare rule code: " + code);
            }
        }
        return rules;
    }
}
