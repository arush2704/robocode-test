package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationArticulationPointsTest {
    @Test
    void testSimpleGraph() {
        // 0-1-2, 1-3
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),        // 0
            Arrays.asList(0,2,3),    // 1
            Arrays.asList(1),        // 2
            Arrays.asList(1)         // 3
        );
        Set<Integer> expected = new HashSet<>(Collections.singletonList(1));
        assertEquals(expected, Evaluation.findArticulationPoints(graph));
    }

    @Test
    void testTwoArticulationPoints() {
        // 0-1-2-3, 1-4, 2-5
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),        // 0
            Arrays.asList(0,2,4),    // 1
            Arrays.asList(1,3,5),    // 2
            Arrays.asList(2),        // 3
            Arrays.asList(1),        // 4
            Arrays.asList(2)         // 5
        );
        Set<Integer> expected = new HashSet<>(Arrays.asList(1,2));
        assertEquals(expected, Evaluation.findArticulationPoints(graph));
    }

    @Test
    void testNoArticulationPoints() {
        // Complete graph (triangle): 0-1-2-0
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1,2),
            Arrays.asList(0,2),
            Arrays.asList(0,1)
        );
        assertTrue(Evaluation.findArticulationPoints(graph).isEmpty());
    }

    @Test
    void testDisconnectedGraph() {
        // Two components: 0-1-2 and 3-4
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),        // 0
            Arrays.asList(0,2),      // 1
            Arrays.asList(1),        // 2
            Arrays.asList(4),        // 3
            Arrays.asList(3)         // 4
        );
        Set<Integer> expected = new HashSet<>(Collections.singletonList(1));
        assertEquals(expected, Evaluation.findArticulationPoints(graph));
    }

    @Test
    void testSingleNode() {
        List<List<Integer>> graph = Arrays.asList(Collections.emptyList());
        assertTrue(Evaluation.findArticulationPoints(graph).isEmpty());
    }

    @Test
    void testNullGraph() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.findArticulationPoints(null));
    }
}

