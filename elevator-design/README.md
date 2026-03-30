# Lift Control System

## Overview

A vertically distributed passenger transport system managing multiple lift cabins with intelligent call routing, load management, and failover capability.

## Core Challenges Solved

1. **Decoupled Call Routing** — Separating passenger call origination from assignment logic enables flexible routing strategies
2. **Load Constraints** — Preventing overload while maintaining responsiveness
3. **Service Resilience** — Taking cabs offline without blocking the entire system
4. **Safety Critical Operations** — Emergency halt mechanism with immediate state change

## System Architecture

### Passenger Call Model (`PassengerCall`)

Encapsulates a lift request with:
- **Origin & Destination** — Where passenger is and wants to go
- **Direction Intent** — ASCENDING or DESCENDING preference
- **Mass Data** — Passenger weight in kilograms

This unified model flows through terminal → system → cab, eliminating fragmented request types.

### Extensible Assignment Strategies

`AssignmentAlgorithm` interface allows different routing policies:

- **SequentialAssignment** — Rotate through available cabs regardless of position
- **ProximityAssignment** — Minimize travel distance to pickup point

Each implements `selectOptimalCab()` and advertises itself via `getAlgorithmName()`. The system swaps strategies at runtime without stopping operations.

### Lift Cabin State Machine

`ElevatorCab` cycles through four states:
- **STATIONARY** — Idle, ready to accept calls
- **ASCENDING** — Moving upward toward destination(s)
- **DESCENDING** — Moving downward toward destination(s)
- **OUT_OF_SERVICE** — Offline for maintenance, no new calls accepted

Load tracking (`loadMass`) ensures no cabin exceeds 700kg capacity. Emergency activation immediately transitions to OUT_OF_SERVICE, clearing all pending operations.

### Terminal Interfaces

- **FloorTerminal** — Per-floor calling station with ASCEND/DESCEND buttons. Creates `PassengerCall` and submits to system
- **CabTerminal** — Interior control panel for floor selection and emergency override

## Implementation Details

### Thread Safety
- All public methods in `ElevatorCab` and `ElevatorSystem` are synchronized
- Prevents race conditions during concurrent floor updates and state transitions

### Load Validation
- System pre-filters available cabs before assignment
- Cabin rejects floor requests if adding the load would exceed capacity
- Overweight calls receive system-level rejection with logging

### Movement Simulation
- Each cab advances one floor per `moveOneLevel()` call
- Maintains set of destination floors, sorts by proximity (closest-first)
- Removes floor from queue upon arrival, logs the stop

## File Structure

| Class | Purpose |
|-------|---------|
| Direction | UP/DOWN intent enum |
| CabState | Cabin status enum (STATIONARY, ASCENDING, DESCENDING, OUT_OF_SERVICE) |
| ControlButton | Button types enum |
| PassengerCall | Request model with origin, destination, direction, mass |
| ElevatorCab | Core lift cabin logic with state machine |
| FloorTerminal | Floor-level call station |
| CabTerminal | Cabin interior controls |
| AssignmentAlgorithm | Strategy interface for routing |
| SequentialAssignment | Round-robin assignment strategy |
| ProximityAssignment | Nearest-cabin-first strategy |
| ElevatorSystem | Central dispatcher and lifecycle manager |
| Main | Simulation demonstration |

## How to Run

```bash
cd elevator-design/src
javac *.java
java Main
```

Demonstrates:
- External call dispatching (floor terminals)
- Internal floor selection (cabin terminals)
- Load capacity enforcement
- Service suspension & restoration
- Emergency halt with state recovery
- Live algorithm switching
