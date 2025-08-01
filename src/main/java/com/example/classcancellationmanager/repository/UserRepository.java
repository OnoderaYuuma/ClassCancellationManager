package com.example.classcancellationmanager.repository;

import com.example.classcancellationmanager.entity.User;
import com.example.classcancellationmanager.mapper.UserMapper;
import org.springframework.stereotype.Repository;

/**
 * ユーザー情報へのアクセスを行うリポジトリクラスです。
 * MyBatis の `UserMapper` を利用してデータベースとやり取りします。
 */
@Repository
public class UserRepository {
    private final UserMapper userMapper;

    /**
     * `UserMapper` をインジェクションしてリポジトリを初期化します。
     * @param userMapper MyBatis のユーザーマッパー
     */
    public UserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * ユーザー名でユーザーを検索します。
     * @param username 検索するユーザー名
     * @return ユーザー情報。見つからない場合は null。
     */
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
