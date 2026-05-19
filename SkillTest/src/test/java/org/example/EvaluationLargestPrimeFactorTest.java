package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationLargestPrimeFactorTest {
    @Test
    void testSmallComposite() {
        assertEquals(5, Evaluation.largestPrimeFactor(10)); // 2 * 5
        assertEquals(7, Evaluation.largestPrimeFactor(14)); // 2 * 7
        assertEquals(3, Evaluation.largestPrimeFactor(9)); // 3 * 3
    }

    @Test
    void testPrimeNumber() {
        assertEquals(13, Evaluation.largestPrimeFactor(13));
        assertEquals(97, Evaluation.largestPrimeFactor(97));
    }

    @Test
    void testPowerOfTwo() {
        assertEquals(2, Evaluation.largestPrimeFactor(8)); // 2^3
        assertEquals(2, Evaluation.largestPrimeFactor(1024)); // 2^10
    }

    @Test
    void testLargeComposite() {
        assertEquals(29, Evaluation.largestPrimeFactor(13195)); // 5 * 7 * 13 * 29
        assertEquals(6857, Evaluation.largestPrimeFactor(600851475143L)); // Project Euler #3
    }

    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.largestPrimeFactor(1));
        assertThrows(IllegalArgumentException.class, () -> Evaluation.largestPrimeFactor(0));
        assertThrows(IllegalArgumentException.class, () -> Evaluation.largestPrimeFactor(-10));
    }
}

