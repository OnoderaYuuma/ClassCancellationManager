package com.example.classcancellationmanager.entity;

import lombok.Data;

@Data
public class Enrollment {
    private Long enrollmentId;
    private Long studentId;
    private Long classId;
    private Integer academicYear;
    private Long termId;
}
