package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationTest {
    @Test
    void testBubbleSort_emptyList() {
        List<Integer> arr = Collections.emptyList();
        Evaluation.bubbleSort(arr);
        assertEquals(Collections.emptyList(), arr);
    }

    @Test
    void testBubbleSort_singleElement() {
        List<Integer> arr = Arrays.asList(42);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(42), arr);
    }

    @Test
    void testBubbleSort_sortedList() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), arr);
    }

    @Test
    void testBubbleSort_reverseList() {
        List<Integer> arr = Arrays.asList(5, 4, 3, 2, 1);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), arr);
    }

    @Test
    void testBubbleSort_duplicates() {
        List<Integer> arr = Arrays.asList(3, 1, 2, 3, 1);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(1, 1, 2, 3, 3), arr);
    }

    @Test
    void testBubbleSort_largeList() {
        List<Integer> arr = Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(1,2,3,4,5,6,7,8,9,10), arr);
    }
}
