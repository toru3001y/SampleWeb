package com.example.demo.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.common.ApplicationMessage;
import com.example.demo.common.SignUpMessage;
import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignUpForm;
import com.example.demo.service.SignUpService;

import lombok.RequiredArgsConstructor;

/**
 * ユーザ画面 Controller
 *
 * @author t.y
 */

@Controller
@RequestMapping(UrlConst.SIGNUP)
@RequiredArgsConstructor
public class SignUpController {

    /** ユーザ画面 Service */
    private final SignUpService service;

    /** メッセージソース */
    private final MessageSource messageSource;

    /**
     * 初期表示
     *
     * @param model モデル
     * @param form 入力情報
     * @return 表示画面
     */
    @GetMapping
    public String view(Model model, SignUpForm form) {
        return UrlConst.SIGNUP;
    }

    /**
     * ユーザ登録
     * @param model モデル
     * @param form 入力情報
     * @param bindingResult 入力チェック結果
     * @return 表示画面
     */
    @PostMapping
    public String signUp(Model model, @Validated SignUpForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            editMessage(model, MessageConst.FORM_ERROR,true);
            return UrlConst.SIGNUP;
        }
        var userInfo = service.registerUserInfo(form);
        var signUpMessage = editMessageKey(userInfo);
        editMessage(model, signUpMessage.getMessageId(), signUpMessage.isError());
        return UrlConst.SIGNUP;
    }

    /**
     * 画面に表示するメッセージを設定する
     * @param model モデル
     * @param messageId メッセージID
     * @param isError エラー有無
     */
    private void editMessage(Model model, String messageId, boolean isError) {
        var message = ApplicationMessage.getMessage(messageSource, messageId);
        model.addAttribute("message", message);
        model.addAttribute("isError", isError);
    }

    /**
     * ユーザ登録の結果メッセージを判断する
     * @param userInfo ユーザ登録結果(未登録の場合は登録情報(ユーザ情報Entity)、それ以外の場合はEmpty)
     * @return メッセージキー
     */
    private SignUpMessage editMessageKey(Optional<UserInfo> userInfo) {
        if (userInfo.isEmpty()) {
            return SignUpMessage.EXISTED_LOGIN_ID;
        } else {
            return SignUpMessage.SUCCEED;
        }
    }
}
