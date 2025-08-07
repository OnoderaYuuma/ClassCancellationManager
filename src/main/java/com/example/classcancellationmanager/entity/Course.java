package com.example.classcancellationmanager.entity;

import lombok.Data;

import java.util.List;

@Data
public class Course {
    private Long classId;
    private String className;
    private Long courseRuleId;
    private Integer creditHours;
    private Integer academicYear;
    private Long termId;
    private Integer recommendedGrade;
    private List<ClassSchedule> schedules;
}
