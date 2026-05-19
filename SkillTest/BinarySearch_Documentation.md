# Binary Search Implementation and Test Cases

## Overview
This document describes the implementation of the Binary Search algorithm in Java and the corresponding unit tests to verify its correctness and performance.

## Binary Search Implementation
The `binarySearch` method is implemented in the `Evaluation` class:

```java
public static int binarySearch(List<Integer> arr, int target) {
    if (arr == null || arr.isEmpty()) {
        return -1;
    }
    
    int left = 0;
    int right = arr.size() - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2; // Avoids overflow compared to (left + right) / 2
        int midValue = arr.get(mid);
        
        if (midValue == target) {
            return mid; // Target found
        } else if (midValue < target) {
            left = mid + 1; // Search in right half
        } else {
            right = mid - 1; // Search in left half
        }
    }
    
    return -1; // Target not found
}
```

- **Input:** 
  - `arr`: A sorted list of integers (`List<Integer>`)
  - `target`: The integer value to search for (`int`)
- **Output:** 
  - Index of the target element if found
  - `-1` if the target is not found or the list is null or empty
- **Complexity:**
  - Time: O(log n) - The search space is halved in each iteration
  - Space: O(1) - Only constant extra space is used

## Algorithm Explanation
1. Start with two pointers: `left` (at the beginning of the array) and `right` (at the end).
2. While `left` <= `right`:
   - Calculate the middle index using `mid = left + (right - left) / 2` (to avoid integer overflow).
   - If the element at the middle index equals the target, return the middle index.
   - If the element is less than the target, discard the left half and continue searching in the right half.
   - If the element is greater than the target, discard the right half and continue searching in the left half.
3. If the target is not found after the loop, return `-1`.

## Test Cases
Unit tests are provided in `EvaluationBinarySearchTest.java` using JUnit 5:

```java
class EvaluationBinarySearchTest {
    // Test cases with empty or null lists
    @Test
    void testBinarySearch_emptyList() { /* ... */ }
    @Test
    void testBinarySearch_nullList() { /* ... */ }
    
    // Test cases with the element present in the list
    @Test
    void testBinarySearch_elementPresent() { /* ... */ }
    @Test
    void testBinarySearch_firstElement() { /* ... */ }
    @Test
    void testBinarySearch_lastElement() { /* ... */ }
    
    // Test cases with the element not present in the list
    @Test
    void testBinarySearch_elementNotPresent() { /* ... */ }
    
    // Test cases for edge cases and performance
    @Test
    void testBinarySearch_duplicateElements() { /* ... */ }
    @Test
    void testBinarySearch_largeList() { /* ... */ }
}
```

### Test Coverage
- **Edge Cases:**
  - Empty list
  - Null list
  - Element at the beginning (first element)
  - Element at the end (last element)
  - Element not present in the list
- **Specific Cases:**
  - Duplicate elements (binary search will find one of them)
  - Large list (performance and correctness for a list with 1000 elements)

## How to Run
1. Ensure Maven is installed and available in your system PATH.
2. Run `mvn test -Dtest=org.example.EvaluationBinarySearchTest` to execute all binary search test cases.

## Conclusion
The binary search implementation is efficient (O(log n)) and thoroughly tested for various edge cases and input scenarios. All tests should pass if the environment is correctly set up. Binary search is particularly useful for large sorted data sets where linear search would be inefficient.
