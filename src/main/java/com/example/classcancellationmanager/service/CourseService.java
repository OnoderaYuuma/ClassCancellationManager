package com.example.classcancellationmanager.service;

import com.example.classcancellationmanager.entity.ClassSchedule;
import com.example.classcancellationmanager.entity.Course;
import com.example.classcancellationmanager.entity.CourseRule;
import com.example.classcancellationmanager.entity.Term;
import com.example.classcancellationmanager.form.ClassForm;
import com.example.classcancellationmanager.form.ScheduleForm;
import com.example.classcancellationmanager.mapper.ClassScheduleMapper;
import com.example.classcancellationmanager.mapper.CourseMapper;
import com.example.classcancellationmanager.mapper.CourseRuleMapper;
import com.example.classcancellationmanager.mapper.TermMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseRuleMapper courseRuleMapper;

    @Autowired
    private TermMapper termMapper;

    @Autowired
    private ClassScheduleMapper classScheduleMapper;

    public List<Course> findAllCourses() {
        return courseMapper.findAll();
    }

    public List<CourseRule> findAllCourseRules() {
        return courseRuleMapper.findAll();
    }

    public List<Term> findAllTerms() {
        return termMapper.findAll();
    }

    @Transactional
    public void createClass(ClassForm classForm) {
        // 1. classesテーブルに登録
        Course course = new Course();
        BeanUtils.copyProperties(classForm, course);
        courseMapper.insert(course);

        // 2. class_schedulesテーブルに登録
        Long newClassId = course.getClassId();
        for (ScheduleForm scheduleForm : classForm.getSchedules()) {
            ClassSchedule schedule = new ClassSchedule();
            schedule.setClassId(newClassId);
            schedule.setDayOfWeek(scheduleForm.getDayOfWeek());
            schedule.setPeriod(scheduleForm.getPeriod());
            classScheduleMapper.insert(schedule);
        }
    }
}
