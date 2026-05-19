package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the matrixMultiply method in the Evaluation class.
 */
public class EvaluationMatrixMultiplyTest {

    @Test
    void testBasicMatrixMultiplication() {
        // Simple 2x2 matrices
        int[][] matrix1 = {
            {1, 2},
            {3, 4}
        };

        int[][] matrix2 = {
            {5, 6},
            {7, 8}
        };

        int[][] expected = {
            {19, 22},
            {43, 50}
        };

        int[][] result = Evaluation.matrixMultiply(matrix1, matrix2);
        assertArrayEquals(expected, result);
    }

    @Test
    void testNonSquareMatrixMultiplication() {
        // 2x3 matrix multiplied by 3x2 matrix
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int[][] matrix2 = {
            {7, 8},
            {9, 10},
            {11, 12}
        };

        int[][] expected = {
            {58, 64},
            {139, 154}
        };

        int[][] result = Evaluation.matrixMultiply(matrix1, matrix2);
        assertArrayEquals(expected, result);
    }

    @Test
    void testMatrixWithZeros() {
        // Matrix with zeros
        int[][] matrix1 = {
            {1, 0},
            {0, 1}
        };

        int[][] matrix2 = {
            {5, 6},
            {7, 8}
        };

        int[][] expected = {
            {5, 6},
            {7, 8}
        };

        int[][] result = Evaluation.matrixMultiply(matrix1, matrix2);
        assertArrayEquals(expected, result);
    }

    @Test
    void testIdentityMatrix() {
        // Multiplication with identity matrix should return the original matrix
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int[][] identity = {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };

        int[][] result = Evaluation.matrixMultiply(matrix, identity);
        assertArrayEquals(matrix, result);
    }

    @Test
    void testLargerMatrices() {
        // Larger matrices (3x3)
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        int[][] matrix2 = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
        };

        int[][] expected = {
            {30, 24, 18},
            {84, 69, 54},
            {138, 114, 90}
        };

        int[][] result = Evaluation.matrixMultiply(matrix1, matrix2);
        assertArrayEquals(expected, result);
    }

    @Test
    void testNullMatrices() {
        // Test null matrices
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Evaluation.matrixMultiply(null, new int[][]{{1, 2}, {3, 4}});
        });
        assertTrue(exception.getMessage().contains("cannot be null"));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Evaluation.matrixMultiply(new int[][]{{1, 2}, {3, 4}}, null);
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
    }

    @Test
    void testEmptyMatrices() {
        // Test empty matrices
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Evaluation.matrixMultiply(new int[][]{}, new int[][]{{1, 2}, {3, 4}});
        });
        assertTrue(exception.getMessage().contains("cannot be empty"));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Evaluation.matrixMultiply(new int[][]{{1, 2}, {3, 4}}, new int[][]{});
        });
        assertTrue(exception.getMessage().contains("cannot be empty"));
    }

    @Test
    void testIrregularMatrices() {
        // Test matrices with irregular row lengths
        int[][] irregular = {
            {1, 2},
            {3, 4, 5} // This row has different length
        };

        int[][] regular = {
            {1, 2},
            {3, 4}
        };

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Evaluation.matrixMultiply(irregular, regular);
        });
        assertTrue(exception.getMessage().contains("same length"));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Evaluation.matrixMultiply(regular, irregular);
        });
        assertTrue(exception.getMessage().contains("same length"));
    }

    @Test
    void testIncompatibleDimensions() {
        // Test matrices with incompatible dimensions for multiplication
        int[][] matrix1 = {
            {1, 2},
            {3, 4}
        };

        int[][] matrix2 = {
            {5, 6, 7},
            {8, 9, 10},
            {11, 12, 13}
        };

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Evaluation.matrixMultiply(matrix1, matrix2);
        });
        assertTrue(exception.getMessage().contains("Matrix multiplication not possible"));
    }

    @Test
    void testSingleElementMatrices() {
        // Test single element matrices
        int[][] matrix1 = {{5}};
        int[][] matrix2 = {{7}};
        int[][] expected = {{35}};

        int[][] result = Evaluation.matrixMultiply(matrix1, matrix2);
        assertArrayEquals(expected, result);
    }
}
