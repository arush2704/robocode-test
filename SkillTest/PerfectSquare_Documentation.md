# Perfect Square Checker Documentation

## Overview

This document describes a function that determines whether a given integer is a perfect square. A perfect square is an integer that is the square of another integer. For example, 16 is a perfect square because it's 4².

## Function Signature

```java
public static boolean isPerfectSquare(int number)
```

## Description

The `isPerfectSquare` function efficiently checks if an integer is a perfect square by using a binary search approach to find its square root. If the number has an exact integer square root, it is a perfect square.

## Parameters

- `number` (int): The integer to check. Must be a non-negative value.

## Return Value

- Returns `true` if the input is a perfect square.
- Returns `false` if the input is not a perfect square.

## Exceptions

- Throws `IllegalArgumentException` if the input is negative, as perfect squares are defined only for non-negative integers.

## Algorithm

The function uses a binary search approach to efficiently determine if a number is a perfect square:

1. First, check if the input is negative and throw an exception if it is.
2. Handle special cases: 0 and 1 are both perfect squares.
3. For values > 1, use binary search to find if there exists an integer whose square equals the input:
   - Set a search range from 1 to number/2 (as the square root cannot be larger than number/2 for values > 4)
   - For each iteration, compute the middle value and its square
   - If the square equals the input number, it's a perfect square
   - Otherwise, adjust the search range accordingly

This approach has a logarithmic time complexity, making it efficient for large numbers.

## Complexity Analysis

- **Time Complexity**: O(log n), where n is the input number. The binary search algorithm reduces the search space by half in each iteration.
- **Space Complexity**: O(1), as the algorithm uses a constant amount of extra space regardless of the input size.

## Usage Examples

```java
// Check if a number is a perfect square
boolean result = Evaluation.isPerfectSquare(16);  // true (16 = 4²)
boolean result = Evaluation.isPerfectSquare(25);  // true (25 = 5²)
boolean result = Evaluation.isPerfectSquare(10);  // false (10 is not a perfect square)

// Handling edge cases
boolean result = Evaluation.isPerfectSquare(0);   // true (0 = 0²)
boolean result = Evaluation.isPerfectSquare(1);   // true (1 = 1²)

// Handling negative numbers
try {
    Evaluation.isPerfectSquare(-4);  // Throws IllegalArgumentException
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());  // "Cannot check if negative number is a perfect square"
}
```

## Edge Cases

The function handles these edge cases:

1. **Negative Numbers**: Throws an exception, as perfect squares are defined only for non-negative integers.
2. **Zero**: Returns `true` because 0² = 0.
3. **One**: Returns `true` because 1² = 1.
4. **Large Numbers**: Uses `long` type internally to avoid potential integer overflow when calculating squares of large integers.
5. **Integer.MAX_VALUE**: Will correctly identify that it is not a perfect square.

## Test Cases

The function is thoroughly tested in `EvaluationPerfectSquareTest` with various test cases:

- Known perfect squares (0, 1, 4, 9, 16, 25, etc.)
- Non-perfect squares (2, 3, 5, 6, etc.)
- Larger perfect squares (10000, 40000, etc.)
- Larger non-perfect squares
- Negative numbers (to verify exception throwing)
- Edge cases (0, 1, Integer.MAX_VALUE)

## Mathematical Properties

A perfect square has these interesting properties:
1. The digital root (sum of digits repeatedly until a single digit) of a perfect square can only be 1, 4, 7, or 9
2. A perfect square has an even number of trailing zeros
3. A perfect square cannot end with 2, 3, 7, or 8

While our algorithm doesn't use these properties (as the binary search approach is more general and efficient), they could be used for pre-checks to quickly filter out non-perfect squares in some cases.

## Alternative Approaches

1. **Newton's Method**: Another efficient approach is to use Newton's method to find the square root. This approach has similar efficiency but might require more iterations in some cases.

2. **Square Root and Check**: Calculate the square root using `Math.sqrt()`, round it, and check if its square equals the original number. This method may have issues with floating-point precision.

```java
boolean isPerfectSquare(int number) {
    if (number < 0) return false;
    int sqrt = (int) Math.sqrt(number);
    return sqrt * sqrt == number;
}
```

3. **Integer Square Root**: For perfect squares, the integer square root will yield an exact result.

## Limitations

1. The function is limited to integers within the range of the `int` data type (up to approximately 2 billion).
2. While the function uses `long` internally to avoid overflow during calculations, extremely large integers near `Integer.MAX_VALUE` might still cause issues.

## Possible Extensions

1. Extend to support `long` input types for checking larger numbers
2. Add an option to return the square root when the number is a perfect square
3. Implement functionality to find the nearest perfect square to a given number
