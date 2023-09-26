package com.example.demo.entity.converter;

import com.example.demo.constant.AuthorityKind;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


/**
 *  ユーザ情報 ユーザ権限フィールドConverter
 *
 *
 * @author t.y
 */
@Converter
public class UserAuthorityConverter implements AttributeConverter<AuthorityKind, String> {

    /**
     * 引数で受け取ったユーザ権限種別Enumをコード値のStringに変換します。
     *
     * @param ユーザ権限種別Enum
     * @return 引数で受け取ったユーザ権限種別Enumに保持されているコード値
     */
    @Override
    public String convertToDatabaseColumn(AuthorityKind authorityKind) {
        return authorityKind.getCode();
    }

    /**
     * 引数で受け取った権限種別のコード値をユーザ権限種別Enumに変換します。
     *
     * @param 権限種別のコード値
     * @return 引数で受け取った権限種別のコード値から逆引きしたユーザ権限種別Enum
     */
    @Override
    public AuthorityKind convertToEntityAttribute(String code) {
        return AuthorityKind.from(code);
    }
}
