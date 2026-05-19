package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingRBOPDesc {

    public static List<Integer> sortListRBOPDesc(List<Integer> list, int K) {
        List<Integer> sortedlist = new ArrayList<>();
        if(K > 1)
        {
            sortedlist.addAll(list);
            for (int i = K -1; i > 0; i --) {
                sortedlist.set(i,list.get(i-1));
            }
            sortedlist.set(0, list.get(K-1));
        }

        List<Integer> descList = new ArrayList<>(list.subList(K, list.size()));
        descList.sort(Collections.reverseOrder());

        for(int j=K; j<list.size(); j++) {
            sortedlist.set(j, descList.get(j-K));
        }
        return sortedlist;
    }

    public static void main(String[] args) {
        System.out.println(sortListRBOPDesc(List.of(3, 2, 1, 5, 6, 4), 3));
    }
}
