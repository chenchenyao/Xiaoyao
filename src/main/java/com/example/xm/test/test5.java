package com.example.xm.test;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

public class test5 {


    public static String calcSignData(Map<String, String> requestParam, String appSecret) {
        // 创建一个用于存储键值对的列表
        List<Map.Entry<String, String>> paramList = new ArrayList<>(requestParam.entrySet());
        // 对键进行排序（忽略大小写）
        paramList.sort(Comparator.comparing(entry -> entry.getKey().toUpperCase()));
        // 构建要哈希的字符串
        StringBuilder tempVal = new StringBuilder();
        for (Map.Entry<String, String> entry : paramList) {
            if (!"operatetype".equalsIgnoreCase(entry.getKey()) && !"signdata".equalsIgnoreCase(entry.getKey())) {
                tempVal.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        // 将appSecret追加到字符串末尾
        tempVal.append(appSecret);
        // 使用MD5算法计算哈希值
        String signData = DigestUtils.md5Hex(tempVal.toString());
        return signData;
    }


    public static void main(String[] args) {
        String appSecret = "459b8a21cefe40ee80ced87f2be5445d";
        Map<String, String> params1 = new HashMap<>();
        params1.put("appKey", "a3a80d03961a4c8f9fea328f80dff98c");
        params1.put("operateType", "uploadFile");
        params1.put("orgDocId", "");
        params1.put("orgDocVerId", "");
        params1.put("timeStamp", "1728973369");
        params1.put("uploadType", "1");
        String signData = calcSignData(params1, appSecret);
        System.out.println("Generated signData: " + signData);


        Map<String, String> params2 = new HashMap<>();
        params2.put("appKey", "a3a80d03961a4c8f9fea328f80dff98c");
        params2.put("docId", "fb46d5a4deac42be96889fb1e62a1ed7");
        params2.put("docVerId", "ff314e68574b4bfeb42351d0aca60572");
        params2.put("extParam", "");
        params2.put("idType", "light");
        params2.put("timeStamp", "1728958416");
        params2.put("userId", "10801");
        params2.put("userName", "张三");
        String signData2 = calcSignData(params2, appSecret);
        //params2.put("signData", signData2);
        System.out.println("Generated signData: " + signData2);
    }
}
