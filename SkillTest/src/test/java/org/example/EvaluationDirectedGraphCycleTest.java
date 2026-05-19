package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationDirectedGraphCycleTest {
    @Test
    void testNoCycle() {
        // 0 -> 1 -> 2
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2),
            Collections.emptyList()
        );
        assertFalse(Evaluation.hasCycleDirectedGraph(graph));
    }

    @Test
    void testSimpleCycle() {
        // 0 -> 1 -> 2 -> 0
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2),
            Arrays.asList(0)
        );
        assertTrue(Evaluation.hasCycleDirectedGraph(graph));
    }

    @Test
    void testSelfLoop() {
        // 0 -> 0
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(0)
        );
        assertTrue(Evaluation.hasCycleDirectedGraph(graph));
    }

    @Test
    void testDisconnectedGraphWithCycle() {
        // 0 -> 1, 2 -> 3 -> 2
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),
            Collections.emptyList(),
            Arrays.asList(3),
            Arrays.asList(2)
        );
        assertTrue(Evaluation.hasCycleDirectedGraph(graph));
    }

    @Test
    void testDisconnectedGraphNoCycle() {
        // 0 -> 1, 2 -> 3
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),
            Collections.emptyList(),
            Arrays.asList(3),
            Collections.emptyList()
        );
        assertFalse(Evaluation.hasCycleDirectedGraph(graph));
    }

    @Test
    void testEmptyGraph() {
        List<List<Integer>> graph = new ArrayList<>();
        assertFalse(Evaluation.hasCycleDirectedGraph(graph));
    }

    @Test
    void testNullGraph() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.hasCycleDirectedGraph(null));
    }
}

