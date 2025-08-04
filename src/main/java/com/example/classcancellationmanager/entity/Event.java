package com.example.classcancellationmanager.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Event {
    private Long eventId;
    private Long classId;
    private String eventType;
    private LocalDate eventDate;
    private Integer eventPeriod;
    private String description;
    private LocalDate makeupDate;
    private Integer makeupPeriod;
    private Long registeredBy;
    private LocalDateTime createdAt;
}
