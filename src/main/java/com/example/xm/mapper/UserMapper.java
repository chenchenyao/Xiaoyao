package com.example.xm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xm.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-08-01 15:24:04
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




