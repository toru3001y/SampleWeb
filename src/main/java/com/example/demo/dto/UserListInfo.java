package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * ユーザー一覧画面DTOクラス
 *
 * @author t.y
 *
 */
@Data
public class UserListInfo {

    /** ログインID */
    private String loginId;

    /** ログイン失敗回数 */
    private int loginFailureCount;

    /** アカウントロック日時 */
    private LocalDateTime accountLockedTime;

    /** アカウント状態 */
    private String status;

    /** 権限 */
    private String authority;

}