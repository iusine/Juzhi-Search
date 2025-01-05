package com.github.jusearch.model.vo.user;

import cn.hutool.json.JSONUtil;
import com.github.jusearch.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author iusie
 * @description 登录的用户信息
 * @date 2024/12/27
 */
@Data
public class LoggingUserVO implements Serializable {

    private static final long serialVersionUID = -1110235170505958882L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户账号
     */
    private String userAccount;


    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 用户自我介绍
     */
    private String userProfile;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 标签 json 列表
     */
    private List<String> tags;

    /**
     * 用户角色 0 - 普通用户 1 - 管理员
     */
    private String userRole;

    /**
     * 封装类转对象
     */
    public static User voToObj(LoggingUserVO loggingUserVO) {
        if (loggingUserVO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(loggingUserVO, user);
        // 类型不同，需要转换
        user.setTags(JSONUtil.toJsonStr(loggingUserVO.getTags()));
        return user;
    }

    /**
     * 对象转封装类
     */
    public static LoggingUserVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        LoggingUserVO loggingUserVO = new LoggingUserVO();
        BeanUtils.copyProperties(user, loggingUserVO);
        // 类型不同，需要转换
        loggingUserVO.setTags(JSONUtil.toList(user.getTags(), String.class));
        return loggingUserVO;
    }


}