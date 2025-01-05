package com.github.jusearch.model.dto.user;

import lombok.Data;

/**
 * @author iusie
 * @description
 * @date 2024/12/27
 */
@Data
public class UserAddRequest {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
