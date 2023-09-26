package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.form.UserListForm;
import com.example.demo.service.UserListService;

import lombok.RequiredArgsConstructor;

/**
 * ユーザ一覧画面Controllerクラス
 *
 * @author t.y
 */
@Controller
@RequestMapping(UrlConst.USER_LIST)
@RequiredArgsConstructor
public class UserListController {
    private final UserListService service;
    /** モデルキー：ユーザ情報リスト*/
    private static final String KEY_USERLIST = "userList";
    /** モデルキー：ユーザ情報リスト */
    private static final String KEY_USER_STATUS_KINDS="userStatusKinds";
    /** モデルキー：権限情報リスト */
    private static final String KEY_AUTHORITY_KINDS = "authorityKinds";

    /**
     * 画面の初期表示を行います。
     *
     * @param model モデル
     * @param form ユーザ一覧画面入力情報
     * @return 表示画面
     */
    @GetMapping
    public String view(Model model, UserListForm form) {
        var userInfos = service.editUserList();
        model.addAttribute(KEY_USERLIST, userInfos);
        model.addAttribute(KEY_USER_STATUS_KINDS, UserStatusKind.values());
        model.addAttribute(KEY_AUTHORITY_KINDS, AuthorityKind.values());
        return UrlConst.USER_LIST;
    }
}
