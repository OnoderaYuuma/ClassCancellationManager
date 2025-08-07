package com.example.classcancellationmanager.mapper;

import com.example.classcancellationmanager.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * MyBatis のマッパーインターフェースです。
 * `users` テーブルへのアクセスを定義します。
 */
@Mapper
public interface UserMapper {
    /**
     * ユーザー名でユーザーを検索します。
     * @param username 検索するユーザー名
     * @return ユーザー情報。見つからない場合は null。
     */
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO users (username, password, role) VALUES (#{username}, #{password}, #{role})")
    void insert(User user);
}
