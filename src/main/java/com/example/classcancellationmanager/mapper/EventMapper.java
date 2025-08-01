package com.example.classcancellationmanager.mapper;

import com.example.classcancellationmanager.entity.Event;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventMapper {

    @Select("SELECT event_id, class_id, event_type, original_date, description, makeup_date, makeup_period, registered_by, created_at FROM schedule_events")
    List<Event> findAll();

    @Select("SELECT event_id, class_id, event_type, original_date, description, makeup_date, makeup_period, registered_by, created_at FROM schedule_events WHERE class_id = #{classId}")
    List<Event> findByClassId(Long classId);

    @Insert("INSERT INTO schedule_events (class_id, event_type, original_date, description, makeup_date, makeup_period, registered_by) " +
            "VALUES (#{classId}, #{eventType}, #{originalDate}, #{description}, #{makeupDate}, #{makeupPeriod}, #{registeredBy})")
    @Options(useGeneratedKeys = true, keyProperty = "eventId")
    void insert(Event event);
}