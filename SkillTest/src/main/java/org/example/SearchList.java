package org.example;


import java.util.Arrays;
import java.util.List;

public class SearchList {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Alice");
        for (String name : names) {
            if (names.contains(name)) {
                System.out.println(name + " is in the list.");
            }
        }
    }
}
