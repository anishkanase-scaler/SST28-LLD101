# Multilevel Parking Lot

## Problem

Design a multilevel parking lot with three slot types (small, medium, large), multiple entry gates, automatic nearest-slot assignment, vehicle-to-slot compatibility, and billing based on slot type.

## Design Approach

### Slot Types & Vehicle Compatibility

| Vehicle | Compatible Slots |
|---------|-----------------|
| TWO_WHEELER | SMALL, MEDIUM, LARGE |
| CAR | MEDIUM, LARGE |
| BUS | LARGE only |

A smaller vehicle can park in a larger slot if no matching slot is available. Billing is always based on the **allocated slot type**, not the vehicle type. So a bike in a MEDIUM slot pays the MEDIUM rate.

### Strategy Pattern — Slot Assignment

The slot selection logic is abstracted behind `SlotAssignmentStrategy`. The current implementation, `NearestSlotStrategy`, picks the closest available slot to the entry gate using:

```
distance = |slot.floor - gate.floor| * 100 + |slot.position - gate.position|
```

Floor difference is heavily weighted so same-floor slots are preferred. A new strategy (e.g., random, least-utilized floor) can be plugged in without changing `ParkingLot`.

### Core Flow

**Parking (`park()`):**
1. Look up the entry gate
2. Determine compatible slot types for the vehicle
3. Starting from the requested type, try each compatible type (smallest first)
4. Use the strategy to pick the nearest available slot
5. Mark slot occupied, generate and return a `ParkingTicket`

**Status (`status()`):**
Returns available slot count grouped by slot type.

**Exit (`exit()`):**
1. Free the allocated slot
2. Compute duration (minimum 1 hour)
3. Calculate amount = hours x slot hourly rate
4. Return a `Bill`

## Class Diagram

See [docs/class-diagram.md](docs/class-diagram.md)

## How to Run

```bash
cd parking-lot/src
javac *.java
java Main
```
