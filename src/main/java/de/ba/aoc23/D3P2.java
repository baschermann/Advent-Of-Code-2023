package de.ba.aoc23;

import java.util.*;
import java.util.stream.Stream;

/**
 * 81721933 is the correct answer
 * Again I mostly got stuck on java streams trying to multiply the two gear numbers together.
 */
public class D3P2 extends PuzzleSolver {

    record Number(int row, int startIndex, int endIndex, int value) {}
    record Location(int x, int y) {}
    private final List<Location> adjacentLocations =List.of(
            new Location(-1, -1),
            new Location(0, -1),
            new Location(+1, -1),

            new Location(-1, 0),
            new Location(+1, 0),

            new Location(-1, +1),
            new Location(0, +1),
            new Location(+1, +1)
    );

    private char[][] grid;
    private final Map<Location, HashSet<Number>> gears = new HashMap<>();

    @Override
    protected String solve(Stream<String> linesAsStream) {
        List<char[]> rows = new ArrayList<>();
        linesAsStream.forEach(lines -> rows.add(lines.toCharArray()));
        grid = rows.toArray(char[][]::new);

        analyzeGrid(grid);

        List<HashSet<Number>> gearsWithTwoNumbers = gears.values().stream()
                .filter(numbers -> numbers.size() == 2)
                .toList();

        int result = 0;
        for (HashSet<Number> gearsWithTwoNumber : gearsWithTwoNumbers) {
            result += gearsWithTwoNumber.stream().mapToInt(b -> b.value).reduce(1, (a, b) -> a * b);
        }

        return String.valueOf(result);
    }

    private void analyzeGrid(char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                analyzeLocation(x, y);
            }
        }
    }

    private void analyzeLocation(int x, int y) {
        char c = grid[y][x];

        if(c == '*') {
            //System.out.println("analyzing location x:" + x + ", y: " + y + " with char: " + c);
            captureSurroundingNumbers(y, x);
        }
    }

    private void captureSurroundingNumbers(int y, int x) {
        for (Location adjacentLocation : adjacentLocations) {
            Location locationToCheck = new Location(adjacentLocation.x + x, adjacentLocation.y + y);

            if(isInBounds(locationToCheck)) {
                //System.out.println("Checking at " + locationToCheck + " with char: " + grid[locationToCheck.y][locationToCheck.x]);
                if(Character.isDigit(grid[locationToCheck.y][locationToCheck.x])) {
                    Location gearLocation = new Location(x, y);
                    HashSet<Number> numbersAdjacentToGear = gears.getOrDefault(gearLocation, new HashSet<>());
                    Number adjacentNumber = captureNumberAt(locationToCheck);
                    numbersAdjacentToGear.add(adjacentNumber);
                    gears.put(gearLocation, numbersAdjacentToGear);
                }
            }
        }
    }

    private Number captureNumberAt(Location locationToCheck) {
        //System.out.println("Capturing number at: " +  locationToCheck);
        int xLeft = getMostLeftPositionOfNumber(locationToCheck.x, locationToCheck.y);
        int xRight = getMostRightPositionOfNumber(locationToCheck.x, locationToCheck.y);

        //System.out.println("row: " + locationToCheck.y + ", xLeft: " + xLeft + ", xRight: " + xRight);

        var numberBuilder = new StringBuilder();
        for (int xPos = xLeft; xPos <= xRight; xPos++) {
            numberBuilder.append(grid[locationToCheck.y][xPos]);
        }

        return new Number(locationToCheck.y, xLeft, xRight, Integer.parseInt(numberBuilder.toString()));
    }

    private int getMostLeftPositionOfNumber(int x, int y) {
        if(isInBounds(new Location(x, y))) {
            char c = grid[y][x];
            if(Character.isDigit(c)) {
                return getMostLeftPositionOfNumber(x - 1, y);
            }
        }

        return x + 1;
    }

    private int getMostRightPositionOfNumber(int x, int y) {
        if(isInBounds(new Location(x, y))) {
            char c = grid[y][x];
            if(Character.isDigit(c)) {
                return getMostRightPositionOfNumber(x + 1, y);
            }
        }

        return x - 1;
    }

    private boolean isInBounds(Location location) {
        return location.y >= 0
                && location.x >= 0
                && location.y < grid.length
                && location.x < grid[location.y].length;
    }
}
