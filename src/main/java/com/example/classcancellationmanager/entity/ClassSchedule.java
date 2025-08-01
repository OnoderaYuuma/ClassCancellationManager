package com.example.classcancellationmanager.entity;

import lombok.Data;

@Data
public class ClassSchedule {
    private Long scheduleId;
    private Long classId;
    private String dayOfWeek;
    private Integer period;
}
