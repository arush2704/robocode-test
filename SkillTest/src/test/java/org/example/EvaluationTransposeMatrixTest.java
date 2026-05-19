package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationTransposeMatrixTest {
    @Test
    void testSquareMatrix() {
        int[][] input = {
            {1, 2},
            {3, 4}
        };
        int[][] expected = {
            {1, 3},
            {2, 4}
        };
        assertArrayEquals(expected, Evaluation.transposeMatrix(input));
    }

    @Test
    void testRectangularMatrix() {
        int[][] input = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int[][] expected = {
            {1, 4},
            {2, 5},
            {3, 6}
        };
        assertArrayEquals(expected, Evaluation.transposeMatrix(input));
    }

    @Test
    void testSingleRow() {
        int[][] input = {
            {7, 8, 9}
        };
        int[][] expected = {
            {7},
            {8},
            {9}
        };
        assertArrayEquals(expected, Evaluation.transposeMatrix(input));
    }

    @Test
    void testSingleColumn() {
        int[][] input = {
            {5},
            {6},
            {7}
        };
        int[][] expected = {
            {5, 6, 7}
        };
        assertArrayEquals(expected, Evaluation.transposeMatrix(input));
    }

    @Test
    void testEmptyMatrix() {
        int[][] input = {};
        assertThrows(IllegalArgumentException.class, () -> Evaluation.transposeMatrix(input));
    }

    @Test
    void testNullMatrix() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.transposeMatrix(null));
    }

    @Test
    void testJaggedMatrix() {
        int[][] input = {
            {1, 2},
            {3}
        };
        assertThrows(IllegalArgumentException.class, () -> Evaluation.transposeMatrix(input));
    }
}

