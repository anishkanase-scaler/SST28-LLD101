# Film Booking System

## Overview

A seat reservation platform for cinemas with concurrent booking safety, dynamic fare calculation, and event-driven notifications. The system coordinates seat locking, payment processing, and real-time pricing through a unified facade.

## Core Challenges Solved

1. **Concurrent Seat Contention** — Synchronized seat state tracking inside `Showtime` prevents two patrons from locking the same seat simultaneously
2. **Timed Seat Holds** — `SeatLock` enforces a 120-second reservation window; expired locks auto-release before every write operation
3. **Runtime Fare Flexibility** — Managers configure pricing rules at runtime via `FareRuleFactory` without touching source code
4. **Atomic Rollback** — Payment failure triggers immediate seat release, keeping system state consistent

## System Architecture

### Film Catalogue (`Film`)

Immutable record with title, language, and duration. Stored in `DataStore` and referenced by ID in showtimes.

### Cinema & Hall Model

`Cinema` manages a city-level multiplex with per-tier base prices (`EnumMap<SeatTier, Double>`). Each cinema contains multiple `Hall` instances. `SeatGridFactory` auto-generates seat layouts from tier counts.

### Showtime State Machine

`Showtime` tracks per-seat status via synchronized methods:
- **FREE** — Available for selection
- **HELD** — Temporarily locked during payment (120s TTL)
- **BOOKED** — Payment confirmed, seat reserved

Inner `SeatLock` class pairs patron ID with expiry timestamp. `clearExpiredLocks()` runs before every state mutation.

### Fare Calculation Pipeline

`FareCalculator` applies active `FareRule` implementations in sequence:

- **WeekendSurcharge** — Multiplier on Saturday/Sunday showtimes
- **DemandSurcharge** — Surge pricing when fill ratio exceeds threshold
- **SeasonalSurcharge** — Month-based multipliers for peak seasons

Final price enforced to never drop below the seat tier's base price.

### Booking Lifecycle

`ReservationHandler` orchestrates: lock seats -> calculate fare -> process payment -> confirm booking. On failure at any step, seats are released atomically. `EventBus` notifies registered `BookingListener` instances on every booking and cancellation.

## File Structure

| Class | Purpose |
|-------|---------|
| SeatTier | Seat category enum (REGULAR, CLUB, PREMIUM, VIP) |
| SeatStatus | Availability enum (FREE, HELD, BOOKED) |
| PatronRole | User role enum (VIEWER, MANAGER) |
| PayMethod | Payment method enum (UPI, CARD, WALLET, NETBANK) |
| PayState | Payment state enum (DONE, FAILED, REVERSED) |
| BookingStatus | Booking state enum (CONFIRMED, CANCELLED) |
| Film | Immutable movie record |
| Cinema | Multiplex with halls and tier-based pricing |
| Hall | Screen with seat grid |
| Seat | Individual seat with tier |
| Showtime | Scheduled show with synchronized seat map |
| Patron | Registered user with role |
| Booking | Reservation record (Builder pattern) |
| Payment | Transaction record with status tracking |
| SequenceGenerator | Thread-safe ID generation (Singleton) |
| DataStore | Centralized in-memory storage (Singleton) |
| FareRule | Pricing strategy interface |
| FareContext | Pricing computation context |
| WeekendSurcharge | Weekend multiplier rule |
| DemandSurcharge | Demand-based surge rule |
| SeasonalSurcharge | Month-based multiplier rule |
| FareRuleFactory | Creates rules from string codes |
| FareCalculator | Applies rules with floor enforcement |
| PayHandler | Payment processor interface |
| CardPayHandler | Card payment implementation |
| UpiPayHandler | UPI payment implementation |
| WalletPayHandler | Wallet payment implementation |
| PayHandlerFactory | Factory for payment processors |
| BookingListener | Observer interface for booking events |
| LoggingListener | Console logging observer |
| EventBus | Observer notification manager |
| SeatGridFactory | Generates seat layouts from tier counts |
| SetupService | Admin operations (films, cinemas, showtimes) |
| BrowseService | Search and discovery queries |
| ReservationHandler | Booking orchestration with rollback |
| PaymentGateway | Payment processing and refunds |
| CinemaFacade | Unified entry point (Facade pattern) |

## How to Run

```bash
cd MovieTicketBooking
javac -d bin src/*.java Main.java
java -cp bin Main
```

Demonstrates:
- Film and cinema registration with seat grid generation
- Showtime scheduling with multipliers
- City-based browsing and seat map viewing
- Seat booking with dynamic pricing
- Concurrent booking rejection (same seats blocked)
- Booking cancellation with refund
- Observer notifications on booking events
