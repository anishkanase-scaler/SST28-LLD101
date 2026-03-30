import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private final String id;
    private final String patronId;
    private final double amount;
    private final PayMethod method;
    private PayState status;
    private final LocalDateTime createdAt;

    public Payment(String id, String patronId, double amount, PayMethod method, PayState status) {
        this.id = Objects.requireNonNull(id);
        this.patronId = Objects.requireNonNull(patronId);
        this.amount = amount;
        this.method = Objects.requireNonNull(method);
        this.status = Objects.requireNonNull(status);
        this.createdAt = LocalDateTime.now();
    }

    public void setStatus(PayState status) {
        this.status = status;
    }

    public String getId()               { return id; }
    public String getPatronId()         { return patronId; }
    public double getAmount()           { return amount; }
    public PayMethod getMethod()        { return method; }
    public PayState getStatus()         { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Payment " + id + " [" + status + "] Rs." + String.format("%.2f", amount) + " via " + method;
    }
}
