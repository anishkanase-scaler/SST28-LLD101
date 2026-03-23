# Design a Pen

## Problem

Design a pen system supporting multiple pen types (ball pen, ink pen), different opening mechanisms (cap, click), refillable ink, and an optional grip feature — all without modifying existing code when adding new behavior.

## Design Approach

### Strategy Pattern — Refill & Start Mechanisms

The pen's refill and start behaviors vary independently of the pen type. Instead of hardcoding these into each pen subclass, they are extracted into strategy interfaces:

- **RefillStrategy** — defines how a pen gets refilled. `SimpleRefill` sets the new ink color directly.
- **StartStrategy** — defines how a pen is activated. `CapStart` removes the cap, `ClickStart` uses a click mechanism.

This way, any pen type can be paired with any mechanism without creating a class explosion (e.g., no need for `BallPenWithCap`, `BallPenWithClick`, etc.).

### Inheritance — Pen Types

`Pen` is an abstract base class. `BallPen` and `InkPen` extend it and provide their own `write()` implementation. Both check that the pen is activated before writing.

### Decorator Pattern — Grip Feature

Adding a grip changes the writing behavior but should not require modifying `BallPen` or `InkPen`. The `PenDecorator` abstract class extends `Pen` and wraps another `Pen` instance. `GripDecorator` overrides `write()` to add grip behavior and then delegates to the wrapped pen.

### Factory — Pen Creation

`PenFactory.createPen(type, color, mechanism)` centralizes object creation. The caller does not need to know which concrete classes or strategies to instantiate.

## Class Diagram

See [docs/class-diagram.md](docs/class-diagram.md)

## How to Run

```bash
cd pen-design/src
javac *.java
java Main
```
