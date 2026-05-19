package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationBalancedParenthesesTest {
    @Test
    void testZeroPairs() {
        List<String> result = Evaluation.generateBalancedParentheses(0);
        assertEquals(Collections.singletonList(""), result);
    }

    @Test
    void testOnePair() {
        List<String> result = Evaluation.generateBalancedParentheses(1);
        assertEquals(Collections.singletonList("()"), result);
    }

    @Test
    void testTwoPairs() {
        List<String> expected = Arrays.asList("(())", "()()");
        List<String> result = Evaluation.generateBalancedParentheses(2);
        assertEquals(new HashSet<>(expected), new HashSet<>(result));
        assertEquals(expected.size(), result.size());
    }

    @Test
    void testThreePairs() {
        Set<String> expected = new HashSet<>(Arrays.asList(
            "((()))", "(()())", "(())()", "()(())", "()()()"
        ));
        List<String> result = Evaluation.generateBalancedParentheses(3);
        assertEquals(expected, new HashSet<>(result));
        assertEquals(expected.size(), result.size());
    }

    @Test
    void testNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> Evaluation.generateBalancedParentheses(-1));
    }

    @Test
    void testResultCount() {
        // Catalan numbers: 1, 1, 2, 5, 14 for n=0..4
        assertEquals(1, Evaluation.generateBalancedParentheses(0).size());
        assertEquals(1, Evaluation.generateBalancedParentheses(1).size());
        assertEquals(2, Evaluation.generateBalancedParentheses(2).size());
        assertEquals(5, Evaluation.generateBalancedParentheses(3).size());
        assertEquals(14, Evaluation.generateBalancedParentheses(4).size());
    }
}

