package com.example.xm.test;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class FolderToZip {

    public static void main(String[] args) {
        // 指定要压缩的文件夹路径
        Path sourceDirPath = Paths.get("D:\\Tencent\\folder_to_compress");

        // 指定输出的 ZIP 文件路径
        Path zipFilePath = Paths.get("D:\\Tencent\\compressed_folder.zip");

        try {
            // 创建 ZIP 输出流
            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath.toFile()))) {
                // 遍历文件夹并添加到 ZIP 文件
                Files.walk(sourceDirPath)
                        .filter(path -> !Files.isDirectory(path))  // 排除目录
                        .forEach(path -> {
                            try {
                                ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                                zipOut.putNextEntry(zipEntry);
                                Files.copy(path, zipOut);
                                zipOut.closeEntry();
                            } catch (IOException e) {
                                System.err.println("无法压缩文件: " + path);
                                e.printStackTrace();
                            }
                        });
            }
            System.out.println("文件夹已成功压缩为: " + zipFilePath);
        } catch (IOException e) {
            System.err.println("压缩文件夹时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
