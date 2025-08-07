package com.example.classcancellationmanager.mapper;

import com.example.classcancellationmanager.entity.ClassSchedule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassScheduleMapper {

    @Select("SELECT schedule_id, class_id, day_of_week, period FROM class_schedules")
    List<ClassSchedule> findAll();

    @Select("SELECT schedule_id, class_id, day_of_week, period FROM class_schedules WHERE class_id = #{classId}")
    List<ClassSchedule> findByClassId(Long classId);

    @Insert("INSERT INTO class_schedules (class_id, day_of_week, period) VALUES (#{classId}, #{dayOfWeek}, #{period})")
    void insert(ClassSchedule schedule);

    @Select("SELECT schedule_id, class_id, day_of_week, period FROM class_schedules WHERE schedule_id = #{scheduleId}")
    ClassSchedule findById(Long scheduleId);

    @Select("SELECT schedule_id, class_id, day_of_week, period FROM class_schedules WHERE class_id = #{classId} AND day_of_week = #{dayOfWeek}")
    List<ClassSchedule> findByClassIdAndDayOfWeek(@Param("classId") Long classId, @Param("dayOfWeek") String dayOfWeek);

    @Select("SELECT schedule_id, class_id, day_of_week, period FROM class_schedules WHERE class_id = #{classId} AND day_of_week = #{dayOfWeek} AND period = #{period}")
    ClassSchedule findByClassIdAndDayOfWeekAndPeriod(@Param("classId") Long classId, @Param("dayOfWeek") String dayOfWeek, @Param("period") Integer period);
}