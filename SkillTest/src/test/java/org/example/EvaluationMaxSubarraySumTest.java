package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationMaxSubarraySumTest {
    @Test
    void testAllPositive() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(15, Evaluation.maxSubarraySum(arr));
    }

    @Test
    void testAllNegative() {
        int[] arr = {-8, -3, -6, -2, -5, -4};
        assertEquals(-2, Evaluation.maxSubarraySum(arr));
    }

    @Test
    void testMixedNumbers() {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        assertEquals(6, Evaluation.maxSubarraySum(arr)); // [4, -1, 2, 1]
    }

    @Test
    void testSingleElement() {
        int[] arr = {7};
        assertEquals(7, Evaluation.maxSubarraySum(arr));
    }

    @Test
    void testSingleNegativeElement() {
        int[] arr = {-7};
        assertEquals(-7, Evaluation.maxSubarraySum(arr));
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        assertThrows(IllegalArgumentException.class, () -> Evaluation.maxSubarraySum(arr));
    }

    @Test
    void testNullArray() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.maxSubarraySum(null));
    }

    @Test
    void testAllZeros() {
        int[] arr = {0, 0, 0, 0};
        assertEquals(0, Evaluation.maxSubarraySum(arr));
    }
}

