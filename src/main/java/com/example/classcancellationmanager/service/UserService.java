package com.example.classcancellationmanager.service;

import com.example.classcancellationmanager.entity.User;
import com.example.classcancellationmanager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(String username, String password) {
        // ユーザーが既に存在するか確認
        if (userMapper.findByUsername(username) != null) {
            return false; // 既にユーザーが存在する場合は登録失敗
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("STUDENT"); // デフォルトのロールを設定

        userMapper.insert(user);
        return true;
    }
}
