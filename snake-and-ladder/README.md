# Snake and Ladder Game

## Problem

Build a Snake and Ladder game for an n x n board with configurable players and difficulty. The board has n snakes and n ladders placed randomly. Players roll a 6-sided dice, move forward, and encounter snakes (slide down) or ladders (climb up). The game runs until only one player remains.

## Design Approach

### Board Generation

`BoardGenerator` randomly places `n` snakes and `n` ladders on the board. A `reserved` set ensures no two entities share an endpoint, which naturally prevents cycles.

**Difficulty affects placement:**
- **EASY** — snakes drop you at most 25% of the board, ladders can boost you up to 50%
- **HARD** — snakes drop you up to 50% of the board, ladders boost at most 25%

### Board & Transitions

`Board` stores all snakes and ladders and builds a `HashMap<Integer, Integer>` mapping every snake head to its tail and every ladder bottom to its top. This gives O(1) position resolution after each dice roll.

### Game Loop

The `Game` class manages the turn-by-turn flow:

1. For each active player (skip those who already won):
   - Roll the dice (random 1-6)
   - Compute new position = current + dice value
   - If new position exceeds the board size, the player stays put
   - Otherwise, resolve the position through the transition map (snake/ladder check)
   - If the player reaches the last cell, they win and are ranked
2. The game continues until only 1 player has not finished

### Key Rules Implemented

- Players start at position 0 (outside the board)
- Cannot move past the last cell (n²)
- Snake: land on head, slide to tail
- Ladder: land on bottom, climb to top
- Billing is turn-based — all players take turns in order
- Game ends when at most 1 player is still playing

## Class Diagram

See [docs/class-diagram.md](docs/class-diagram.md)

## How to Run

```bash
cd snake-and-ladder/src
javac *.java
java Main
```

The program will ask for:
- Board dimension (n for n x n)
- Number of players
- Difficulty (easy/hard)
