package com.example.classcancellationmanager.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class EventForm {

    private Long classId;

    private String eventType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate originalDate;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate makeupDate;

    private Integer makeupPeriod;
}
