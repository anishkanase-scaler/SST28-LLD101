public class PassengerCall {

    private final int originFloor;
    private final int destinationFloor;
    private final Direction movementDirection;
    private final double passengerMass;

    public PassengerCall(int originFloor, int destinationFloor,
                        Direction movementDirection, double passengerMass) {
        this.originFloor = originFloor;
        this.destinationFloor = destinationFloor;
        this.movementDirection = movementDirection;
        this.passengerMass = passengerMass;
    }

    public int getOriginFloor() { return originFloor; }
    public int getDestinationFloor() { return destinationFloor; }
    public Direction getMovementDirection() { return movementDirection; }
    public double getPassengerMass() { return passengerMass; }

    @Override
    public String toString() {
        return "PassengerCall{origin=" + originFloor + ", dest=" + destinationFloor
                + ", dir=" + movementDirection + ", mass=" + passengerMass + "kg}";
    }
}
