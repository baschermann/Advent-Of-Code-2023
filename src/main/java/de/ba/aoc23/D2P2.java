package de.ba.aoc23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 *  This one was easy to adapt. Only 15 Minutes. Huh.
 */
public class D2P2 extends PuzzleSolver {

    record Game(int power) {
    }

    @Override
    protected String solve(Stream<String> linesAsStream) {
        List<Game> games = new ArrayList<>();

        linesAsStream.forEach(line -> {
            var gameSplit = line.split(": ");
            var minRequiredDicesWithColor = new HashMap<String, Integer>();

            String[] rounds = gameSplit[1].split("; ");
            for (String round : rounds) {
                String[] diceThrows = round.split(", ");

                for (String diceThrow : diceThrows) {
                    String[] diceThrowSplit = diceThrow.split(" ");
                    var numberOfThrownDice = Integer.parseInt(diceThrowSplit[0]);
                    var diceColor = diceThrowSplit[1];

                    var currentDiceOfColorRequired = minRequiredDicesWithColor.getOrDefault(diceColor, 0);
                    if(currentDiceOfColorRequired < numberOfThrownDice) {
                        minRequiredDicesWithColor.put(diceColor, numberOfThrownDice);
                    }
                }
            }

            int total = minRequiredDicesWithColor.values().stream().reduce(1, (a, b) -> a * b);
            games.add(new Game(total));
        });

        return String.valueOf(games.stream().mapToInt(g -> g.power).sum());
    }
}
