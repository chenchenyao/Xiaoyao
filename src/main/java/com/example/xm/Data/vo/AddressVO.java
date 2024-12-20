package com.example.xm.Data.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AddressVO {
    @ExcelProperty("省份")
    private String province;
    @ExcelProperty("城市")
    private String city;
    @ExcelProperty("区")
    private String town;
}
