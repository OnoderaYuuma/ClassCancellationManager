package com.example.classcancellationmanager.entity;

import lombok.Data;

@Data
public class Course {
    private Long classId;
    private String className;
    private String courseRulesName;
    private Integer creditHours;
    private Integer academicYear;
    private String scheduledTerm;
    private Integer recommendedGrade;
}
