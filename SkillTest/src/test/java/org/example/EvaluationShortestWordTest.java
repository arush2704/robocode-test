package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the findShortestWord method in the Evaluation class.
 */
public class EvaluationShortestWordTest {

    @Test
    void testFindShortestWordNormal() {
        // Normal cases with a clear shortest word
        assertEquals("a", Evaluation.findShortestWord("This is a simple test"));
        assertEquals("hi", Evaluation.findShortestWord("hi there how are you doing"));
        assertEquals("cat", Evaluation.findShortestWord("The cat sat on the mat"));
    }

    @Test
    void testFindShortestWordMultipleSameLength() {
        // Cases where multiple words have the same shortest length
        // Should return the first occurrence
        assertEquals("is", Evaluation.findShortestWord("This is an example"));
        assertEquals("a", Evaluation.findShortestWord("a b c d e f"));
    }

    @Test
    void testFindShortestWordSpecialCases() {
        // Special cases with punctuation and mixed case
        assertEquals("a", Evaluation.findShortestWord("Hello, a world!"));
        assertEquals("in", Evaluation.findShortestWord("Words in UPPERCASE and lowercase"));
    }

    @Test
    void testFindShortestWordExtraSpaces() {
        // Cases with extra spaces
        assertEquals("a", Evaluation.findShortestWord("  This   is  a  test  "));
        assertEquals("to", Evaluation.findShortestWord("How  to   handle    multiple     spaces"));
    }

    @Test
    void testFindShortestWordSingleWord() {
        // Case with a single word
        assertEquals("Hello", Evaluation.findShortestWord("Hello"));
        assertEquals("X", Evaluation.findShortestWord("X"));
    }

    @Test
    void testFindShortestWordEmptyAndNull() {
        // Edge cases: empty and null inputs
        assertNull(Evaluation.findShortestWord(""));
        assertNull(Evaluation.findShortestWord("   "));
        assertNull(Evaluation.findShortestWord(null));
    }
}
