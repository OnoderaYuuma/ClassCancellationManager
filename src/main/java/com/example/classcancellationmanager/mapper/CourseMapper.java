package com.example.classcancellationmanager.mapper;

import com.example.classcancellationmanager.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("SELECT class_id, class_name, course_rule_id, credit_hours, academic_year, term_id, recommended_grade FROM classes")
    List<Course> findAll();

    @Select("SELECT class_id, class_name, course_rule_id, credit_hours, academic_year, term_id, recommended_grade FROM classes WHERE class_id = #{classId}")
    Course findById(Long classId);
}
