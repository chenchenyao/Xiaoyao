package com.example.xm.util;

import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PoiUtil {
    /**
     *获取resource下模板文件的文件流
     *
     */
    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    /**
     * 通过模板 生成多sheet
     *
     * @param inputStream
     * @return
     */
    @SneakyThrows
    public static InputStream createSheetFromTemplate(InputStream inputStream, int size) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //原模板只有一个sheet，通过poi复制出需要的sheet个数的模板
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        //设置模板的第一个sheet的名称
        workbook.setSheetName(0, "sheet1");
        for (int i = 1; i < size; i++) {
            //复制模板，得到第i个sheet
            int num = i + 1;
            workbook.cloneSheet(0, "sheet" + num);
        }
        //写到流里
        workbook.write(bos);
        byte[] bArray = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(bArray);
        return is;
    }

}
