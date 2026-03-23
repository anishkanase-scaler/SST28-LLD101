import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {

    private final List<ParkingFloor> floors;
    private final Map<String, Gate> gates;
    private final Map<String, ParkingTicket> activeTickets;
    private final SlotAssignmentStrategy strategy;
    private int ticketSeq;

    public ParkingLot(SlotAssignmentStrategy strategy) {
        this.floors = new ArrayList<>();
        this.gates = new HashMap<>();
        this.activeTickets = new HashMap<>();
        this.strategy = strategy;
        this.ticketSeq = 0;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public void addGate(Gate gate) {
        gates.put(gate.getGateId(), gate);
    }

    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime, SlotType requestedType, String gateId) {
        Gate gate = gates.get(gateId);
        if (gate == null) {
            throw new RuntimeException("Invalid gate: " + gateId);
        }

        List<SlotType> compatible = getCompatibleTypes(vehicle.getVehicleType());

        for (SlotType type : SlotType.values()) {
            if (type.ordinal() < requestedType.ordinal()) continue;
            if (!compatible.contains(type)) continue;

            List<ParkingSlot> available = getAvailableSlotsByType(type);
            if (available.isEmpty()) continue;

            ParkingSlot chosen = strategy.assignSlot(available, gate);
            if (chosen != null) {
                chosen.markOccupied();
                String ticketId = "TKT-" + (++ticketSeq);
                ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, chosen, entryTime);
                activeTickets.put(ticketId, ticket);
                return ticket;
            }
        }

        throw new RuntimeException("No compatible slot available for " + vehicle);
    }

    public Map<SlotType, Integer> status() {
        Map<SlotType, Integer> availability = new LinkedHashMap<>();
        for (SlotType type : SlotType.values()) {
            availability.put(type, 0);
        }
        for (ParkingFloor floor : floors) {
            for (ParkingSlot slot : floor.getSlots()) {
                if (!slot.isOccupied()) {
                    availability.put(slot.getSlotType(), availability.get(slot.getSlotType()) + 1);
                }
            }
        }
        return availability;
    }

    public Bill exit(ParkingTicket ticket, LocalDateTime exitTime) {
        ticket.getAllocatedSlot().markFree();
        activeTickets.remove(ticket.getTicketId());
        return new Bill(ticket, exitTime);
    }

    private List<SlotType> getCompatibleTypes(VehicleType vehicleType) {
        switch (vehicleType) {
            case TWO_WHEELER:
                return Arrays.asList(SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE);
            case CAR:
                return Arrays.asList(SlotType.MEDIUM, SlotType.LARGE);
            case BUS:
                return Arrays.asList(SlotType.LARGE);
            default:
                return Collections.emptyList();
        }
    }

    private List<ParkingSlot> getAvailableSlotsByType(SlotType type) {
        List<ParkingSlot> result = new ArrayList<>();
        for (ParkingFloor floor : floors) {
            for (ParkingSlot slot : floor.getSlots()) {
                if (slot.getSlotType() == type && !slot.isOccupied()) {
                    result.add(slot);
                }
            }
        }
        return result;
    }
}
