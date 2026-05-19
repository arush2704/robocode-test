package org.example;

import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationPriorityQueueHeapTest {
    @Test
    void testMinHeapOrder() {
        Evaluation.PriorityQueueHeap<Integer> pq = new Evaluation.PriorityQueueHeap<>(Comparator.naturalOrder());
        pq.add(5); pq.add(1); pq.add(3);
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(5, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void testMaxHeapOrder() {
        Evaluation.PriorityQueueHeap<Integer> pq = new Evaluation.PriorityQueueHeap<>(Comparator.reverseOrder());
        pq.add(2); pq.add(7); pq.add(4);
        assertEquals(7, pq.poll());
        assertEquals(4, pq.poll());
        assertEquals(2, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void testPeek() {
        Evaluation.PriorityQueueHeap<Integer> pq = new Evaluation.PriorityQueueHeap<>(Comparator.naturalOrder());
        pq.add(10); pq.add(2);
        assertEquals(2, pq.peek());
        assertEquals(2, pq.poll());
        assertEquals(10, pq.peek());
    }

    @Test
    void testSizeAndIsEmpty() {
        Evaluation.PriorityQueueHeap<String> pq = new Evaluation.PriorityQueueHeap<>(Comparator.naturalOrder());
        assertTrue(pq.isEmpty());
        pq.add("a");
        assertEquals(1, pq.size());
        pq.add("b");
        assertEquals(2, pq.size());
        pq.poll();
        assertEquals(1, pq.size());
        pq.poll();
        assertTrue(pq.isEmpty());
    }

    @Test
    void testExceptionOnEmpty() {
        Evaluation.PriorityQueueHeap<Integer> pq = new Evaluation.PriorityQueueHeap<>(Comparator.naturalOrder());
        assertThrows(java.util.NoSuchElementException.class, pq::poll);
        assertThrows(java.util.NoSuchElementException.class, pq::peek);
    }

    @Test
    void testNullComparator() {
        assertThrows(IllegalArgumentException.class, () -> new Evaluation.PriorityQueueHeap<>(null));
    }
}

