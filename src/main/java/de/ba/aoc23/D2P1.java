package de.ba.aoc23;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 *  Using only Stream interfaces broke my brain. So I went the easy route to get through the 3 puzzles on the 3rd of december.
 *  Result is 3059
 */
public class D2P1 extends PuzzleSolver {

    record Game(int id, boolean isValid) {
    }

    private final Map<String, Integer> availableDices = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14
    );

    @Override
    protected String solve(Stream<String> linesAsStream) {
        List<Game> games = new ArrayList<>();

        linesAsStream.forEach(line -> {
            var gameSplit = line.split(": ");
            var gameId = gameSplit[0].split(" ")[1];
            boolean gameIsValid = true;

            String[] rounds = gameSplit[1].split("; ");
            for (String round : rounds) {
                String[] diceThrows = round.split(", ");

                for (String diceThrow : diceThrows) {
                    String[] diceThrowSplit = diceThrow.split(" ");
                    var numberOfThrownDice = Integer.parseInt(diceThrowSplit[0]);
                    var diceColor = diceThrowSplit[1];

                    if(availableDices.get(diceColor) < numberOfThrownDice) {
                        gameIsValid = false;
                    }
                }
            }

            games.add(new Game(Integer.parseInt(gameId), gameIsValid));
        });

        return String.valueOf(games.stream().filter(g -> g.isValid).mapToInt(g -> g.id).sum());
    }
}
