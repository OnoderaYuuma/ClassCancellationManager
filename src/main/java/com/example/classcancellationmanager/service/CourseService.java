package com.example.classcancellationmanager.service;

import com.example.classcancellationmanager.entity.Course;
import com.example.classcancellationmanager.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public List<Course> findAllCourses() {
        return courseMapper.findAll();
    }
}
