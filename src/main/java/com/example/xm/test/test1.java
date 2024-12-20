package com.example.xm.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class test1 {
    public static void main(String[] args) {
        //List<Integer> list1 = List.of(1, 2, 3, 4, 5);
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = List.of(3, 4, 5, 6, 7);
        List<Integer> list3 = null;
        List<Integer> commonElements = mergeAndDeduplicate(list1, list2);
        System.out.println(commonElements);
        System.out.println(list1.size());
        System.out.println(100 / 3);
        //System.out.println(list3.size());
        int m =0;
        for (int i = 0; i < list2.size(); i++){
            //m++;
            System.out.println(m+++1);
        }

    }
    public static List<Integer> findCommonElements(List<Integer> list1, List<Integer> list2) {
        return list1.stream()
                .distinct()
                .filter(list2::contains)
                .collect(Collectors.toList());
    }

    private static List<Integer> mergeAndDeduplicate(List<Integer> list1, List<Integer> list2) {
        Set<Integer> set = new HashSet<>(list1);
        set.addAll(list2);
        return new ArrayList<>(set);
    }



}
