package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationActivitySelectionTest {
    @Test
    void testTypicalCase() {
        int[] start = {1, 3, 0, 5, 8, 5};
        int[] end =   {2, 4, 6, 7, 9, 9};
        List<Integer> result = Evaluation.activitySelection(start, end);
        // One possible correct answer: [0, 1, 3, 4]
        List<Integer> expected = Arrays.asList(0, 1, 3, 4);
        assertEquals(expected, result);
    }

    @Test
    void testAllOverlapping() {
        int[] start = {1, 1, 1};
        int[] end =   {4, 4, 4};
        List<Integer> result = Evaluation.activitySelection(start, end);
        // Only one activity can be selected
        assertEquals(1, result.size());
    }

    @Test
    void testAllNonOverlapping() {
        int[] start = {1, 3, 5};
        int[] end =   {2, 4, 6};
        List<Integer> result = Evaluation.activitySelection(start, end);
        // All activities can be selected
        List<Integer> expected = Arrays.asList(0, 1, 2);
        assertEquals(expected, result);
    }

    @Test
    void testSingleActivity() {
        int[] start = {2};
        int[] end =   {3};
        List<Integer> result = Evaluation.activitySelection(start, end);
        assertEquals(Collections.singletonList(0), result);
    }

    @Test
    void testEmptyInput() {
        int[] start = {};
        int[] end =   {};
        assertThrows(IllegalArgumentException.class, () -> Evaluation.activitySelection(start, end));
    }

    @Test
    void testMismatchedInput() {
        int[] start = {1, 2};
        int[] end =   {3};
        assertThrows(IllegalArgumentException.class, () -> Evaluation.activitySelection(start, end));
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.activitySelection(null, null));
    }

    @Test
    void testOrderPreservation() {
        int[] start = {4, 2, 1};
        int[] end =   {5, 3, 2};
        List<Integer> result = Evaluation.activitySelection(start, end);
        // Should select the earliest finishing activities
        List<Integer> expected = Arrays.asList(2, 1, 0);
        assertEquals(expected, result);
    }
}

