package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationFactorialTest {
    @Test
    void testFactorial_zero() {
        assertEquals(1, Evaluation.factorial(0));
    }

    @Test
    void testFactorial_one() {
        assertEquals(1, Evaluation.factorial(1));
    }

    @Test
    void testFactorial_smallNumber() {
        assertEquals(120, Evaluation.factorial(5));
    }

    @Test
    void testFactorial_largeNumber() {
        assertEquals(3628800, Evaluation.factorial(10));
    }

    @Test
    void testFactorial_negativeNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Evaluation.factorial(-1));
        assertEquals("Factorial is not defined for negative numbers", exception.getMessage());
    }
}

