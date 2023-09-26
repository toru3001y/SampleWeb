package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignUpForm;
import com.example.demo.repository.UserInfoRepository;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;

/**
 * ユーザ登録画面 Service実装クラス
 *
 * @author t.y
 */
@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    /** ユーザ情報テーブルDAO*/
    private final UserInfoRepository repository;

    /** Dozer Mapper */
    private final Mapper mapper;

    /** PasswordEncoder */
    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserInfo> registerUserInfo(SignUpForm form) {
        var userInfoExisted = repository.findById(form.getLoginId());
        if (userInfoExisted.isPresent()) {
            return Optional.empty();
        }
        var userInfo = mapper.map(form, UserInfo.class);
        var encodedPassword = passwordEncoder.encode(form.getPassword());
        userInfo.setPassword(encodedPassword);
        userInfo.setAuthority(AuthorityKind.ITEM_WATCHER);
        return Optional.of(repository.save(userInfo));
    }
}
