package com.example.xm.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ExcelData {
    private String projectName;
    private String location;
    private Map<String,String> list1;
    private Map<String,String> list2;
}
