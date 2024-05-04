package minesweeper.plus.core;

import java.util.HashSet;
import java.util.Set;

public class N10Proximity implements Proximity {
    @Override
    public Set<Coordinates> neighbourhood(Coordinates guess) {
        Set<Coordinates> result = new HashSet<>();
        result.add(new Coordinates(guess.xValue, guess.yValue + 1, guess.zValue));          // N
        result.add(new Coordinates(guess.xValue + 1, guess.yValue + 1, guess.zValue));          // NE
        result.add(new Coordinates(guess.xValue + 1, guess.yValue, guess.zValue));          // E
        result.add(new Coordinates(guess.xValue + 1, guess.yValue - 1, guess.zValue));          // SE
        result.add(new Coordinates(guess.xValue, guess.yValue - 1, guess.zValue));          // S
        result.add(new Coordinates(guess.xValue - 1, guess.yValue - 1, guess.zValue));          // SW
        result.add(new Coordinates(guess.xValue - 1, guess.yValue, guess.zValue));          // W
        result.add(new Coordinates(guess.xValue - 1, guess.yValue + 1, guess.zValue));          // NW
        result.add(new Coordinates(guess.xValue, guess.yValue, guess.zValue - 1));          // F
        result.add(new Coordinates(guess.xValue, guess.yValue, guess.zValue + 1));          // B
        return result;
    }
}