public class InvoiceFormatter {
    public static String format(Bill b) {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice# ").append(b.invId).append("\n");
        for (String line : b.lineDescs) {
            sb.append(line).append("\n");
        }
        sb.append(String.format("Subtotal: %.2f\n", b.subtotal));
        sb.append(String.format("Tax(%.0f%%): %.2f\n", b.taxPct, b.tax));
        sb.append(String.format("Discount: -%.2f\n", b.discount));
        sb.append(String.format("TOTAL: %.2f\n", b.total));
        return sb.toString();
    }
}
