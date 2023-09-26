package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;

/**
 * ユーザ一覧画面Serviceクラス
 *
 * @author t.y
 */
@Service
@RequiredArgsConstructor
public class UserListServiceImpl implements UserListService {

    /** ユーザ情報テーブルDAO */
    private final UserInfoRepository repository;
    /** Dozer Mapper */
    private final Mapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserListInfo> editUserList() {
        return toUserListInfos(repository.findAll());
    }

    /**
     * ユーザ情報Entityのリストをユーザ一覧情報DTOのリストに変換します。
     *
     * @param userInfos ユーザ情報Entityのリスト
     * @return ユーザ一覧情報DTOのリスト
     */
    private List<UserListInfo> toUserListInfos(List<UserInfo> userInfos) {
        var userListInfos = new ArrayList<UserListInfo>();
        for (UserInfo userInfo : userInfos) {
           var userListInfo = mapper.map(userInfo, UserListInfo.class);
            userListInfo.setStatus(userInfo.getStatus().getDisplayValue());
            userListInfo.setAuthority(userInfo.getAuthority().getDisplayValue());
            userListInfos.add(userListInfo);
        }
        return userListInfos;
    }
}
