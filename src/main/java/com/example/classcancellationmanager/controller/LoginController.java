package com.example.classcancellationmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ログイン画面に関するリクエストを処理するコントローラーです。
 */
@Controller
public class LoginController {

    /**
     * ログイン画面を表示します。
     * HTTP GETリクエストで "/login" にアクセスされた場合にこのメソッドが呼び出されます。
     * @return "login.html" をテンプレートとして返す
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
