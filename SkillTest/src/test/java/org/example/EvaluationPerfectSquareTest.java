package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the isPerfectSquare method in the Evaluation class.
 */
public class EvaluationPerfectSquareTest {

    @Test
    void testPerfectSquares() {
        // Test known perfect squares
        assertTrue(Evaluation.isPerfectSquare(0));
        assertTrue(Evaluation.isPerfectSquare(1));
        assertTrue(Evaluation.isPerfectSquare(4));
        assertTrue(Evaluation.isPerfectSquare(9));
        assertTrue(Evaluation.isPerfectSquare(16));
        assertTrue(Evaluation.isPerfectSquare(25));
        assertTrue(Evaluation.isPerfectSquare(36));
        assertTrue(Evaluation.isPerfectSquare(49));
        assertTrue(Evaluation.isPerfectSquare(64));
        assertTrue(Evaluation.isPerfectSquare(81));
        assertTrue(Evaluation.isPerfectSquare(100));
    }

    @Test
    void testNonPerfectSquares() {
        // Test numbers that are not perfect squares
        assertFalse(Evaluation.isPerfectSquare(2));
        assertFalse(Evaluation.isPerfectSquare(3));
        assertFalse(Evaluation.isPerfectSquare(5));
        assertFalse(Evaluation.isPerfectSquare(6));
        assertFalse(Evaluation.isPerfectSquare(7));
        assertFalse(Evaluation.isPerfectSquare(8));
        assertFalse(Evaluation.isPerfectSquare(10));
        assertFalse(Evaluation.isPerfectSquare(99));
    }

    @Test
    void testLargerPerfectSquares() {
        // Test larger perfect squares
        assertTrue(Evaluation.isPerfectSquare(10000));  // 100²
        assertTrue(Evaluation.isPerfectSquare(40000));  // 200²
        assertTrue(Evaluation.isPerfectSquare(160000)); // 400²
        assertTrue(Evaluation.isPerfectSquare(1000000)); // 1000²
    }

    @Test
    void testLargerNonPerfectSquares() {
        // Test larger non-perfect squares
        assertFalse(Evaluation.isPerfectSquare(10001));
        assertFalse(Evaluation.isPerfectSquare(40001));
        assertFalse(Evaluation.isPerfectSquare(999999));
    }

    @Test
    void testNegativeNumbers() {
        // Test that negative numbers throw an IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class,
                                          () -> Evaluation.isPerfectSquare(-1));
        assertEquals("Cannot check if negative number is a perfect square", exception.getMessage());

        assertThrows(IllegalArgumentException.class, () -> Evaluation.isPerfectSquare(-4));
        assertThrows(IllegalArgumentException.class, () -> Evaluation.isPerfectSquare(-100));
    }

    @Test
    void testEdgeCases() {
        // Test edge cases
        assertTrue(Evaluation.isPerfectSquare(0));  // 0 is 0²
        assertTrue(Evaluation.isPerfectSquare(1));  // 1 is 1²

        // Test Integer.MAX_VALUE (not a perfect square)
        assertFalse(Evaluation.isPerfectSquare(Integer.MAX_VALUE));
    }
}
