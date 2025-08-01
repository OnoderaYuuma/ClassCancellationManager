package com.example.classcancellationmanager.service;

import com.example.classcancellationmanager.entity.ClassSchedule;
import com.example.classcancellationmanager.entity.Course;
import com.example.classcancellationmanager.entity.Enrollment;
import com.example.classcancellationmanager.entity.Event;
import com.example.classcancellationmanager.mapper.ClassScheduleMapper;
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
    private ClassScheduleMapper classScheduleMapper;

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
            if (course == null) continue; // 念のためコースが存在しない場合はスキップ

            // 2. 授業に関連するイベントを取得
            List<Event> events = eventMapper.findByClassId(enrollment.getClassId());

            // 3. 授業の週ごとのスケジュールを取得
            List<ClassSchedule> classSchedules = classScheduleMapper.findByClassId(enrollment.getClassId());

            for (Event event : events) {
                // original_date に紐づくイベント（休講、独立した振替授業、教室変更など）を処理
                if (event.getOriginalDate() != null) {
                    for (ClassSchedule classSchedule : classSchedules) {
                        if (event.getOriginalDate().getDayOfWeek().toString().equals(classSchedule.getDayOfWeek())) {
                            // 曜日が一致した場合のみイベントを作成

                            if ("CANCELLATION".equals(event.getEventType())) {
                                // 「休講」イベントを追加
                                fullCalendarEvents.add(createCalendarEvent(
                                        course.getClassName(),
                                        "休講",
                                        event.getOriginalDate(),
                                        classSchedule.getPeriod(),
                                        event.getDescription(),
                                        event,
                                        "CANCELLATION"
                                ));
                            } else {
                                // 「休講」以外のイベント（振替授業、教室変更など）
                                // getEventTypeInJapanese により、「MAKEUP_CLASS」は「振替授業」に変換される
                                fullCalendarEvents.add(createCalendarEvent(
                                        course.getClassName(),
                                        getEventTypeInJapanese(event.getEventType()),
                                        event.getOriginalDate(),
                                        classSchedule.getPeriod(),
                                        event.getDescription(),
                                        event,
                                        event.getEventType()
                                ));
                            }
                            // スケジュールが見つかったのでループを抜ける
                            break;
                        }
                    }
                }

                // 休講に紐づく振替授業は、元のスケジュールとは独立しているため、ここで別途追加
                if ("CANCELLATION".equals(event.getEventType()) && event.getMakeupDate() != null && event.getMakeupPeriod() != null) {
                    fullCalendarEvents.add(createCalendarEvent(
                            course.getClassName(),
                            "振替授業",
                            event.getMakeupDate(),
                            event.getMakeupPeriod(),
                            event.getDescription(),
                            event,
                            "MAKEUP_CLASS"
                    ));
                }
            }
        }
        return fullCalendarEvents;
    }

    /**
     * FullCalendar用のイベントオブジェクトを生成するヘルパーメソッド
     */
    private Map<String, Object> createCalendarEvent(String courseName, String eventTypeJp, LocalDate date, int period, String description, Event originalEvent, String eventTypeForColor) {
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
        calendarEvent.put("extendedProps", originalEvent); // 元のイベント情報を保持

        return calendarEvent;
    }

    /**
     * イベント種別を日本語に変換するヘルパーメソッド
     */
    private String getEventTypeInJapanese(String eventType) {
        switch (eventType) {
            case "CANCELLATION":
                return "休講";
            case "MAKEUP_CLASS":
                return "振替授業";
            case "ROOM_CHANGE":
                return "教室変更";
            case "SPECIAL_LECTURE":
                return "特別講義";
            case "OTHER":
                return "その他";
            default:
                return eventType; // 不明な種別はそのまま返す
        }
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
