package com.example.classcancellationmanager.service;

import com.example.classcancellationmanager.entity.Course;
import com.example.classcancellationmanager.entity.Enrollment;
import com.example.classcancellationmanager.entity.Event;
import com.example.classcancellationmanager.mapper.CourseMapper;
import com.example.classcancellationmanager.mapper.EnrollmentMapper;
import com.example.classcancellationmanager.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentScheduleService {

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private CourseMapper courseMapper;

    public List<Map<String, Object>> getStudentEventsForFullCalendar(Long studentId) {
        List<Map<String, Object>> fullCalendarEvents = new ArrayList<>();

        // 1. 学生が履修している授業の情報を取得
        List<Enrollment> enrollments = enrollmentMapper.findByStudentId(studentId);

        for (Enrollment enrollment : enrollments) {
            Course course = courseMapper.findById(enrollment.getClassId());
            if (course == null) continue;

            // 2. 授業に関連するすべてのイベントを取得
            List<Event> events = eventMapper.findByClassId(enrollment.getClassId());

            for (Event event : events) {
                switch (event.getEventType()) {
                    case "CANCELLATION":
                        // 休講イベントを追加
                        fullCalendarEvents.add(createCalendarEvent(
                                course.getClassName(),
                                "休講",
                                event.getEventDate(),
                                event.getEventPeriod(),
                                event.getDescription(),
                                "CANCELLATION"
                        ));

                        // 紐付く振替授業があれば、それも追加
                        if (event.getMakeupDate() != null && event.getMakeupPeriod() != null) {
                            fullCalendarEvents.add(createCalendarEvent(
                                    course.getClassName(),
                                    "振替授業",
                                    event.getMakeupDate(),
                                    event.getMakeupPeriod(),
                                    "(振替) " + event.getDescription(),
                                    "MAKEUP_CLASS"
                            ));
                        }
                        break;

                    case "MAKEUP_CLASS":
                        // 単独の振替授業イベントを追加
                        fullCalendarEvents.add(createCalendarEvent(
                                course.getClassName(),
                                "振替授業",
                                event.getEventDate(),
                                event.getEventPeriod(),
                                event.getDescription(),
                                "MAKEUP_CLASS"
                        ));
                        break;

                    case "SPECIAL_LECTURE":
                        // 特別講義イベントを追加
                        fullCalendarEvents.add(createCalendarEvent(
                                course.getClassName(),
                                "特別講義",
                                event.getEventDate(),
                                event.getEventPeriod(),
                                event.getDescription(),
                                "SPECIAL_LECTURE"
                        ));
                        break;

                    // OTHER など、他のケースもここに追加可能
                }
            }
        }
        return fullCalendarEvents;
    }

    /**
     * FullCalendar用のイベントオブジェクトを生成するヘルパーメソッド
     */
    private Map<String, Object> createCalendarEvent(String courseName, String eventTypeJp, LocalDate date, int period, String description, String eventTypeForColor) {
        Map<String, Object> calendarEvent = new HashMap<>();

        LocalTime startTime = getTimeForPeriod(period);
        LocalTime endTime = startTime.plusMinutes(90); // 1コマ90分と仮定
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

        calendarEvent.put("title", "(" + eventTypeJp + ") " + courseName);
        calendarEvent.put("start", startDateTime.toString());
        calendarEvent.put("end", endDateTime.toString());
        calendarEvent.put("description", description);
        calendarEvent.put("color", getColorForEventType(eventTypeForColor)); // 色を設定

        return calendarEvent;
    }

    /**
     * イベント種別に応じたカラーコードを返すヘルパーメソッド
     */
    private String getColorForEventType(String eventType) {
        switch (eventType) {
            case "CANCELLATION":
                return "#d9534f"; // 赤系 (休講)
            case "MAKEUP_CLASS":
                return "#5cb85c"; // 緑系 (振替)
            case "SPECIAL_LECTURE":
                return "#5bc0de"; // 水色系 (特別講義)
            case "OTHER":
            default:
                return "#777777"; // グレー (その他)
        }
    }

    /**
     * コマ数に応じた開始時刻を返すヘルパーメソッド
     */
    private LocalTime getTimeForPeriod(int period) {
        switch (period) {
            case 1: return LocalTime.of(9, 30);
            case 2: return LocalTime.of(11, 10);
            case 3: return LocalTime.of(13, 40);
            case 4: return LocalTime.of(15, 20);
            default: return LocalTime.of(9, 30); // 不正な値の場合はデフォルト値を返す
        }
    }
}