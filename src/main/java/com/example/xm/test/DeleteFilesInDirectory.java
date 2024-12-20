package com.example.xm.test;

import java.io.File;

public class DeleteFilesInDirectory {
    public static void main(String[] args) {
        // 指定要处理的文件夹路径
        String folderPath = "E:\\123\\456\\789";
        // 创建File对象表示文件夹
        File folder = new File(folderPath);
        // 检查是否是目录
        if (folder.isDirectory()) {
            // 获取文件夹内所有文件和子文件夹
            File[] files = folder.listFiles();
            // 如果文件数组不为null且长度大于0，说明文件夹非空
            if (files != null && files.length > 0) {
                // 遍历文件夹下的每个文件或子文件夹
                for (File file : files) {
                    // 删除文件或子文件夹
                    deleteFileOrDirectory(file);
                }
            }
        }
    }

    /**
     * 删除文件或递归删除文件夹及其内容
     * @param file 要删除的文件或文件夹
     */
    private static void deleteFileOrDirectory(File file) {
        if (file.isDirectory()) {
            // 如果是文件夹，则递归删除其内容
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteFileOrDirectory(entry);
                }
            }
        }
        // 删除文件或清空后的文件夹
        file.delete();
    }
}
