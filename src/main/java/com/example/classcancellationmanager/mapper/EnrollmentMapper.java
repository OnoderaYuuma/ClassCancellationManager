package com.example.classcancellationmanager.mapper;

import com.example.classcancellationmanager.entity.Enrollment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnrollmentMapper {

    @Select("SELECT enrollment_id, student_id, class_id, academic_year, scheduled_term FROM enrollments")
    List<Enrollment> findAll();

    @Select("SELECT enrollment_id, student_id, class_id, academic_year, scheduled_term FROM enrollments WHERE student_id = #{studentId}")
    List<Enrollment> findByStudentId(Long studentId);
}
