package com.example.xm.entity;

import lombok.Data;

@Data
public class ExcelProjectTaskVO {
    private Integer projectId;
    private Integer taskId;
    private String workMonth;
    private String workDay;
    private String straightTime;
    private String overtimeWeekday;
    private String overtimeWeekend;
    private String overtimeHoliday;

    public ExcelProjectTaskVO(Integer projectId, Integer taskId, String workMonth, String workDay, String straightTime, String overtimeWeekday, String overtimeWeekend, String overtimeHoliday) {
        this.projectId = projectId;
        this.taskId = taskId;
        this.workMonth = workMonth;
        this.workDay = workDay;
        this.straightTime = straightTime;
        this.overtimeWeekday = overtimeWeekday;
        this.overtimeWeekend = overtimeWeekend;
        this.overtimeHoliday = overtimeHoliday;
    }
}
