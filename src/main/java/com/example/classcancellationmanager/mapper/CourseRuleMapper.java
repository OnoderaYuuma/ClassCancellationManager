package com.example.classcancellationmanager.mapper;

import com.example.classcancellationmanager.entity.CourseRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseRuleMapper {

    @Select("SELECT course_rule_id, course_rule_name FROM course_rules ORDER BY course_rule_id")
    List<CourseRule> findAll();
}
