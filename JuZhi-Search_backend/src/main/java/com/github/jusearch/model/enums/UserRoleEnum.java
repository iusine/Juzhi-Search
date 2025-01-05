package com.github.jusearch.model.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author iusie
 * @description 用户角色枚举
 * @date 2024/12/27
 */

@Getter
public enum UserRoleEnum {

    USER("用户","user"),
    ADMIN("管理员","admin");

    private final String text;

    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static UserRoleEnum getUserRoleValue(String value){
        if (StrUtil.isEmpty(value)){
            return null;
        }else
        {
            Map<String, UserRoleEnum> userRoleEnumMap = new HashMap<>(4);
            userRoleEnumMap.put("user",USER);
            userRoleEnumMap.put("admin",ADMIN);

            return userRoleEnumMap.get(value);
        }
    }
}
