package de.ba.aoc23;

import java.util.stream.Stream;

public class D1P1 extends PuzzleSolver {

    @Override
    public String solve(Stream<String> linesAsStream) {
        return String.valueOf(linesAsStream
                .map(line -> line.replaceAll("[^0-9]", ""))
                .map(intLine -> "" + intLine.charAt(0) + intLine.charAt(intLine.length() - 1))
                .mapToInt(Integer::valueOf)
                .sum()
        );
    }
}
