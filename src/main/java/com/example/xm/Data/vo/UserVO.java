package com.example.xm.Data.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserVO {
    @ExcelProperty("姓名")
    private String username;
    @ExcelProperty("密码")
    private String password;
    @ExcelProperty("手机号")
    private String phone;
}
