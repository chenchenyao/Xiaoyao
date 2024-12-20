package com.example.xm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xm.entity.User;
import com.example.xm.service.IUserService;
import com.example.xm.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-08-01 15:24:04
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}




