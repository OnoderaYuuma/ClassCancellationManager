package com.example.classcancellationmanager.service;

import com.example.classcancellationmanager.entity.Event;
import com.example.classcancellationmanager.mapper.ClassScheduleMapper;
import com.example.classcancellationmanager.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private ClassScheduleMapper classScheduleMapper;

    public List<Event> findAllEvents() {
        return eventMapper.findAll();
    }

    @Transactional
    public void saveEvent(Event event) {
        // イベント種別が「休講」の場合のみ、元の授業スケジュールが存在するかを検証する
        if ("CANCELLATION".equals(event.getEventType())) {
            DayOfWeek dayOfWeek = event.getEventDate().getDayOfWeek();
            String dayOfWeekString = dayOfWeek.name(); // "MONDAY", "TUESDAY", ...

            // 指定された曜日に授業が存在するかチェック
            if (classScheduleMapper.findByClassIdAndDayOfWeekAndPeriod(event.getClassId(), dayOfWeekString, event.getEventPeriod()) == null) {
                throw new IllegalArgumentException("休講対象の授業が、指定された曜日・時限（" + dayOfWeek + " " + event.getEventPeriod() + "限）に存在しません。");
            }
        }


        eventMapper.insert(event);
    }
}

