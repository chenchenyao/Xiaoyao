package com.example.xm.test;

import java.util.List;

public class test6 {
    public static void main(String[] args) {
        List<String> list = List.of("1-","2","3");
        String result = joinElementsWithFormat(list, "");
        System.out.println(result);
    }

     private static String joinElementsWithFormat(List<String> elements, String delimiter) {
        if (elements == null || elements.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i));
            if (i < elements.size() - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}
