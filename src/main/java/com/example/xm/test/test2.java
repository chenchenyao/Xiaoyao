package com.example.xm.test;

import com.example.xm.entity.ExcelProjectTaskVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class test2 {
    public static void main(String[] args) {
        List<ExcelProjectTaskVO> tasks = Arrays.asList(
                new ExcelProjectTaskVO(1,1,"2024-08","2024-08-28", "2","9","3","2"),
                new ExcelProjectTaskVO(1,1,"2024-08","2024-08-28", "2","3","8","3"),
                new ExcelProjectTaskVO(1,1,"2024-08","2024-08-25", "2","3","9","6"),
                new ExcelProjectTaskVO(1,1,"2024-08","2024-08-22", "2","3","0","2"),
                new ExcelProjectTaskVO(1,1,"2024-08","2024-08-15", "2","3","2","2"),
                new ExcelProjectTaskVO(1,1,"2024-08","2024-08-02", "2","3","0","3"),
                new ExcelProjectTaskVO(1,1,"2024-08","2024-08-02", "2","0","3","0")
        );
        System.out.println(tasks);
        //Map<String, String> stringStringMap = processTasks(tasks);
        Map<String, String> stringStringMap = processTotalTasks(tasks);
//        Map<String, String> result = tasks.stream()
//                .collect(Collectors.toMap(
//                        vo -> vo.getWorkDay().length() > 5 ? vo.getWorkDay().substring(vo.getWorkDay().length() - 2) : vo.getWorkDay(), // 提取day部分
//                        ExcelProjectTaskVO::getOvertimeWeekday,
//                        (oldValue, newValue) -> String.valueOf(Double.parseDouble(oldValue) + Double.parseDouble(newValue)), // 合并策略：将overtime相加
//                        LinkedHashMap::new // 保持插入顺序
//                ));
        stringStringMap.forEach((key, value) -> System.out.println(key + "=" + value));
        System.out.println(sumStraightTime(new HashMap<>()));
    }
    public static Map<String, String> processTasks(List<ExcelProjectTaskVO> list) {
        Map<String, BigDecimal> result = new HashMap<>();
        // 遍历列表
        for (ExcelProjectTaskVO vo : list) {
            // 获取 workDay 并提取最后两位数字
            String workDay = extractDate(vo.getWorkDay());
            // 将 overtimeWeekday 转换为 BigDecimal 进行数值计算
            BigDecimal overtime = new BigDecimal(vo.getOvertimeWeekday());
            // 如果 map 中已经有这个 key，则累加值；否则，添加新的条目
            result.merge(workDay, overtime, BigDecimal::add);
        }
        // 创建一个新的映射来存储最终的结果
        Map<String, String> finalResult = new HashMap<>();
        for (Map.Entry<String, BigDecimal> entry : result.entrySet()) {
            // 格式化 BigDecimal 值，去掉不需要的小数点后缀
            String value = entry.getValue().stripTrailingZeros().toPlainString();
            finalResult.put(entry.getKey(), value);
        }
        return finalResult;
    }


    public static Map<String, String> processTotalTasks(List<ExcelProjectTaskVO> list) {
        Map<String, BigDecimal> result = new HashMap<>();
        // 遍历列表
        for (ExcelProjectTaskVO vo : list) {
            // 获取 workDay 并提取最后两位数字
            String workDay = extractDate(vo.getWorkDay());
            // 将 overtimeWeekday 转换为 BigDecimal 进行数值计算
            BigDecimal overtime = new BigDecimal(vo.getOvertimeWeekday())
                    .add(new BigDecimal(vo.getStraightTime()))
                    .add(new BigDecimal(vo.getOvertimeWeekend()))
                    .add(new BigDecimal(vo.getOvertimeHoliday()));
            // 如果 map 中已经有这个 key，则累加值；否则，添加新的条目
            result.merge(workDay, overtime, BigDecimal::add);
        }
        // 创建一个新的映射来存储最终的结果
        Map<String, String> finalResult = new HashMap<>();
        for (Map.Entry<String, BigDecimal> entry : result.entrySet()) {
            // 格式化 BigDecimal 值，去掉不需要的小数点后缀
            String value = entry.getValue().stripTrailingZeros().toPlainString();
            finalResult.put(entry.getKey(), value);
        }
        return finalResult;
    }


    public static String sumStraightTime(Map<String, String> timeMap) {
        BigDecimal total = BigDecimal.ZERO; // Start with zero

        // Iterate over all values in the map and add them to the total
        for (String value : timeMap.values()) {
            total = total.add(new BigDecimal(value));
        }

        // Convert to string and remove trailing '.0' if present
        return total.stripTrailingZeros().toPlainString();
    }


    private static  String extractDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr); // 假设日期格式正确
        return String.format("%02d", date.getDayOfMonth());
    }
}
