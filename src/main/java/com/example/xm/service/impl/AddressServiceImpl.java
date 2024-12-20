package com.example.xm.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xm.entity.Address;
import com.example.xm.service.IAddressService;
import com.example.xm.mapper.AddressMapper;
import com.example.xm.util.PoiUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
* @author admin
* @description 针对表【address】的数据库操作Service实现
* @createDate 2024-08-02 15:23:05
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

    @Override
    public Object exportExcel(HttpServletResponse response) throws IOException {
        listExport(response);
        return "ok";
    }

    public static void listExport(HttpServletResponse response) throws IOException {
        Address address = new Address();
        // 获取导出数据
        List<Address> list =new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //Address address = new Address();
            address.setProvince("南京00" + i);
            list.add(address);
        }

        //List<MyData> myDataList = service.getData();
        InputStream inputStream = PoiUtil.getResourcesFileInputStream("template/excel/model.xlsx");
        // 由模板生成多sheet
        InputStream is = PoiUtil.createSheetFromTemplate(inputStream, list.size());
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(is).build();
        for (int i = 0; i < list.size(); i++) {
            int num = i + 1;
            Address data = list.get(i);
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet" + num).build();
            excelWriter.fill(data, writeSheet);
        }
        excelWriter.finish();
    }
}




