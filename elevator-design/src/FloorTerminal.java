import java.util.*;

public class FloorTerminal {

    private final int floorLevel;
    private final List<ControlButton> buttons;
    private final ElevatorSystem system;

    public FloorTerminal(int floorLevel, ElevatorSystem system) {
        this.floorLevel = floorLevel;
        this.system = system;
        this.buttons = new ArrayList<>();
        buttons.add(ControlButton.ASCEND);
        buttons.add(ControlButton.DESCEND);
    }

    public synchronized void activateButton(ControlButton btn, double mass) {
        System.out.println("[Floor " + floorLevel + "] Button " + btn + " pressed (" + mass + "kg)");
        Direction dir = btn == ControlButton.ASCEND ? Direction.ASCENDING : Direction.DESCENDING;
        PassengerCall call = new PassengerCall(floorLevel, -1, dir, mass);
        system.processCall(call);
    }

    public int getFloorLevel() { return floorLevel; }
    public List<ControlButton> getButtons() { return new ArrayList<>(buttons); }

    @Override
    public String toString() {
        return "FloorTerminal[Level=" + floorLevel + "]";
    }
}
