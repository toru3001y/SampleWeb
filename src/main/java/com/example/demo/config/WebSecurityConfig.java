package com.example.demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.constant.UrlConst;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    /** PasswordEncoder */
    private final PasswordEncoder passwordEncoder;

    /** ユーザ情報取得Service */
    private final UserDetailsService userDetailsService;

    /** メッセージソース */
    private final MessageSource messageSource;

    /** ユーザ名のname属性 */
    private final String USERNAME_PARAMETER = "loginId";

    /***
     * Spring Securityカスタマイズ
     *
     * @param http　カスタマイズパラメータ
     * @return カスタマイズ結果
     * @throws Exception 認証エラー
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(UrlConst.NO_AUTHENTICATED).permitAll()
                    .anyRequest().authenticated()
                )
            .formLogin(login -> login
                    .loginPage(UrlConst.LOGIN)    // 自分で作成したログインページを呼び出すようにする設定
                    .usernameParameter(USERNAME_PARAMETER)  // SpringSecurityのデフォルト設定はusernameとなっている。この行はパラメータの名前をデフォルトから変更する設定。
                    .defaultSuccessUrl(UrlConst.MENU)   // ログイン後のリダイレクト画面はindex.htmlになっている。デフォルトの呼び出し画面を変更する設定
                )
            // デフォルトのログアウト先の画面を変更する設定
            .logout(logout -> logout.logoutSuccessUrl(UrlConst.SIGNUP)
              );
        return http.build();
    }

    /**
     * Provider定義
     *
     * @return カスタマイズProvider情報
     */
    @Bean
    AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setMessageSource(messageSource);   // カスタムメッセージ
        return provider;
    }
}
