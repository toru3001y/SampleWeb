package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.common.ApplicationMessage;
import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * ログイン画面 Controller
 *
 * @author t.y
 */

@Controller
@RequestMapping(UrlConst.LOGIN)
@RequiredArgsConstructor
public class LoginController {

    /** ログイン画面 Service */
    private final LoginService service;

    /** PasswordEncoder */
    private final PasswordEncoder passwordEncoder;

    /** メッセージソース */
    private final MessageSource messageSource;

    /** セッション情報 */
    private final HttpSession session;


    /**
     * 初期表示
     *
     * @param model モデル
     * @param form 入力情報
     * @return 表示画面
     */
    @GetMapping
    public String view(Model model, LoginForm form) {
        return UrlConst.LOGIN;
    }

    @GetMapping(params = UrlConst.LOGIN_ERROR)
    public String viewWithError(Model model, LoginForm form) {
        var errorInfo = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        model.addAttribute("errorMsg", errorInfo.getMessage());
        return UrlConst.LOGIN;
    }


    /**
     * ログイン
     * @param model モデル
     * @param form 入力情報
     * @return 表示画面
     */
    @PostMapping
    public String login(Model model, LoginForm form) {
        var userInfo = service.findById(form.getLoginId());
        var isCollectUserAuth = userInfo.isPresent()
                && passwordEncoder.matches(form.getPassword(), userInfo.get().getPassword());
        if (isCollectUserAuth) {
            return "redirect:" + UrlConst.MENU;
        } else {
            var errorMsg = ApplicationMessage.getMessage(messageSource, MessageConst.LOGIN_WRONG_INPUT);
            model.addAttribute("errorMsg", errorMsg);
            return UrlConst.LOGIN;
        }
    }
}
