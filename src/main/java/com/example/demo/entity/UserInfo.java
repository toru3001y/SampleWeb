package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.entity.converter.UserAuthorityConverter;
import com.example.demo.entity.converter.UserStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー情報テーブル Entity
 *
 * @author t.y
 */
@Entity
@Table(name="user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    /** ユーザID */
    @Id
    @Column(name="login_id")    // TBLの項目名とマッピング
    private String loginId;

    /** パスワード */
    private String password;

    /**  認証失敗回数 */
    @Column(name = "login_failure_count")
    private int loginFailureCount;

    /**  アカウントロック日時 */
    @Column(name = "account_locked_time")
    private LocalDateTime accountLockedTime;

    /**  アカウント利用可否(true:利用可能) */
    @Column(name = "is_disabled")
    @Convert(converter = UserStatusConverter.class)
    private UserStatusKind status;

    /** ユーザ権限 */
    @Convert(converter = UserAuthorityConverter.class)
    private AuthorityKind authority;

    /**
     * ログイン失敗回数をインクリメントする
     *
     * @return ログイン失敗回数がインクリメントされたUserInfo
     */
    public UserInfo incrementLoginFailureCount() {
        return new UserInfo(loginId, password, ++loginFailureCount , accountLockedTime, status, authority);
    }

    /**
     * ログイン失敗情報をリセットする
     *
     * @return　ログイン情報がリセットされたUserInfo
     */
    public UserInfo resetLoginFailureInfo() {
        return new UserInfo(loginId, password, 0, null, status, authority);
    }

    /**
     * アカウントロック状態に更新する
     *
     * @return ログイン失敗回数、アカウントロック日時が更新されたUserInfo
     */
    public UserInfo updateAccountLocked() {
        return new UserInfo(loginId, password, 0, LocalDateTime.now(), status, authority);
    }
}
