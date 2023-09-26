package com.example.demo.entity.converter;

import com.example.demo.constant.UserStatusKind;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * ユーザ情報 ユーザ状態種別フィールドConverter
 *
 * @author t.y
 */
@Converter
public class UserStatusConverter implements AttributeConverter<UserStatusKind, Boolean>{

    /**
     * 引数で受け取ったユーザ状態種別Enumを利用可否のbooleanに変換します。
     *
     * @param ユーザ状態種別Enum
     * @return 引数で受け取ったユーザ状態種別Enumに保持されているboolean
     */
    @Override
    public Boolean convertToDatabaseColumn(UserStatusKind userStatus) {
        return userStatus.isDisabled();
    }

    /**
     * 引数で受け取った状態種別のコード値をユーザ状態種別Enumに変換します。
     *
     * @param 利用可否(利用不可ならtrue)
     * @return 引数で受け取った状態種別のコード値から逆引きしたユーザ状態種別Enum
     */
    @Override
    public UserStatusKind convertToEntityAttribute(Boolean isDisable) {
        return isDisable ? UserStatusKind.DISABLE : UserStatusKind.ENABLE;
    }
}
