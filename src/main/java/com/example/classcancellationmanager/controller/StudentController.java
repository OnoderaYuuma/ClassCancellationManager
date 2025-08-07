package com.example.classcancellationmanager.controller;

import com.example.classcancellationmanager.entity.Course;
import com.example.classcancellationmanager.entity.Enrollment;
import com.example.classcancellationmanager.entity.Term;
import com.example.classcancellationmanager.mapper.EnrollmentMapper;
import com.example.classcancellationmanager.mapper.TermMapper;
import com.example.classcancellationmanager.security.UserDetailsImpl;
import com.example.classcancellationmanager.service.CourseService;
import com.example.classcancellationmanager.service.EnrollmentService;
import com.example.classcancellationmanager.service.StudentScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentScheduleService studentScheduleService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private TermMapper termMapper;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/events")
    public String showStudentEvents(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long studentId = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            studentId = userDetails.getUserId();
        } else {
            return "redirect:/login";
        }

        List<Map<String, Object>> events = studentScheduleService.getStudentEventsForFullCalendar(studentId);

        try {
            String eventsJson = objectMapper.writeValueAsString(events);
            model.addAttribute("eventsJson", eventsJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            model.addAttribute("eventsJson", "[]");
        }

        return "student_events";
    }

    @GetMapping("/enrollment")
    public String showEnrollmentScreen(@RequestParam(value = "year", required = false) Integer year,
                                       @RequestParam(value = "termId", required = false) Long termId,
                                       Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long studentId = ((UserDetailsImpl) authentication.getPrincipal()).getUserId();

        int selectedYear = (year != null) ? year : LocalDate.now().getYear();
        long selectedTermId = (termId != null) ? termId : (LocalDate.now().getMonthValue() >= 4 && LocalDate.now().getMonthValue() <= 8 ? 1L : 2L);

        List<Integer> yearList = IntStream.rangeClosed(selectedYear - 1, selectedYear + 1).boxed().collect(Collectors.toList());
        model.addAttribute("yearList", yearList);

        List<Term> termList = termMapper.findAll();
        model.addAttribute("termList", termList);

        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("selectedTermId", selectedTermId);

        List<Course> courses = courseService.searchCourses(selectedYear, selectedTermId, null, null, null);
        model.addAttribute("courses", courses);

        List<Enrollment> enrolledCourses = enrollmentMapper.findByStudentIdAndYearAndTerm(studentId, selectedYear, selectedTermId);
        List<Long> enrolledClassIds = enrolledCourses.stream()
                                                    .map(Enrollment::getClassId)
                                                    .collect(Collectors.toList());
        model.addAttribute("enrolledClassIds", enrolledClassIds);

        return "student/enrollment";
    }

    @PostMapping("/enrollment")
    public String processEnrollment(@RequestParam("academicYear") int year,
                                    @RequestParam("termId") Long termId,
                                    @RequestParam(value = "classIds", required = false) List<Long> classIds) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long studentId = ((UserDetailsImpl) authentication.getPrincipal()).getUserId();

        if (classIds == null) {
            classIds = Collections.emptyList();
        }

        enrollmentService.updateEnrollments(studentId, year, termId, classIds);

        return "redirect:/student/events";
    }

    @GetMapping("/api/courses")
    @ResponseBody
    public List<Course> searchCourses(@RequestParam("year") int year,
                                      @RequestParam("termId") Long termId,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = "courseRuleId", required = false) Long courseRuleId,
                                      @RequestParam(value = "recommendedGrade", required = false) Integer recommendedGrade) {
        return courseService.searchCourses(year, termId, keyword, courseRuleId, recommendedGrade);
    }
}