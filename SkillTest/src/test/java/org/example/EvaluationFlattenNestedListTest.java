package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationFlattenNestedListTest {
    @Test
    void testEmptyList() {
        List<?> input = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, Evaluation.flattenNestedList(input));
    }

    @Test
    void testSingleLevelList() {
        List<?> input = Arrays.asList(1, 2, 3);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertEquals(expected, Evaluation.flattenNestedList(input));
    }

    @Test
    void testTwoLevelList() {
        List<?> input = Arrays.asList(1, Arrays.asList(2, 3), 4);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4);
        assertEquals(expected, Evaluation.flattenNestedList(input));
    }

    @Test
    void testDeeplyNestedList() {
        List<?> input = Arrays.asList(1, Arrays.asList(2, Arrays.asList(3, Arrays.asList(4))), 5);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(expected, Evaluation.flattenNestedList(input));
    }

    @Test
    void testListWithEmptyLists() {
        List<?> input = Arrays.asList(1, new ArrayList<>(), Arrays.asList(2, new ArrayList<>()), 3);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertEquals(expected, Evaluation.flattenNestedList(input));
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.flattenNestedList(null));
    }

    @Test
    void testListWithInvalidElement() {
        List<?> input = Arrays.asList(1, "string", 2);
        assertThrows(IllegalArgumentException.class, () -> Evaluation.flattenNestedList(input));
    }
}

