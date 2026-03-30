import java.time.LocalDateTime;

public class ExternalRequest {

    private final int floor;
    private final Direction direction;
    private final LocalDateTime requestedAt;

    public ExternalRequest(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
        this.requestedAt = LocalDateTime.now();
    }

    public int getFloor() { return floor; }
    public Direction getDirection() { return direction; }
    public LocalDateTime getRequestedAt() { return requestedAt; }

    @Override
    public String toString() {
        return "ExternalRequest[floor=" + floor + ", dir=" + direction + "]";
    }
}
