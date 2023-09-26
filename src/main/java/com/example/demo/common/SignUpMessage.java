package com.example.demo.common;

import com.example.demo.constant.MessageConst;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignUpMessage {
    SUCCEED(MessageConst.SIGNUP_REGISTER_SUCCESS, false),
    EXISTED_LOGIN_ID(MessageConst.SIGNUP_EXISTED_LOGIN_ID, true);

    private String messageId;
    private boolean isError;
}
