package com.example.demo.constant;

/**
 * URL 定数クラス
 *
 * @author t.y
 */
public class UrlConst {

    /** ログイン画面 */
    public static final String LOGIN = "/login";

    /** 認証エラー画面 */
    public static final String LOGIN_ERROR = "error";

    /** ユーザ登録画面 */
    public static final String SIGNUP = "/signup";

    /** メニュー画面 */
    public static final String MENU = "/menu";

    /** */
    public static final String USER_LIST = "/userList";


    /** 認証不要画面 */
    public static final String[] NO_AUTHENTICATED = {LOGIN, SIGNUP, "/webjars/**"};
}
