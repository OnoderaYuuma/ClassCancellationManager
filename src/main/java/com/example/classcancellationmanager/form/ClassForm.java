package com.example.classcancellationmanager.form;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ClassForm {
    private String className;
    private Long courseRuleId;
    private Integer creditHours;
    private Integer academicYear;
    private Long termId;
    private Integer recommendedGrade;
    private List<ScheduleForm> schedules = new ArrayList<>();
}
