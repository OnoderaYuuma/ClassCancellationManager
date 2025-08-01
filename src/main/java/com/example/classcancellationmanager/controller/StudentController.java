package com.example.classcancellationmanager.controller;

import com.example.classcancellationmanager.entity.User;
import com.example.classcancellationmanager.security.UserDetailsImpl;
import com.example.classcancellationmanager.service.StudentScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 学生画面に関するリクエストを処理するコントローラーです。
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentScheduleService studentScheduleService;

    @Autowired
    private ObjectMapper objectMapper; // JSON変換用のObjectMapper

    /**
     * 学生向けの授業予定一覧画面（カレンダー）を表示します。
     * ログインしている学生の予定を取得し、JSON形式でViewに渡します。
     * @param model Modelオブジェクト
     * @return 学生向けの授業予定一覧画面のテンプレート名
     */
    @GetMapping("/events")
    public String showStudentEvents(Model model) {
        // --- 認証情報の取得 ---
        // SecurityContextHolderから現在の認証情報を取得します。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long studentId = null;

        // 認証情報からUserDetailsを取り出し、学生IDを取得します。
        // Spring Securityの認証プリンシパルがUserDetailsImplのインスタンスであることを期待しています。
        // これは、UserDetailsServiceImplでユーザー情報をロードする際に返されるオブジェクトです。
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            studentId = userDetails.getUserId();
        } else {
            // 認証情報が取得できない、または期待した型でない場合はエラーページなどにリダイレクトすることを推奨
            // ここでは、ひとまずログインページにリダイレクトする例を示します。
            return "redirect:/login";
        }

        // --- イベント情報の取得とJSONへの変換 ---
        // StudentScheduleServiceを使って、学生の予定リストを取得します。
        List<Map<String, Object>> events = studentScheduleService.getStudentEventsForFullCalendar(studentId);

        try {
            // 取得したイベントリストをJSON文字列に変換します。
            String eventsJson = objectMapper.writeValueAsString(events);
            // 変換したJSON文字列を "eventsJson" という名前でModelに追加します。
            model.addAttribute("eventsJson", eventsJson);
        } catch (JsonProcessingException e) {
            // JSONへの変換に失敗した場合のフォールバック処理
            // エラーログを出力し、空のJSON配列を渡すことで、フロントエンドでのエラーを防ぎます。
            e.printStackTrace();
            model.addAttribute("eventsJson", "[]");
        }

        // student_events.htmlテンプレートを返します。
        return "student_events";
    }
}