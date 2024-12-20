package com.example.xm.service;

import com.example.xm.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author admin
* @description 针对表【address】的数据库操作Service
* @createDate 2024-08-02 15:23:05
*/
public interface IAddressService extends IService<Address> {

    Object exportExcel(HttpServletResponse response) throws IOException;
}
