package com.example.classcancellationmanager.security;

import com.example.classcancellationmanager.entity.User;
import com.example.classcancellationmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

/**
 * Spring Security の `UserDetailsService` を実装したクラスです。
 * ユーザー名からユーザー情報を取得し、認証のための `UserDetails` オブジェクトを生成します。
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * `UserRepository` をインジェクションしてサービスを初期化します。
     * @param userRepository ユーザーリポジトリ
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * ユーザー名でユーザー情報をロードします。
     * @param username ログインフォームで入力されたユーザー名
     * @return ユーザーの詳細情報 (UserDetails)
     * @throws UsernameNotFoundException ユーザーが見つからない場合にスローされます
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // 新しく作成した UserDetailsImpl オブジェクトを生成して返す
        return new UserDetailsImpl(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                // 権限情報を "ROLE_" プレフィックス付きで設定
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
