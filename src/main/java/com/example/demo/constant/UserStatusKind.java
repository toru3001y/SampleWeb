package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ユーザ状態種別
 *
 * @author t.y
 */
@Getter
@AllArgsConstructor
public enum UserStatusKind {
    /** 利用可能 */
    ENABLE(false, "利用可能"),
    /** 利用不可 */
    DISABLE(true, "利用不可");
    /** コード値 */
    private boolean isDisabled;
    /** 画面表示する説明文*/
    private String displayValue;
}
