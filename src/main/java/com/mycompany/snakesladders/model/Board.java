/**
 *
 * @author LUTS
 */
package com.mycompany.snakesladders.model;

import java.util.Map;

public class Board {
    public static final int SIZE = 100;
    public static final int COLS = 10;

    public enum EventType { NONE, SNAKE, LADDER }

    public record MoveResult(EventType eventType, int finalPosition, int from, int to) {}

    private final Map<Integer, Integer> ladders = Map.of(
        1, 38, 4, 14, 9, 31, 21, 42, 28, 84, 36, 44, 51, 67, 71, 91, 80, 100
    );

    private final Map<Integer, Integer> snakes = Map.of(
        16, 6, 47, 26, 49, 11, 56, 53, 62, 19, 64, 60, 87, 24, 93, 73, 95, 75, 98, 78
    );

    public Map<Integer, Integer> getLadders() { return ladders; }
    public Map<Integer, Integer> getSnakes() { return snakes; }

    public MoveResult applyEvent(int pos) {
        if (ladders.containsKey(pos)) {
            int to = ladders.get(pos);
            return new MoveResult(EventType.LADDER, to, pos, to);
        } else if (snakes.containsKey(pos)) {
            int to = snakes.get(pos);
            return new MoveResult(EventType.SNAKE, to, pos, to);
        } else {
            return new MoveResult(EventType.NONE, pos, pos, pos);
        }
    }

    public boolean isWinningSquare(int pos) {
        return pos == SIZE;
    }
}
