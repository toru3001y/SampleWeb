package com.example.demo.form;

import org.hibernate.validator.constraints.Length;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;

import lombok.Data;

/**
 * ユーザ一覧画面
 *
 * @author t.y
 */
@Data
public class UserListForm {
    /**  ログインID */
    @Length(min=8, max=20)
    private String loginId;
    /** アカウント状態種別 */
    private UserStatusKind userStatusKind;
    /** ユーザ権限種別 */
    private AuthorityKind authorityKind;
}
