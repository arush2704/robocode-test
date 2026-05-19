# Recursive Factorial Implementation and Test Cases

## Overview
This document describes the recursive implementation of the factorial function in Java and the corresponding unit tests to verify its correctness and robustness.

## Factorial Implementation
The `factorial` method is implemented in the `Evaluation` class:

```
public static long factorial(int n) {
    if (n < 0) {
        throw new IllegalArgumentException("Factorial is not defined for negative numbers");
    }
    if (n == 0 || n == 1) {
        return 1;
    }
    return n * factorial(n - 1);
}
```

- **Input:** Non-negative integer (`int n`)
- **Output:** Factorial of `n` as a `long`
- **Complexity:**
  - Time: O(n)
  - Space: O(n) (due to recursion stack)
- **Validation:** Throws `IllegalArgumentException` for negative input.

## Test Cases
Unit tests are provided in `EvaluationFactorialTest.java` using JUnit 5:

```
class EvaluationFactorialTest {
    @Test
    void testFactorial_zero() {
        assertEquals(1, Evaluation.factorial(0));
    }

    @Test
    void testFactorial_one() {
        assertEquals(1, Evaluation.factorial(1));
    }

    @Test
    void testFactorial_smallNumber() {
        assertEquals(120, Evaluation.factorial(5));
    }

    @Test
    void testFactorial_largeNumber() {
        assertEquals(3628800, Evaluation.factorial(10));
    }

    @Test
    void testFactorial_negativeNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Evaluation.factorial(-1));
        assertEquals("Factorial is not defined for negative numbers", exception.getMessage());
    }
}
```

### Test Coverage
- **Zero and one:** Verifies base cases.
- **Small and large numbers:** Verifies correctness for typical inputs.
- **Negative input:** Verifies exception handling for invalid input.

## How to Run
1. Ensure Maven is installed and available in your system PATH.
2. Run `mvn test` in the project directory to execute all test cases.

## Conclusion
The recursive factorial implementation is robust and thoroughly tested for various edge cases and input scenarios. All tests should pass if the environment is correctly set up.

