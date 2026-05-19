package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationWordLadderTest {
    @Test
    void testBasicCase() {
        String begin = "hit";
        String end = "cog";
        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog");
        assertEquals(5, Evaluation.wordLadderLength(begin, end, wordList));
    }

    @Test
    void testNoPossibleTransformation() {
        String begin = "hit";
        String end = "cog";
        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log");
        assertEquals(0, Evaluation.wordLadderLength(begin, end, wordList));
    }

    @Test
    void testBeginEqualsEnd() {
        String begin = "hit";
        String end = "hit";
        List<String> wordList = Arrays.asList("hit","hot","dot");
        assertEquals(1, Evaluation.wordLadderLength(begin, end, wordList));
    }

    @Test
    void testCaseSensitivity() {
        String begin = "Hit";
        String end = "Cog";
        List<String> wordList = Arrays.asList("Hot","Dot","Dog","Lot","Log","Cog");
        assertEquals(0, Evaluation.wordLadderLength(begin, end, wordList));
    }

    @Test
    void testNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.wordLadderLength(null, "a", Arrays.asList("a")));
        assertThrows(IllegalArgumentException.class, () -> Evaluation.wordLadderLength("a", null, Arrays.asList("a")));
        assertThrows(IllegalArgumentException.class, () -> Evaluation.wordLadderLength("a", "b", null));
    }

    @Test
    void testSingleLetterWords() {
        String begin = "a";
        String end = "c";
        List<String> wordList = Arrays.asList("a", "b", "c");
        assertEquals(2, Evaluation.wordLadderLength(begin, end, wordList));
    }

    @Test
    void testDisconnectedGraph() {
        String begin = "hit";
        String end = "cog";
        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog","abc","def");
        assertEquals(5, Evaluation.wordLadderLength(begin, end, wordList));
    }
}

