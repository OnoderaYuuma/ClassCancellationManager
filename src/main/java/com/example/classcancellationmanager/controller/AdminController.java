package com.example.classcancellationmanager.controller;

import com.example.classcancellationmanager.entity.Event;
import com.example.classcancellationmanager.form.ClassForm;
import com.example.classcancellationmanager.form.EventForm;
import com.example.classcancellationmanager.security.UserDetailsImpl;
import com.example.classcancellationmanager.service.CourseService;
import com.example.classcancellationmanager.service.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/events")
    public String getEvents(Model model) {
        model.addAttribute("events", eventService.findAllEvents());
        return "admin/events";
    }

    @GetMapping("/events/new")
    public String newEventForm(Model model) {
        model.addAttribute("eventForm", new EventForm());
        model.addAttribute("courses", courseService.findAllCourses());
        return "admin/event_form";
    }

    @PostMapping("/events")
    public String createEvent(@ModelAttribute EventForm eventForm, BindingResult result, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAllCourses());
            return "admin/event_form";
        }

        Event event = new Event();
        BeanUtils.copyProperties(eventForm, event);
        event.setRegisteredBy(userDetails.getUserId());

        try {
            eventService.saveEvent(event);
        } catch (IllegalArgumentException e) {
            model.addAttribute("courses", courseService.findAllCourses());
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/event_form";
        }

        return "redirect:/admin/events";
    }

    @GetMapping("/classes/new")
    public String newClassForm(Model model) {
        model.addAttribute("classForm", new ClassForm());
        model.addAttribute("courseRules", courseService.findAllCourseRules());
        model.addAttribute("terms", courseService.findAllTerms());
        // 推奨学年やコマ数のリストを渡すことも可能
        return "admin/class_form";
    }

    @PostMapping("/classes")
    public String createClass(@ModelAttribute ClassForm classForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courseRules", courseService.findAllCourseRules());
            model.addAttribute("terms", courseService.findAllTerms());
            return "admin/class_form";
        }

        courseService.createClass(classForm);

        return "redirect:/admin/events"; // 登録後はイベント一覧にリダイレクト
    }
}