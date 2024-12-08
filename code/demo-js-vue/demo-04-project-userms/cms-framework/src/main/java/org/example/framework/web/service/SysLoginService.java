package org.example.framework.web.service;

import javax.annotation.Resource;

import org.example.common.constant.UserConstants;
import org.example.common.core.domain.entity.SysUser;
import org.example.common.core.domain.model.LoginUser;
import org.example.common.exception.ServiceException;
import org.example.common.exception.user.UserNotExistsException;
import org.example.common.exception.user.UserPasswordNotMatchException;
import org.example.common.utils.DateUtils;
import org.example.common.utils.StringUtils;
import org.example.common.utils.web.IpUtils;
import org.example.framework.security.context.AuthenticationContextHolder;
import org.example.system.service.ISysUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 登录校验方法
 */
@Service
public class SysLoginService {
    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

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
        // 用户验证
        Authentication authentication = null;
        try {
            // 构建认证凭据
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 调用UserDetailsServiceImpl.loadUserByUsername方法，完成认证流程
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                // TODO 记录日志
                throw new UserPasswordNotMatchException();
            } else {
                // TODO 记录日志
                throw new ServiceException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        // 登录信息
        // TODO 记录日志
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    private void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getClientIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        iSysUserService.updateUserProfile(sysUser);
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
