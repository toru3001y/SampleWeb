package com.example.demo.common;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * アプリケーション共通クラス
 *
 * @author t.y
 */
public class ApplicationMessage {

    /**
     * メッセージIDからメッセージを取得する。
     * @param messageSource メッセージソース
     * @param key メッセージキー
     * @param params 置換文字列群
     * @return メッセージ
     */
    public static String getMessage(MessageSource messageSource,
                                    String key, Object... params) {
        return messageSource.getMessage(key, params, Locale.JAPAN);
    }
}
