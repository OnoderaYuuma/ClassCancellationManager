package com.example.classcancellationmanager.service;

import com.example.classcancellationmanager.entity.ClassSchedule;
import com.example.classcancellationmanager.mapper.ClassScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassScheduleService {

    @Autowired
    private ClassScheduleMapper classScheduleMapper;

    public List<ClassSchedule> findAll() {
        return classScheduleMapper.findAll();
    }
}
