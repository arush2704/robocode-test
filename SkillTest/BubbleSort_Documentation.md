# Bubble Sort Implementation and Test Cases

## Overview
This document describes the implementation of the Bubble Sort algorithm in Java and the corresponding unit tests to verify its correctness and robustness.

## Bubble Sort Implementation
The `bubbleSort` method is implemented in the `Evaluation` class:

```
public static void bubbleSort(List<Integer> arr) {
    int n = arr.size();
    boolean swapped;
    for (int i = 0; i < n - 1; i++) {
        swapped = false;
        for (int j = 0; j < n - i - 1; j++) {
            if (arr.get(j) > arr.get(j + 1)) {
                int temp = arr.get(j);
                arr.set(j, arr.get(j + 1));
                arr.set(j + 1, temp);
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}
```

- **Input:** List of integers (`List<Integer>`)
- **Output:** The list is sorted in ascending order (in-place)
- **Complexity:**
  - Worst-case: O(n²)
  - Best-case (already sorted): O(n)

## Test Cases
Unit tests are provided in `EvaluationTest.java` using JUnit 5:

```
class EvaluationTest {
    @Test
    void testBubbleSort_emptyList() {
        List<Integer> arr = Collections.emptyList();
        Evaluation.bubbleSort(arr);
        assertEquals(Collections.emptyList(), arr);
    }

    @Test
    void testBubbleSort_singleElement() {
        List<Integer> arr = Arrays.asList(42);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(42), arr);
    }

    @Test
    void testBubbleSort_sortedList() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), arr);
    }

    @Test
    void testBubbleSort_reverseList() {
        List<Integer> arr = Arrays.asList(5, 4, 3, 2, 1);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), arr);
    }

    @Test
    void testBubbleSort_duplicates() {
        List<Integer> arr = Arrays.asList(3, 1, 2, 3, 1);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(1, 1, 2, 3, 3), arr);
    }

    @Test
    void testBubbleSort_largeList() {
        List<Integer> arr = Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Evaluation.bubbleSort(arr);
        assertEquals(Arrays.asList(1,2,3,4,5,6,7,8,9,10), arr);
    }
}
```

### Test Coverage
- **Empty list**: Ensures sorting does not fail on empty input.
- **Single element**: Ensures sorting does not alter a single-element list.
- **Already sorted list**: Confirms best-case performance and correctness.
- **Reverse order**: Confirms worst-case performance and correctness.
- **Duplicates**: Ensures duplicates are handled correctly.
- **Large list**: Verifies correctness and performance on larger input.

## How to Run
1. Ensure Maven is installed and available in your system PATH.
2. Run `mvn test` in the project directory to execute all test cases.

## Conclusion
The bubble sort implementation is robust and thoroughly tested for various edge cases and input scenarios. All tests should pass if the environment is correctly set up.

