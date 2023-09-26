package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UrlConst;

/**
 * メニュー画面 Controller
 *
 * @author t.y
 */
@Controller
@RequestMapping(UrlConst.MENU)
public class MenuController {

    /**
     * 初期表示
     * @param user ユーザ情報
     * @param model モデル
     * @return 表示画面
     */
    @GetMapping
    public String view(@AuthenticationPrincipal User user, Model model) {
        var hasUserManageAuth = user.getAuthorities().stream()
                .allMatch(authority -> authority.getAuthority()
                        .equals(AuthorityKind.ITEM_AND_USER_MANAGER.getCode()));
        model.addAttribute("hasUserManageAuth", hasUserManageAuth);
        return UrlConst.MENU;
    }
}
