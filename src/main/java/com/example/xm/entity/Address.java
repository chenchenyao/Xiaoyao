package com.example.xm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName address
 */
@TableName(value ="address")
@Data
public class Address implements Serializable {
    private Long id;

    private Long userId;

    private String province;

    private String city;

    private String town;

    private String mobile;

    private String street;

    private String contact;

    private Boolean isDefault;

    private String notes;

    private Boolean deleted;

    private static final long serialVersionUID = 1L;

}