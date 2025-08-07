package com.example.classcancellationmanager.mapper;

import com.example.classcancellationmanager.entity.Enrollment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnrollmentMapper {

    @Select("SELECT enrollment_id, student_id, class_id, academic_year, term_id FROM enrollments WHERE student_id = #{studentId}")
    List<Enrollment> findByStudentId(Long studentId);

    @Select("SELECT enrollment_id, student_id, class_id, academic_year, term_id FROM enrollments " +
            "WHERE student_id = #{studentId} AND academic_year = #{year} AND term_id = #{termId}")
    List<Enrollment> findByStudentIdAndYearAndTerm(@Param("studentId") Long studentId, @Param("year") int year, @Param("termId") Long termId);

    @Delete("DELETE FROM enrollments WHERE student_id = #{studentId} AND academic_year = #{year} AND term_id = #{termId}")
    void deleteByStudentIdAndYearAndTerm(@Param("studentId") Long studentId, @Param("year") int year, @Param("termId") Long termId);

    @Insert("INSERT INTO enrollments (student_id, class_id, academic_year, term_id) " +
            "VALUES (#{studentId}, #{classId}, #{academicYear}, #{termId})")
    void insert(Enrollment enrollment);

}