package org.example;

import java.util.List;

public class CountLessK {

    public static int countLessThanK(List<Integer> values, int k) {
    int count = 0;
    if(values == null || values.isEmpty()) {
        return 0;
    }
    for(int i = 0; i < values.size(); i++) {
        if(values.get(i) < k) {
            count++;
        }
    }
    return count;
    }

    public static void main(String[] args) {
        System.out.println(countLessThanK(List.of(1, 2, 3, 4, 5), 3)); // Output: 2
    }
}
