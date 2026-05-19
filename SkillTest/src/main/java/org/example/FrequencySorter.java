package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencySorter {

    public static List<Integer> sortByFrequency(List<Integer> nums)
    {
     Map<Integer, Integer> frequencyMap = new HashMap<>();
     Map<Integer, Integer> frequencyIndexMap = new HashMap<>();

     for(int i=0; i<nums.size(); i++) {
     int num = nums.get(i);
     frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
     frequencyIndexMap.putIfAbsent(num, i);
     }

     List<Integer> sorted = new ArrayList<>(nums);
     sorted.sort((a,b) -> {
         int freqA = frequencyMap.get(a);
         int freqB = frequencyMap.get(b);
         if(freqA != freqB) {
             return Integer.compare(freqA, freqB);
         } else {
             return Integer.compare(frequencyIndexMap.get(a), frequencyIndexMap.get(b));
         }
     });
     return sorted;
    }

    public static void main(String[] args) {
        List<Integer> nums = List.of(4, 5, 6, 5, 4, 3);
        List<Integer> sorted = sortByFrequency(nums);
        System.out.println(sorted); // Output: [6, 3, 5, 5, 4, 4]
    }
}
