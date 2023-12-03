package de.ba.aoc23;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;


public abstract class PuzzleSolver {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        PuzzleSolver puzzleSolver = (PuzzleSolver) Class.forName(args[0]).getConstructor().newInstance();
        String className = puzzleSolver.getClass().getSimpleName();
        String fileName = className.substring(0, className.length() - 2);
        String result = puzzleSolver.solveByFile(fileName);

        System.out.println("-----------------------");
        System.out.println("---- Puzzle Result ----");
        System.out.println();
        System.out.println(result);
        System.out.println();
        System.out.println("-- Puzzle Result End --");
        System.out.println("-----------------------");
    }

    public String solveByFile(String fileName) {
        try {
            return solve(Files.lines(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).toURI())));
        } catch (IOException | URISyntaxException e) {
            System.err.println("Cannot read file: " + fileName);
            System.exit(0);
        }

        return null;
    }

    protected abstract String solve(Stream<String> linesAsStream);
}

