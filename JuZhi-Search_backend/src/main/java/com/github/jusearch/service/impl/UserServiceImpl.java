package com.github.jusearch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jusearch.exception.BusinessException;
import com.github.jusearch.exception.StateCode;
import com.github.jusearch.model.dto.user.UserLoginRequest;
import com.github.jusearch.model.dto.user.UserQueryRequest;
import com.github.jusearch.model.dto.user.UserRegisterRequest;
import com.github.jusearch.model.entity.User;
import com.github.jusearch.model.enums.UserRoleEnum;
import com.github.jusearch.model.vo.user.LoggingUserVO;
import com.github.jusearch.model.vo.user.UserVO;
import com.github.jusearch.service.UserService;
import com.github.jusearch.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.github.jusearch.constant.UserConstant.LOGGING_STATE;
import static com.github.jusearch.constant.UserConstant.SALT;

/**
* @author iusine
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-01-05 00:11:37
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 用户注册
     *
     * @param userRegisterRequest 注册信息实体
     * @return 返回用户id
     */
    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String Email = userRegisterRequest.getEmail();
        //账号，密码 不能为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "账号或密码为空");
        }
        // 账号校验
        if (userAccount.length() < 4) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "账号长度小于4");
        }
        // 密码校验
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "密码长度小于8");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "校验密码不一致");
        }
        //账号非法字符校验
        String validPattern = "[\\u4e00-\\u9fa5\\u00A0\\s\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "账号含非法字符");
        }
        if (StringUtils.isNotBlank(Email) && !Validator.isEmail(Email)) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "邮箱号格式错误");
        }
        //账号不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "账号重复");
        }
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //随机生成用户名
        String userName = "用户_" + RandomUtil.randomString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", 6);
        //插入数据
        User user = new User();
        user.setUserAccount("https://jsd.onmicrosoft.cn/gh/iusie/image/user.svg");
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUsername(userName);
        user.setEmail(Email);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(StateCode.SYSTEM_ERROR, "注册失败, 数据插入错误");
        }
        return user.getId();
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户账号密码实体
     * @param request          请求体
     * @return LoggingUserVO
     */
    @Override
    public LoggingUserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        //账号，密码 不能为空
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "登录参数为空");
        }
        // 账号校验
        if (userAccount.length() < 4) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "账号长度小于4");
        }
        //账号非法字符校验
        String validPattern = "[\\u00A0\\s\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "账号非法");
        }
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //查询用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount).eq("userPassword", encryptPassword);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            log.info("userAccount or userPassword err");
            throw new BusinessException(StateCode.PARAMS_ERROR, "用户不存在或者账号密码错误");
        }
        request.getSession().setAttribute(LOGGING_STATE, user);

        return getLoggingUserVO(user);
    }

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUserPermitNull(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(LOGGING_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        return this.getById(userId);
    }
    /**
     * 获取用户权限和信息
     *
     * @param request 请求体
     * @return User
     */
    @Override
    public User getLoggingUser(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(LOGGING_STATE);
        User user = (User)attribute;
        if (user == null || user.getId() == null){
            throw new BusinessException(StateCode.NOT_LOGIN_ERROR, "用户未登录");
        }
        // 从数据库查询（追求性能的话可以注释，直接返回上述结果）
        long userId = user.getId();
        user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(StateCode.NOT_LOGIN_ERROR);
        }
        return user;
    }


    /**
     * 用户信息脱敏
     *
     * @param user 用户实体类
     * @return LoggingUserVO
     */
    @Override
    public LoggingUserVO getLoggingUserVO(User user) {
        if (user == null) {
            return null;
        }
        return LoggingUserVO.objToVo(user);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(LOGGING_STATE);
        if (userObj == null) {
            throw new BusinessException(StateCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(LOGGING_STATE);
        return true;
    }

    /**
     * 获得脱敏后的用户信息
     *
     * @param user
     * @return
     */
    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 获取脱敏后的用户列表
     *
     * @param userList
     * @return
     */
    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream()
                .map(this::getUserVO)
                .collect(Collectors.toList());
    }

    @Override
    public Wrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(StateCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String userName = userQueryRequest.getUserName();
        String userAccount = userQueryRequest.getUserAccount();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNotNull(id), "id", id)
                .eq(StrUtil.isNotBlank(userRole), "userRole", userRole)
                .like(StrUtil.isNotBlank(userAccount), "userAccount", userAccount)
                .like(StrUtil.isNotBlank(userName), "userName", userName)
                .like(StrUtil.isNotBlank(userProfile), "userProfile", userProfile)
                .orderBy(StrUtil.isNotEmpty(sortField), sortOrder.equals("ascend"), sortField);
        return queryWrapper;
    }


    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(LOGGING_STATE);
        User user = (User) userObj;
        return isAdmin(user);
    }
}




