package org.example.framework.web.service;

import java.util.Objects;

import javax.annotation.Resource;

import org.example.common.core.domain.entity.SysUser;
import org.example.common.core.domain.model.LoginUser;
import org.example.common.enums.UserStatus;
import org.example.common.exception.ServiceException;
import org.example.common.utils.MessageUtils;
import org.example.framework.security.context.AuthenticationContextHolder;
import org.example.system.service.ISysUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户 验证处理
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private ISysUserService userService;

    @Resource
    private SysPasswordService sysPasswordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.selectUserByUserName(username);

        // 状态校验
        if (Objects.isNull(sysUser)) {
            log.info("登录用户：{} 不存在", username);
            throw new ServiceException(MessageUtils.message("user.exists.not"));
        } else if (UserStatus.DELETED.getCode().equals(sysUser.getStatus())) {
            log.info("登录用户：{} 已被删除", username);
            throw new ServiceException(MessageUtils.message("user.status.deleted"));
        } else if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
            log.info("登录用户：{} 已被停用", username);
            throw new ServiceException(MessageUtils.message("user.status.disable"));
        }

        // 认证校验
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        sysPasswordService.validate(sysUser, usernamePasswordAuthenticationToken);

        // 结果返回
        return new LoginUser(sysUser.getUserId(), null, sysUser, null);
    }
}
