package com.example.classcancellationmanager.entity;

import lombok.Data;

/**
 * ユーザー情報を表すエンティティクラスです。
 * `users` テーブルのレコードに対応します。
 */
@Data
public class User {
    /**
     * ユーザーID。主キー。
     */
    private Long userId;

    /**
     * ユーザー名。ログイン時に使用します。
     */
    private String username;

    /**
     * パスワード。ハッシュ化されてデータベースに保存されます。
     */
    private String password;

    /**
     * ユーザーの権限（役割）。"ADMIN" または "STUDENT" など。
     */
    private String role;
}
