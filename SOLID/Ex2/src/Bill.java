import java.util.List;

public class Bill {
    public final String invId;
    public final List<String> lineDescs;
    public final double subtotal;
    public final double taxPct;
    public final double tax;
    public final double discount;
    public final double total;

    public Bill(String invId, List<String> lineDescs, double subtotal, double taxPct, double tax, double discount, double total) {
        this.invId = invId;
        this.lineDescs = lineDescs;
        this.subtotal = subtotal;
        this.taxPct = taxPct;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
    }
}
