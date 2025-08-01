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
        // 1. 元の日付の曜日と、授業のスケジュールが合っているか検証
        DayOfWeek dayOfWeek = event.getOriginalDate().getDayOfWeek();
        String dayOfWeekString = dayOfWeek.name(); // "MONDAY", "TUESDAY", ...

        // 振替授業でない場合は、元の授業の時限を特定する必要があるが、
        // 現在の設計ではEventに元の時限を保持するフィールドがない。
        // ここでは、その曜日に何らかの授業スケジュールが存在するかどうかで簡易的にチェックする。
        // TODO: Eventにoriginal_periodを追加し、より厳密なチェックを行うことを検討
        if (classScheduleMapper.findByClassIdAndDayOfWeek(event.getClassId(), dayOfWeekString).isEmpty()) {
            throw new IllegalArgumentException("指定された授業は、その曜日（" + dayOfWeek + "）に開講されていません。");
        }

        // 2. 振替授業の場合のバリデーション
        if ("MAKEUP_CLASS".equals(event.getEventType())) {
            if (event.getMakeupDate() == null || event.getMakeupPeriod() == null) {
                throw new IllegalArgumentException("振替授業の場合は、振替日と振替時限の両方を指定してください。");
            }
        }

        eventMapper.insert(event);
    }
}

