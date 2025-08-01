package com.example.classcancellationmanager.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Spring SecurityのUserDetailsを拡張したカスタムクラスです。
 * アプリケーション固有のユーザー情報（例: ユーザーID）を保持します。
 */
public class UserDetailsImpl extends User {

    /**
     * データベース上のユーザーID
     */
    private final Long userId;

    /**
     * コンストラクタ
     * @param userId ユーザーID
     * @param username ユーザー名
     * @param password パスワード
     * @param authorities 権限リスト
     */
    public UserDetailsImpl(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    /**
     * ユーザーIDを取得します。
     * @return ユーザーID
     */
    public Long getUserId() {
        return userId;
    }
}
