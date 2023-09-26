package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ログ出力共通クラス
 *
 * @author t.y
 */
@Aspect
@Component
public class CommonLogAspect {

    /** ロガー(Logback) */
    private final Logger log = LoggerFactory.getLogger(CommonLogAspect.class);

    /**
     * 指定したメソッドの開始、終了ログを出力します。
     *
     * <p>例外発生時は例外内容をログに出力します。
     *
     * @param jp 処理を挿入する場所の情報
     * @return 指定したメソッドの戻り値
     */
    @Around("execution(* com.example.demo..*(..))")
    public Object writeLog(ProceedingJoinPoint jp) {
        // 返却オブジェクトの定義
        Object returnObj = null;
        // 開始ログを出力
        log.info("start:" + jp.getSignature().toLongString());
        try {
            // JoinPointのメソッドを実行
            returnObj = jp.proceed();
        } catch (Throwable t) {
            // エラーログを出力
            log.error(t.toString());
        }
        // 終了ログを出力
        log.info("end:" + jp.getSignature().toLongString());
        // 指定したメソッドの戻り値を戻さないと、Controllerクラスの場合、次の画面に遷移が行えない
        return returnObj;
    }


}
