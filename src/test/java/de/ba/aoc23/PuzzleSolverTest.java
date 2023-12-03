package de.ba.aoc23;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class PuzzleSolverTest {

    public abstract String getExpectedResult();

    @SuppressWarnings("unchecked")
    @Test
    public void solveExample() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = getClass().getName();
        Class<? extends PuzzleSolver> puzzleSolverClass = (Class<? extends PuzzleSolver>) Class.forName(className.substring(0, className.length() - 4));
        PuzzleSolver puzzleSolver = puzzleSolverClass.getDeclaredConstructor().newInstance();

        assertEquals(getExpectedResult(), puzzleSolver.solveByFile(getClass().getSimpleName()));
    }

}
