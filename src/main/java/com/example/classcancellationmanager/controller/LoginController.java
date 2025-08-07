package com.example.classcancellationmanager.controller;

import com.example.classcancellationmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ログイン画面に関するリクエストを処理するコントローラーです。
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * ログイン画面を表示します。
     * HTTP GETリクエストで "/login" にアクセスされた場合にこのメソッドが呼び出されます。
     * @return "login.html" をテンプレートとして返す
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        boolean success = userService.registerUser(username, password);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "登録が成功しました。");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("message", "登録に失敗しました。ユーザー名が既に存在します。");
            return "redirect:/register";
        }
    }
}
