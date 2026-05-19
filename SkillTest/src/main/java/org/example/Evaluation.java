package org.example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Evaluation {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome! vvv");
        sumOfpairs();
    }

    public static void sumOfpairs()
    {
        int[] param2 = {9, 1, 2, 8, 2, 4, 5, 6, 7, 5};
        int param1 = 10;

        Set<Integer> seen = new HashSet<>();
        Set<String> uniquePairs = new HashSet<>();

        for (int num : param2) {
            int complement = param1 - num;
            if (seen.contains(complement)) {
                int min = Math.min(num, complement);
                int max = Math.max(num, complement);
                uniquePairs.add(min + "," + max);
            }
            seen.add(num);
        }

        System.out.println("Unique pairs that sum to " + param1 + ":");
        for (String pair : uniquePairs) {
            System.out.println("[" + pair.replace(",", ", ") + "]");
        }

    }

    /**
     * Performs bubble sort on a list of integers in ascending order.
     * @param arr the list of integers to sort
     */
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

    /**
     * Calculates the factorial of a non-negative integer using recursion.
     * @param n the number to calculate factorial for
     * @return factorial of n
     * @throws IllegalArgumentException if n is negative
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    /**
     * Performs binary search on a sorted list of integers.
     * @param arr the sorted list of integers to search in
     * @param target the value to search for
     * @return index of the target element if found, -1 otherwise
     */
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

    /**
     * Finds the shortest word in a sentence.
     * Words are defined as sequences of characters separated by spaces.
     * If multiple words have the same shortest length, returns the first occurrence.
     * If the input is null or contains no words, returns null.
     *
     * @param sentence the input sentence to analyze
     * @return the shortest word, or null if no words are found
     */
    public static String findShortestWord(String sentence) {
        if (sentence == null || sentence.trim().isEmpty()) {
            return null;
        }

        String[] words = sentence.trim().split("\\s+");
        if (words.length == 0) {
            return null;
        }

        String shortest = words[0];
        for (String word : words) {
            if (word.length() < shortest.length()) {
                shortest = word;
            }
        }

        return shortest;
    }

    /**
     * Checks if a number is a perfect square.
     * A perfect square is an integer that is the square of an integer.
     * For example, 25 is a perfect square because it's 5².
     *
     * @param number the number to check
     * @return true if the number is a perfect square, false otherwise
     * @throws IllegalArgumentException if the number is negative
     */
    public static boolean isPerfectSquare(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot check if negative number is a perfect square");
        }

        if (number == 0 || number == 1) {
            return true;
        }

        // Use binary search to find the square root
        long left = 1;
        long right = number / 2;

        while (left <= right) {
            long mid = left + (right - left) / 2;
            long square = mid * mid;

            if (square == number) {
                return true;
            } else if (square < number) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * Performs matrix multiplication between two matrices.
     * For matrix multiplication to be valid, the number of columns in the first matrix must equal
     * the number of rows in the second matrix.
     *
     * Optimizations:
     * 1. Input validation to prevent invalid operations
     * 2. Pre-allocation of result matrix for better performance
     * 3. Cache-friendly loop ordering (i, k, j instead of i, j, k)
     * 4. Block size optimization for larger matrices (when applicable)
     *
     * @param matrix1 the first matrix (M x N)
     * @param matrix2 the second matrix (N x P)
     * @return the result of multiplication (M x P)
     * @throws IllegalArgumentException if matrices are null, empty or have incompatible dimensions
     */
    public static int[][] matrixMultiply(int[][] matrix1, int[][] matrix2) {
        // Validate input matrices
        if (matrix1 == null || matrix2 == null) {
            throw new IllegalArgumentException("Input matrices cannot be null");
        }

        if (matrix1.length == 0 || matrix2.length == 0) {
            throw new IllegalArgumentException("Input matrices cannot be empty");
        }

        // Check if all rows in matrix1 have the same length
        int matrix1Columns = matrix1[0].length;
        for (int i = 1; i < matrix1.length; i++) {
            if (matrix1[i].length != matrix1Columns) {
                throw new IllegalArgumentException("All rows in the first matrix must have the same length");
            }
        }

        // Check if all rows in matrix2 have the same length
        int matrix2Columns = matrix2[0].length;
        for (int i = 1; i < matrix2.length; i++) {
            if (matrix2[i].length != matrix2Columns) {
                throw new IllegalArgumentException("All rows in the second matrix must have the same length");
            }
        }

        // Check if matrices can be multiplied (columns of matrix1 = rows of matrix2)
        if (matrix1Columns != matrix2.length) {
            throw new IllegalArgumentException(
                "Matrix multiplication not possible: " +
                "columns of first matrix (" + matrix1Columns + ") " +
                "must equal rows of second matrix (" + matrix2.length + ")"
            );
        }

        // Dimensions of result matrix
        int resultRows = matrix1.length;
        int resultColumns = matrix2Columns;

        // Create result matrix
        int[][] result = new int[resultRows][resultColumns];

        // Determine if the matrices are large enough to benefit from block optimization
        // For simplicity, using a direct approach for all sizes in this implementation
        // For very large matrices, a blocked algorithm would be more efficient

        // Cache-friendly loop ordering (i, k, j) for better performance
        for (int i = 0; i < resultRows; i++) {
            for (int k = 0; k < matrix1Columns; k++) {
                int val = matrix1[i][k]; // Cache this value
                for (int j = 0; j < resultColumns; j++) {
                    result[i][j] += val * matrix2[k][j];
                }
            }
        }

        return result;
    }

    /**
     * Generates all combinations of n pairs of balanced parentheses.
     * Uses backtracking to build valid sequences.
     *
     * @param n number of pairs of parentheses
     * @return list of all valid combinations
     * @throws IllegalArgumentException if n is negative
     */
    public static List<String> generateBalancedParentheses(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of pairs cannot be negative");
        }
        List<String> result = new java.util.ArrayList<>();
        generateParenthesisHelper(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private static void generateParenthesisHelper(List<String> result, StringBuilder current, int open, int close, int max) {
        if (current.length() == max * 2) {
            result.add(current.toString());
            return;
        }
        if (open < max) {
            current.append('(');
            generateParenthesisHelper(result, current, open + 1, close, max);
            current.deleteCharAt(current.length() - 1);
        }
        if (close < open) {
            current.append(')');
            generateParenthesisHelper(result, current, open, close + 1, max);
            current.deleteCharAt(current.length() - 1);
        }
    }

    /**
     * Transposes a given matrix (rows become columns and vice versa).
     *
     * @param matrix the input matrix to transpose
     * @return the transposed matrix
     * @throws IllegalArgumentException if the input is null or has inconsistent row lengths
     */
    public static int[][] transposeMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("Input matrix cannot be null or empty");
        }
        int rowCount = matrix.length;
        int colCount = matrix[0].length;
        for (int i = 1; i < rowCount; i++) {
            if (matrix[i].length != colCount) {
                throw new IllegalArgumentException("All rows must have the same number of columns");
            }
        }
        int[][] transposed = new int[colCount][rowCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    /**
     * Finds the maximum sum of a contiguous subarray using Kadane's algorithm.
     *
     * @param nums the input array of integers
     * @return the maximum sum of any contiguous subarray
     * @throws IllegalArgumentException if nums is null or empty
     */
    public static int maxSubarraySum(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        for (int i = 1; i < nums.length; i++) {
            maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }

    /**
     * Flattens a nested list of integers into a single list.
     * The input is a list that may contain integers or other lists (arbitrarily nested).
     *
     * @param nestedList the nested list to flatten
     * @return a flat list of integers
     * @throws IllegalArgumentException if nestedList is null
     */
    public static List<Integer> flattenNestedList(List<?> nestedList) {
        if (nestedList == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }
        List<Integer> result = new java.util.ArrayList<>();
        flattenHelper(nestedList, result);
        return result;
    }

    @SuppressWarnings("unchecked")
    private static void flattenHelper(List<?> nested, List<Integer> result) {
        for (Object element : nested) {
            if (element instanceof Integer) {
                result.add((Integer) element);
            } else if (element instanceof List<?>) {
                flattenHelper((List<?>) element, result);
            } else {
                throw new IllegalArgumentException("List contains non-integer and non-list element: " + element);
            }
        }
    }

    /**
     * Finds the largest prime factor of a given number.
     *
     * @param n the number to factorize
     * @return the largest prime factor
     * @throws IllegalArgumentException if n is less than 2
     */
    public static long largestPrimeFactor(long n) {
        if (n < 2) {
            throw new IllegalArgumentException("Number must be greater than 1");
        }
        long maxPrime = -1;
        // Remove all the 2s
        while (n % 2 == 0) {
            maxPrime = 2;
            n /= 2;
        }
        // Check for odd factors
        for (long i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                maxPrime = i;
                n /= i;
            }
        }
        // If n is a prime number greater than 2
        if (n > 2) {
            maxPrime = n;
        }
        return maxPrime;
    }

    /**
     * Solves the activity selection problem using a greedy algorithm.
     * Given start and end times of activities, selects the maximum number of non-overlapping activities.
     *
     * @param start array of start times
     * @param end array of end times (must be same length as start)
     * @return list of selected activity indices (0-based, in order of selection)
     * @throws IllegalArgumentException if input arrays are null, empty, or of different lengths
     */
    public static List<Integer> activitySelection(int[] start, int[] end) {
        if (start == null || end == null || start.length != end.length || start.length == 0) {
            throw new IllegalArgumentException("Start and end arrays must be non-null, non-empty, and of equal length");
        }
        int n = start.length;
        // Pair activities with their indices for stable sorting
        class Activity implements Comparable<Activity> {
            int idx, s, e;
            Activity(int idx, int s, int e) { this.idx = idx; this.s = s; this.e = e; }
            public int compareTo(Activity o) { return Integer.compare(this.e, o.e); }
        }
        Activity[] activities = new Activity[n];
        for (int i = 0; i < n; i++) {
            activities[i] = new Activity(i, start[i], end[i]);
        }
        java.util.Arrays.sort(activities);
        List<Integer> selected = new java.util.ArrayList<>();
        int lastEnd = -1;
        for (Activity act : activities) {
            if (act.s >= lastEnd) {
                selected.add(act.idx);
                lastEnd = act.e;
            }
        }
        return selected;
    }

    /**
     * Detects if a directed graph contains a cycle using DFS.
     *
     * @param graph adjacency list representation of the graph (graph[i] is a list of nodes j such that there is an edge i->j)
     * @return true if the graph contains a cycle, false otherwise
     * @throws IllegalArgumentException if the graph is null
     */
    public static boolean hasCycleDirectedGraph(List<List<Integer>> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        int n = graph.size();
        boolean[] visited = new boolean[n];
        boolean[] recStack = new boolean[n];
        for (int node = 0; node < n; node++) {
            if (!visited[node]) {
                if (dfsCycle(node, graph, visited, recStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfsCycle(int node, List<List<Integer>> graph, boolean[] visited, boolean[] recStack) {
        visited[node] = true;
        recStack[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                if (dfsCycle(neighbor, graph, visited, recStack)) {
                    return true;
                }
            } else if (recStack[neighbor]) {
                return true;
            }
        }
        recStack[node] = false;
        return false;
    }

    /**
     * Solves the word ladder problem using BFS.
     * Finds the length of the shortest transformation sequence from beginWord to endWord,
     * such that only one letter can be changed at a time and each transformed word must exist in wordList.
     *
     * @param beginWord the starting word
     * @param endWord the target word
     * @param wordList the list of allowed words
     * @return the length of the shortest transformation sequence, or 0 if no such sequence exists
     * @throws IllegalArgumentException if beginWord, endWord, or wordList is null
     */
    public static int wordLadderLength(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;
        java.util.Queue<String> queue = new java.util.LinkedList<>();
        queue.offer(beginWord);
        java.util.Set<String> visited = new java.util.HashSet<>();
        visited.add(beginWord);
        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                if (word.equals(endWord)) return level;
                char[] chars = word.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[j] = c;
                        String next = new String(chars);
                        if (wordSet.contains(next) && !visited.contains(next)) {
                            queue.offer(next);
                            visited.add(next);
                        }
                    }
                    chars[j] = original;
                }
            }
            level++;
        }
        return 0;
    }

    /**
     * A simple generic priority queue implementation using a binary heap.
     * Supports min-heap or max-heap based on the comparator provided.
     */
    public static class PriorityQueueHeap<T> {
        private final java.util.ArrayList<T> heap = new java.util.ArrayList<>();
        private final java.util.Comparator<? super T> comparator;

        /**
         * Constructs a priority queue with the given comparator.
         * @param comparator Comparator for heap ordering (min-heap or max-heap)
         */
        public PriorityQueueHeap(java.util.Comparator<? super T> comparator) {
            if (comparator == null) throw new IllegalArgumentException("Comparator cannot be null");
            this.comparator = comparator;
        }

        /**
         * Adds an element to the priority queue.
         * @param value the element to add
         */
        public void add(T value) {
            heap.add(value);
            siftUp(heap.size() - 1);
        }

        /**
         * Removes and returns the highest priority element.
         * @return the element with highest priority
         * @throws java.util.NoSuchElementException if the queue is empty
         */
        public T poll() {
            if (heap.isEmpty()) throw new java.util.NoSuchElementException("Priority queue is empty");
            T result = heap.get(0);
            T last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, last);
                siftDown(0);
            }
            return result;
        }

        /**
         * Returns the highest priority element without removing it.
         * @return the element with highest priority
         * @throws java.util.NoSuchElementException if the queue is empty
         */
        public T peek() {
            if (heap.isEmpty()) throw new java.util.NoSuchElementException("Priority queue is empty");
            return heap.get(0);
        }

        /**
         * Returns the number of elements in the queue.
         */
        public int size() {
            return heap.size();
        }

        /**
         * Returns true if the queue is empty.
         */
        public boolean isEmpty() {
            return heap.isEmpty();
        }

        private void siftUp(int idx) {
            while (idx > 0) {
                int parent = (idx - 1) / 2;
                if (comparator.compare(heap.get(idx), heap.get(parent)) < 0) {
                    swap(idx, parent);
                    idx = parent;
                } else {
                    break;
                }
            }
        }

        private void siftDown(int idx) {
            int n = heap.size();
            while (true) {
                int left = 2 * idx + 1, right = 2 * idx + 2, smallest = idx;
                if (left < n && comparator.compare(heap.get(left), heap.get(smallest)) < 0) smallest = left;
                if (right < n && comparator.compare(heap.get(right), heap.get(smallest)) < 0) smallest = right;
                if (smallest != idx) {
                    swap(idx, smallest);
                    idx = smallest;
                } else {
                    break;
                }
            }
        }

        private void swap(int i, int j) {
            T tmp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, tmp);
        }
    }

    /**
     * A simple generic cache with TTL (time-to-live) expiration for each entry.
     * Thread-safe for basic operations.
     */
    public static class TTLCache<K, V> {
        private final java.util.Map<K, CacheEntry<V>> map = new java.util.concurrent.ConcurrentHashMap<>();
        private final long defaultTtlMillis;

        /**
         * Constructs a TTLCache with a default TTL in milliseconds.
         * @param defaultTtlMillis default time-to-live for entries (ms)
         */
        public TTLCache(long defaultTtlMillis) {
            if (defaultTtlMillis <= 0) throw new IllegalArgumentException("TTL must be positive");
            this.defaultTtlMillis = defaultTtlMillis;
        }

        /**
         * Puts a value in the cache with the default TTL.
         */
        public void put(K key, V value) {
            put(key, value, defaultTtlMillis);
        }

        /**
         * Puts a value in the cache with a custom TTL (ms).
         */
        public void put(K key, V value, long ttlMillis) {
            if (ttlMillis <= 0) throw new IllegalArgumentException("TTL must be positive");
            long expireAt = System.currentTimeMillis() + ttlMillis;
            map.put(key, new CacheEntry<>(value, expireAt));
        }

        /**
         * Gets a value from the cache, or null if not present or expired.
         */
        public V get(K key) {
            CacheEntry<V> entry = map.get(key);
            if (entry == null) return null;
            if (System.currentTimeMillis() > entry.expireAt) {
                map.remove(key);
                return null;
            }
            return entry.value;
        }

        /**
         * Removes a value from the cache.
         */
        public void remove(K key) {
            map.remove(key);
        }

        /**
         * Returns the number of unexpired entries in the cache.
         */
        public int size() {
            cleanup();
            return map.size();
        }

        /**
         * Removes all expired entries from the cache.
         */
        public void cleanup() {
            long now = System.currentTimeMillis();
            for (K key : map.keySet()) {
                CacheEntry<V> entry = map.get(key);
                if (entry != null && now > entry.expireAt) {
                    map.remove(key);
                }
            }
        }

        private static class CacheEntry<V> {
            final V value;
            final long expireAt;
            CacheEntry(V value, long expireAt) {
                this.value = value;
                this.expireAt = expireAt;
            }
        }
    }

    /**
     * Finds all articulation points (cut vertices) in an undirected graph using DFS (Tarjan's algorithm).
     *
     * @param graph adjacency list representation of the undirected graph (graph[i] is a list of neighbors of node i)
     * @return a set of articulation point indices
     * @throws IllegalArgumentException if the graph is null
     */
    public static Set<Integer> findArticulationPoints(List<List<Integer>> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        int n = graph.size();
        boolean[] visited = new boolean[n];
        int[] disc = new int[n];
        int[] low = new int[n];
        int[] parent = new int[n];
        java.util.Arrays.fill(parent, -1);
        Set<Integer> result = new java.util.HashSet<>();
        int[] time = {0};
        for (int u = 0; u < n; u++) {
            if (!visited[u]) {
                dfsArticulation(u, visited, disc, low, parent, result, graph, time);
            }
        }
        return result;
    }

    private static void dfsArticulation(int u, boolean[] visited, int[] disc, int[] low, int[] parent, Set<Integer> result, List<List<Integer>> graph, int[] time) {
        visited[u] = true;
        disc[u] = low[u] = ++time[0];
        int children = 0;
        for (int v : graph.get(u)) {
            if (!visited[v]) {
                children++;
                parent[v] = u;
                dfsArticulation(v, visited, disc, low, parent, result, graph, time);
                low[u] = Math.min(low[u], low[v]);
                if (parent[u] == -1 && children > 1) {
                    result.add(u);
                }
                if (parent[u] != -1 && low[v] >= disc[u]) {
                    result.add(u);
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

    /**
     * Finds all strongly connected components (SCCs) in a directed graph using Kosaraju's algorithm.
     *
     * @param graph adjacency list representation of the directed graph (graph[i] is a list of nodes j such that there is an edge i->j)
     * @return a list of SCCs, each SCC is a list of node indices
     * @throws IllegalArgumentException if the graph is null
     */
    public static List<List<Integer>> findStronglyConnectedComponents(List<List<Integer>> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        int n = graph.size();
        boolean[] visited = new boolean[n];
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        // 1. Fill order by finish time
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfsFillOrder(i, graph, visited, stack);
            }
        }
        // 2. Transpose the graph
        List<List<Integer>> transposed = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) transposed.add(new java.util.ArrayList<>());
        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                transposed.get(v).add(u);
            }
        }
        // 3. DFS on transposed graph in stack order
        java.util.Arrays.fill(visited, false);
        List<List<Integer>> sccs = new java.util.ArrayList<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<Integer> scc = new java.util.ArrayList<>();
                dfsCollectSCC(v, transposed, visited, scc);
                sccs.add(scc);
            }
        }
        return sccs;
    }

    private static void dfsFillOrder(int u, List<List<Integer>> graph, boolean[] visited, java.util.Deque<Integer> stack) {
        visited[u] = true;
        for (int v : graph.get(u)) {
            if (!visited[v]) {
                dfsFillOrder(v, graph, visited, stack);
            }
        }
        stack.push(u);
    }

    private static void dfsCollectSCC(int u, List<List<Integer>> graph, boolean[] visited, List<Integer> scc) {
        visited[u] = true;
        scc.add(u);
        for (int v : graph.get(u)) {
            if (!visited[v]) {
                dfsCollectSCC(v, graph, visited, scc);
            }
        }
    }
}