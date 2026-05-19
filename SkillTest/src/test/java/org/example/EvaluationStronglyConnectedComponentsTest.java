package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationStronglyConnectedComponentsTest {
    @Test
    void testSingleSCC() {
        // 0 -> 1 -> 2 -> 0 (cycle)
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2),
            Arrays.asList(0)
        );
        List<List<Integer>> sccs = Evaluation.findStronglyConnectedComponents(graph);
        assertEquals(1, sccs.size());
        Set<Integer> expected = new HashSet<>(Arrays.asList(0,1,2));
        assertEquals(expected, new HashSet<>(sccs.get(0)));
    }

    @Test
    void testMultipleSCCs() {
        // 0 -> 1, 1 -> 2, 2 -> 0, 1 -> 3
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),
            Arrays.asList(2,3),
            Arrays.asList(0),
            Collections.emptyList()
        );
        List<List<Integer>> sccs = Evaluation.findStronglyConnectedComponents(graph);
        assertEquals(2, sccs.size());
        Set<Set<Integer>> expected = new HashSet<>();
        expected.add(new HashSet<>(Arrays.asList(0,1,2)));
        expected.add(new HashSet<>(Collections.singletonList(3)));
        Set<Set<Integer>> actual = new HashSet<>();
        for (List<Integer> scc : sccs) actual.add(new HashSet<>(scc));
        assertEquals(expected, actual);
    }

    @Test
    void testDisconnectedGraph() {
        // 0->1, 2->3, 3->2
        List<List<Integer>> graph = Arrays.asList(
            Arrays.asList(1),
            Collections.emptyList(),
            Arrays.asList(3),
            Arrays.asList(2)
        );
        List<List<Integer>> sccs = Evaluation.findStronglyConnectedComponents(graph);
        Set<Set<Integer>> expected = new HashSet<>();
        expected.add(new HashSet<>(Arrays.asList(0)));
        expected.add(new HashSet<>(Arrays.asList(1)));
        expected.add(new HashSet<>(Arrays.asList(2,3)));
        Set<Set<Integer>> actual = new HashSet<>();
        for (List<Integer> scc : sccs) actual.add(new HashSet<>(scc));
        assertEquals(expected, actual);
    }

    @Test
    void testNoEdges() {
        // 3 nodes, no edges
        List<List<Integer>> graph = Arrays.asList(
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList()
        );
        List<List<Integer>> sccs = Evaluation.findStronglyConnectedComponents(graph);
        Set<Set<Integer>> expected = new HashSet<>();
        expected.add(new HashSet<>(Arrays.asList(0)));
        expected.add(new HashSet<>(Arrays.asList(1)));
        expected.add(new HashSet<>(Arrays.asList(2)));
        Set<Set<Integer>> actual = new HashSet<>();
        for (List<Integer> scc : sccs) actual.add(new HashSet<>(scc));
        assertEquals(expected, actual);
    }

    @Test
    void testNullGraph() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.findStronglyConnectedComponents(null));
    }
}

