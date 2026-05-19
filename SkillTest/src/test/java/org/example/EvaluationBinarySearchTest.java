package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationBinarySearchTest {
    @Test
    void testBinarySearch_emptyList() {
        List<Integer> arr = Collections.emptyList();
        assertEquals(-1, Evaluation.binarySearch(arr, 5));
    }

    @Test
    void testBinarySearch_nullList() {
        assertEquals(-1, Evaluation.binarySearch(null, 5));
    }

    @Test
    void testBinarySearch_elementPresent() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertEquals(4, Evaluation.binarySearch(arr, 5)); // Element 5 is at index 4
    }

    @Test
    void testBinarySearch_elementNotPresent() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5, 7, 8, 9, 10);
        assertEquals(-1, Evaluation.binarySearch(arr, 6)); // Element 6 is not in the list
    }

    @Test
    void testBinarySearch_firstElement() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(0, Evaluation.binarySearch(arr, 1)); // Element 1 is at index 0
    }

    @Test
    void testBinarySearch_lastElement() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(4, Evaluation.binarySearch(arr, 5)); // Element 5 is at index 4
    }

    @Test
    void testBinarySearch_duplicateElements() {
        List<Integer> arr = Arrays.asList(1, 2, 2, 2, 3, 4, 5);
        // Binary search might return any index where the target is found
        // This test verifies that some instance of the target is found
        int result = Evaluation.binarySearch(arr, 2);
        assertTrue(result == 1 || result == 2 || result == 3);
    }

    @Test
    void testBinarySearch_largeList() {
        // Create a large sorted list
        Integer[] array = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            array[i] = i * 2; // Even numbers from 0 to 1998
        }
        List<Integer> arr = Arrays.asList(array);

        // Search for element 500
        assertEquals(250, Evaluation.binarySearch(arr, 500)); // 500 is at index 250

        // Search for element not in the list
        assertEquals(-1, Evaluation.binarySearch(arr, 501)); // 501 is not in the list
    }
}
