package com.example.xm.mapper;

import com.example.xm.entity.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【address】的数据库操作Mapper
* @createDate 2024-08-02 15:23:05
* @Entity com.example.xm.entity.Address
*/
@Mapper
public interface AddressMapper extends BaseMapper<Address> {

}




