package com.example.classcancellationmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Spring Security の設定クラスです。
 * アプリケーションのセキュリティ（認証・認可）に関する設定を行います。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * セキュリティフィルターチェーンを定義します。
     * URL ごとのアクセス制御、ログイン・ログアウト処理などを設定します。
     * @param http HttpSecurity オブジェクト
     * @return 設定済みの SecurityFilterChain
     * @throws Exception 設定中に発生した例外
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // "/admin/**" へのアクセスは "ADMIN" 権限を持つユーザーのみ許可
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // "/student/**" へのアクセスは "STUDENT" 権限を持つユーザーのみ許可
                .requestMatchers("/student/**").hasRole("STUDENT")
                // その他のリクエストはすべて認証が必要
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // ログインページのパス
                .loginPage("/login")
                // ログイン成功時のハンドラー
                .successHandler(authenticationSuccessHandler())
                // ログイン失敗時のリダイレクト先
                .failureUrl("/login?error")
                // ログインページは誰でもアクセス可能
                .permitAll()
            )
            .logout(logout -> logout
                // ログアウトは誰でもアクセス可能
                .permitAll());
        return http.build();
    }

    /**
     * ログイン成功時のリダイレクト処理を定義します。
     * ユーザーの権限に応じてリダイレクト先を決定します。
     * @return AuthenticationSuccessHandler の実装
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin/events");
            } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
                response.sendRedirect("/student/events");
            } else {
                response.sendRedirect("/login");
            }
        };
    }

    /**
     * パスワードエンコーダーとして BCrypt を使用します。
     * @return BCryptPasswordEncoder のインスタンス
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
