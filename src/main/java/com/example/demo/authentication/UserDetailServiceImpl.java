package com.example.demo.authentication;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザ情報生成
 *
 * @author t.y
 */
@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    /** ユーザー情報テーブルDAO */
    private final UserInfoRepository repository;

    /** アカウントロックの継続時間 */
    @Value("${security.locking-time}")
    private int lockingTime;

    /** アカウントロックを行うログイン失敗回数境界値 */
    @Value("${security.locking-border-count}")
    private int lockingBorderCount;

    /**
     * ユーザ情報生成
     *
     * @param username ユーザID
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userInfo = repository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // アカウントロックされた時間を取得し、継続時間が現在時刻を経過しているか判定する
        var accountLockedTime = userInfo.getAccountLockedTime();
        var isAccountLocked = accountLockedTime != null
                && accountLockedTime.plusHours(lockingTime).isAfter(LocalDateTime.now());

        return User.withUsername(userInfo.getLoginId())
                .password(userInfo.getPassword())
                .authorities(userInfo.getAuthority().getCode()) // アカウントの権限
                .disabled(userInfo.getStatus().isDisabled()) // アカウントの有効可否
                .accountLocked(isAccountLocked) // アカウントロック状態
                .build();
    }

    /**
     * 認証失敗時にログイン失敗回数を加算、ロック日時を更新する
     *
     * @param event　イベント情報
     */
    @EventListener
    public void handle(AuthenticationFailureBadCredentialsEvent event) {
        var loginId = event.getAuthentication().getName();
        // AuthenticationFailureBadCredentialsEventはログイン誤りもイベントの対象
        // repository#save()は更新データがなければ登録処理を行ってしまうため、
        // repository#findById(loginId)#ifPresent()とすることでログインIDの間違いの場合はsave対象としないように制御
        repository.findById(loginId).ifPresent(userInfo -> {
            repository.save(userInfo.incrementLoginFailureCount());
            // アカウントロックの回数に到達した場合
            var isReachFailureCount = userInfo.getLoginFailureCount() == lockingBorderCount;
            if (isReachFailureCount) {
                repository.save(userInfo.updateAccountLocked());
            }
        });
    }

    /**
     * 認証成功時にログイン失敗回数をリセットする。
     *
     * @param event イベント情報
     */
    @EventListener
    public void handle(AuthenticationSuccessEvent event) {
        var loginId = event.getAuthentication().getName();
        repository.findById(loginId).ifPresent(usreInfo ->
            repository.save(usreInfo.resetLoginFailureInfo()));
    }
}
