package de.ba.aoc23;

import java.util.Map;
import java.util.stream.Stream;

/**
 * The 2nd part took me way to long (3h). I didn't understand the problem, as the example test did fine.
 * Replacing the first word occurrence of a digit is not required when there is a natural digit before it.
 * Doing so will cause the last worded digit to be partially replaced and won't be replaced with a real digit.
 */
public class D1P2 extends PuzzleSolver {

    // 54412 to low
    // 54426 to high
    // 50946 to low
    // 54418 was correct. First unnecessary replacement stopped finding the last required word replacement

    @Override
    protected String solve(Stream<String> linesAsStream) {
        return String.valueOf(linesAsStream
                .peek(System.out::println)
                .map(D1P2::replaceSpelledDigits)
                .map(line -> line.replaceAll("[^0-9]", ""))
                .map(intLine -> "" + intLine.charAt(0) + intLine.charAt(intLine.length() - 1))
                .mapToInt(Integer::valueOf)
                .peek(System.out::println)
                .sum()
        );
    }

    public static String replaceSpelledDigits(String line) {
        line = replaceFirst(line);
        line = replaceLast(line);

        return line;
    }

    private static String replaceFirst(String line) {
        int indexFirst = Integer.MAX_VALUE;

        Map.Entry<String, String> replacementFirst = null;
        for (Map.Entry<String, String> replacement : digitMapping.entrySet()) {
            int indexFirstFound = line.indexOf(replacement.getKey());
            if(indexFirstFound >= 0 && indexFirstFound < indexFirst) {
                indexFirst = indexFirstFound;
                replacementFirst = replacement;
            }
        }

        if(replacementFirst != null) {
            // check if there is a digit before it, if so don't replace
            boolean digitBeforeFirstIndex = false;
            char[] charArray = line.toCharArray();
            for(int i = 0; i < indexFirst; i++) {
                if(Character.isDigit(charArray[i])) {
                    digitBeforeFirstIndex = true;
                }
            }

            if(!digitBeforeFirstIndex) {
                line = line.replaceFirst(replacementFirst.getKey(), replacementFirst.getValue());
            }
        }
        return line;
    }

    private static String replaceLast(String line) {
        int indexLast= Integer.MIN_VALUE;
        Map.Entry<String, String> replacementLast = null;

        for (Map.Entry<String, String> replacement : digitMapping.entrySet()) {
            int indexLastFound = line.lastIndexOf(replacement.getKey());
            if(indexLastFound >= 0 && indexLastFound > indexLast) {
                indexLast = indexLastFound;
                replacementLast = replacement;
            }
        }

        if(replacementLast != null) {
            line = line.substring(0, indexLast) +
                    replacementLast.getValue() +
                    line.substring(indexLast + replacementLast.getKey().length());
        }

        return line;
    }

    public static Map<String, String> digitMapping = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9"
    );
}
