package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationTTLCacheTest {
    @Test
    void testPutAndGet() {
        Evaluation.TTLCache<String, Integer> cache = new Evaluation.TTLCache<>(1000);
        cache.put("a", 1);
        assertEquals(1, cache.get("a"));
    }

    @Test
    void testExpiration() throws InterruptedException {
        Evaluation.TTLCache<String, String> cache = new Evaluation.TTLCache<>(50);
        cache.put("x", "y");
        Thread.sleep(60);
        assertNull(cache.get("x"));
    }

    @Test
    void testCustomTTL() throws InterruptedException {
        Evaluation.TTLCache<String, String> cache = new Evaluation.TTLCache<>(1000);
        cache.put("foo", "bar", 50);
        Thread.sleep(60);
        assertNull(cache.get("foo"));
    }

    @Test
    void testRemove() {
        Evaluation.TTLCache<String, Integer> cache = new Evaluation.TTLCache<>(1000);
        cache.put("a", 1);
        cache.remove("a");
        assertNull(cache.get("a"));
    }

    @Test
    void testSizeAndCleanup() throws InterruptedException {
        Evaluation.TTLCache<String, Integer> cache = new Evaluation.TTLCache<>(50);
        cache.put("a", 1);
        cache.put("b", 2);
        Thread.sleep(60);
        cache.cleanup();
        assertEquals(0, cache.size());
    }

    @Test
    void testOverwriteValueAndTTL() throws InterruptedException {
        Evaluation.TTLCache<String, String> cache = new Evaluation.TTLCache<>(1000);
        cache.put("k", "v1", 50);
        cache.put("k", "v2", 1000);
        Thread.sleep(60);
        assertEquals("v2", cache.get("k"));
    }

    @Test
    void testIllegalArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Evaluation.TTLCache<>(0));
        Evaluation.TTLCache<String, String> cache = new Evaluation.TTLCache<>(1000);
        assertThrows(IllegalArgumentException.class, () -> cache.put("a", "b", 0));
    }
}

