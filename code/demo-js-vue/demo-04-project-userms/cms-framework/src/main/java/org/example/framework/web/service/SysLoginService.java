package org.example.framework.web.service;

import javax.annotation.Resource;

import org.example.common.constant.UserConstants;
import org.example.common.core.domain.model.LoginUser;
import org.example.common.exception.user.UserNotExistsException;
import org.example.common.exception.user.UserPasswordNotMatchException;
import org.example.common.utils.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 登录校验方法
 */
@Service
public class SysLoginService {
    @Resource
    private TokenService tokenService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        // TODO 验证码校验
        // 登录前置校验
        loginPreCheck(username, password);
        // TODO 用户验证
        LoginUser loginUser = null;
        // TODO 记录登录信息
        // 生成token
        return tokenService.createToken(loginUser);
    }

    private void loginPreCheck(String username, String password) {
        // 用户名或密码为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            // TODO 记录日志
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
            || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            // TODO 记录日志
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
            || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            // TODO 记录日志
            throw new UserPasswordNotMatchException();
        }
        // TODO IP黑名单校验
    }
}
