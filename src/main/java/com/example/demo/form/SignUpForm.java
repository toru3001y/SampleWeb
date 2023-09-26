package com.example.demo.form;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * ログイン画面 Form
 *
 * @author t.y
 */
@Data
public class SignUpForm {
    /** ログインID */
    @Length(min=8, max=20)
    private String loginId;
    /** パスワード */
    @Length(min=8, max=20)
    private String password;
}
