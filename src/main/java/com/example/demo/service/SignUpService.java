package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignUpForm;

/**
 * ユーザ登録画面 Serviceインタフェース
 */

public interface SignUpService {

    /**
     * 画面の入力情報を元にユーザ情報テーブルの新規登録を行います。
     *
     * <p>ただし、以下のテーブル項目はこの限りではありません。
     * <ul>
     * <li>パスワード：画面で入力したパスワードがハッシュ化され登録されます。</li>
     * <li>権限：常に「商品情報の確認が可能」のコード値が登録されます。</li>
     * </ul>
     *
     * @param form 画面入力情報
     * @return 登録情報(ユーザ情報Entity)、既に同じログインIDが登録済の場合はEmpty
     */
    public Optional<UserInfo> registerUserInfo(SignUpForm form);
}
