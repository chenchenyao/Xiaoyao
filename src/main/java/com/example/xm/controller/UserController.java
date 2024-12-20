package com.example.xm.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.example.xm.Data.vo.AddressVO;
import com.example.xm.Data.vo.UserVO;
import com.example.xm.entity.Address;
import com.example.xm.entity.ExcelData;
import com.example.xm.entity.User;
import com.example.xm.mapper.AddressMapper;
import com.example.xm.mapper.UserMapper;
import com.example.xm.service.IAddressService;
import com.example.xm.service.IUserService;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("hello")
    public String getUser(){
        return "hello";
    }


    @GetMapping("export")
    public Object getExcelInfo(HttpServletResponse response) throws IOException {
        return addressService.exportExcel(response);
    }

    @SneakyThrows
    @GetMapping("/excel")
    public void excel (HttpServletResponse response){
        List<ExcelData> testExcels = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            ExcelData excelData = new ExcelData();
//            excelData.setProjectName("青岛" + i + "号基地");
//            excelData.setLocation("江苏南京");
//            HashMap<String,String> map1 = new HashMap<>();
//            map1.put("1","5.0");
//            map1.put("2","6.0");
//            excelData.setList1(map1);
////            HashMap<String,String> map2 = new HashMap<>();
////            map2.put("1","7.0");
////            map2.put("2","8.0");
//            //excelData.setList2(map2);
//            testExcels.add(excelData);
//        }

            ExcelData excelData = new ExcelData();
            excelData.setProjectName("青岛号基地");
            excelData.setLocation("江苏南京");
            HashMap<String,String> map1 = new HashMap<>();
            map1.put("1","5.0");
            map1.put("2","6.0");
            excelData.setList1(map1);
//            HashMap<String,String> map2 = new HashMap<>();
//            map2.put("1","7.0");
//            map2.put("2","8.0");
            //excelData.setList2(map2);
            testExcels.add(excelData);

        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        //ClassPathResource classPathResource = new ClassPathResource("template" + File.separator + "测试.xlsx");
        ClassPathResource classPathResource = new ClassPathResource("template/excel/model.xlsx");
        //InputStream stream = classPathResource.getStream();
        InputStream stream = classPathResource.getInputStream();
        // 把excel流给这个对象，后续可以操作
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        // 设置模板的第一个sheet的名称，名称我们使用合同号
        workbook.setSheetName(0, testExcels.get(0).getProjectName());
        for (int i = 1; i < testExcels.size(); i++) {
            // 剩余的全部复制模板sheet0即可
            workbook.cloneSheet(0, testExcels.get(i).getProjectName());
        }
        // 把workbook写到流里
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] bytes = baos.toByteArray();
        stream = new ByteArrayInputStream(bytes);
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(stream).build();

        for (ExcelData testExcel : testExcels) {
            WriteSheet writeSheet = EasyExcel.writerSheet(testExcel.getProjectName()).build();
            // list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).direction(WriteDirectionEnum.VERTICAL).build();
            excelWriter.fill(testExcel, writeSheet);
            excelWriter.fill(new FillWrapper("list1", Collections.singleton(testExcel.getList1())), fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("list2", Collections.singleton(testExcel.getList2())), fillConfig, writeSheet);
        }
        excelWriter.finish();
        baos.close();
        stream.close();
    }


    /**
     * 导出excel到本地
     * @param request
     * @param response
     */
    @GetMapping("saveExcel")
    public void export(HttpServletRequest request, HttpServletResponse response){
        List<AddressVO> addressList1 = new ArrayList<>();
        List<UserVO> userList1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AddressVO vo =new AddressVO();
            vo.setCity("城市" + i);
            vo.setProvince("省份" + i);
            vo.setTown("乡镇" + i);
            addressList1.add(vo);
        }
        for (int i = 0; i < 9; i++) {
            UserVO vo =new UserVO();
            vo.setPassword("123" + i);
            vo.setPhone("456" + i);
            vo.setUsername("789" + i);
            userList1.add(vo);
        }
        System.out.println(123);

        String outputFileName = "E:/dist/"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +"output.xlsx";
        Resource excel = new ClassPathResource("template/excel/test.xlsx");


        try {
            try (ExcelWriter excelWriter = EasyExcel.write(outputFileName).build()) {
                WriteSheet writeSheet = EasyExcel.writerSheet(0,"123").head(AddressVO.class).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(addressList1, writeSheet);
                WriteSheet writeSheet1 = EasyExcel.writerSheet(1,"456").head(UserVO.class).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(userList1, writeSheet1);

                deleteFilesAndSubdirectories(new File("E:\\"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 递归删除文件夹中的所有文件和子文件夹
     * @param file 目标文件或文件夹
     */
    private static void deleteFilesAndSubdirectories(File file) {
        // 如果当前是文件夹
        if (file.isDirectory()) {
            // 获取文件夹下的所有文件和子文件夹
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    // 递归调用以删除子文件夹及其内容
                    deleteFilesAndSubdirectories(entry);
                }
            }
        }
        // 删除当前文件或空文件夹
        if (!file.delete()) {
            System.err.println("Failed to delete: " + file.getAbsolutePath());
        }
    }
}
