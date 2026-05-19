package org.example;

import javax.xml.transform.stream.StreamSource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaAnalysis {


    Map<String, Integer> map = new HashMap<>();

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public Map<String, Integer> intialiseMap()
    {
        map.put("Aman",1011);
        map.put("Rali",2011);
        map.put("Jali",3011);
        map.put("Pali",22011);
      return map;
    }

    public Map.Entry<String, Integer> getSecondHighestPaid(Map<String, Integer> map1){

        return map1.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .toList().get(1);
    }

    public void findMaxFrequency()
    {
        Integer[] numbers= {1, 2, 2, 3, 1, 4, 2, 5, 1,2};

        Optional<Map.Entry<Integer, Long>> map =Arrays.stream(numbers)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        map.ifPresent(integerLongEntry -> System.out.println("Number Frequency : " + integerLongEntry.getKey()));
        map.ifPresent(integerLongEntry -> System.out.println("Maximum Frequency : " + integerLongEntry.getValue()));
    }

    public void findMaxFrequencyStr()
    {
        String[] names = {"Ram","Krishna","Hari","Jay", "Jay", "Raj","Hari","Hari"};

        Optional<Map.Entry<String,Long>> map = Arrays.stream(names)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        map.ifPresent(str -> System.out.println("Name : "+str.getKey()));
        map.ifPresent(str -> System.out.println("Frequency : "+str.getValue()));
    }

    public void findFrequency()
    {
        String[] names = {"Ram","Krishna","Hari","Jay", "Jay", "Raj","Hari","Hari"};

      Map<String,Long> map = Arrays.stream(names)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

      System.out.println("Name : "+map);
    }

    public void findCharFrequency()
    {
        String name = "rajeshggsa";
        Map<Character,Long> map = name.chars().mapToObj(c->(char) c)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        System.out.println("Name : "+map);
    }

    public void findFirstRepeatingChar() {
        String str = "programming";
        Set<Character> seenCharacters = new HashSet<>();
        for (char ch : str.toCharArray()) {
            if (!seenCharacters.add(ch)) {
                 System.out.println("-----------"+ch);
                 return;// Found the first repeating character
            }
        }
        //Java 8 Lambda

       // Optional<Character> charafte= str.chars().mapToObj(c -> (char) c).filter(c -> !seenCharacters.add(c);
    }

    public void findCommanNames(){
        List<String> list1 = Arrays.asList("apple", "banana", "orange", "grape");
        List<String> list2 = Arrays.asList("banana", "kiwi", "orange", "pineapple");

        List<String> commonNames = list1.stream().filter(list2::contains).toList();
        System.out.println("-----commonNames ------"+commonNames);

    }

    public void pelindrome()
    {
        String str = "HAFAH";
      Boolean result = IntStream.range(0, str.length() / 2)
                .allMatch(i -> str.charAt(i) == str.charAt(str.length() - i - 1));
        System.out.println(result);
    }

    public void fabonacciSeries()
    {
        int n =10;
        List<Integer> fibonacciSeries = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(n)
                .map(t -> t[0])
                .collect(Collectors.toList());
        System.out.println(fibonacciSeries);
    }


    public void concat2Arrays() {
        Integer[] array1 = {5, 3, 9, 1};
        Integer[] array2 = {4, 3, 9, 7};

        List<Integer> result = Stream.concat(Arrays.stream(array1), Arrays.stream(array2))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public void secondHighestSalary() {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "HR", 5000),
                new Employee("Bob", "HR", 6000),
                new Employee("Charlie", "HR", 7000),
                new Employee("David", "IT", 8000),
                new Employee("Eve", "IT", 9000),
                new Employee("Frank", "IT", 10000)
        );

        Map<String, Optional<Employee>> secondHighestByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                                        .skip(1)
                                        .findFirst()
                        )
                ));
    }

    public void getDuplicateName()
    {

        List<String> names = Arrays.asList("apple", "banana", "apple", "orange", "banana", "grape");

        Set<String> duplicates = names.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        System.out.println("Duplicate strings: " + duplicates);
    }

    public void sortBasedonValues()
    {
        Map<String, Integer> fruitMap = new HashMap<>();
        fruitMap.put("Orange", 1);
        fruitMap.put("Apple", 3);
        fruitMap.put("Banana", 2);
        fruitMap.put("Grapes", 5);

        Map<String, Integer> sortedByValue = fruitMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // maintains insertion order
                ));
        System.out.println(sortedByValue);
    }


    public int trap(int[] height) {

        ///  call using int[] elevationMap = {0,1,0,2,1,0,1,3,2,1,2,1};
       /// Input: height = [4,2,0,6,2,5] Output: 9

        if (height == null || height.length < 3) return 0;

        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int waterTrapped = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    waterTrapped += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    waterTrapped += rightMax - height[right];
                }
                right--;
            }
        }

        return waterTrapped;
    }



    public int subarraySum(int[] nums, int k) {
        // Map to store cumulative sum frequencies
       // Input: nums = [1,1,1], k = 2 Output: 2

        //Example 2:

       // Input: nums = [3,4,7,2,-3,1], k = 7 Output: 3

        HashMap<Integer, Integer> sumMap = new HashMap<>();
        sumMap.put(0, 1); // Base case: sum 0 occurs once

        int count = 0;
        int sum = 0;

        for (int num : nums) {
            sum += num;

            // Check if there is a prefix subarray we can subtract to get sum = k
            if (sumMap.containsKey(sum - k)) {
                count += sumMap.get(sum - k);
            }

            // Update the frequency of the current cumulative sum
            sumMap.put(sum, sumMap.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    public void merge2String()
    {
        String s1 = "abcd";
        String s2 = "6343435";
        StringBuilder merged = new StringBuilder();

        int maxLength = Math.max(s1.length(), s2.length());

        for (int i = 0; i < maxLength; i++) {
            if (i < s1.length()) {
                merged.append(s1.charAt(i));
            }
            if (i < s2.length()) {
                merged.append(s2.charAt(i));
            }
        }

        System.out.println("Merged String: " + merged.toString());
    }

}

