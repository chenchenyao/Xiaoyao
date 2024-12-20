package com.example.xm.test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class test4 {
    public static void main(String[] args) {
        // 指定要处理的文件夹路径
        String folderPath = "E:\\123\\456\\789";
        try {
            FileUtils.deleteDirectory(new File(folderPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello World!");
    }

}
