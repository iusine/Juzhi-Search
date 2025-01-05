package com.github.jusearch.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author iusie
 * @description 用户登录实体
 * @date 2024/12/27
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -4172831028042510624L;
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}

