package org.example.framework.web.service;

import org.example.common.core.domain.entity.SysUser;
import org.example.common.exception.user.UserPasswordNotMatchException;
import org.example.common.utils.web.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 登录密码方法
 */
@Component
public class SysPasswordService {
    public void validate(SysUser sysUser, Authentication usernamePasswordAuthenticationToken) {
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        // TODO record retry

        if (!SecurityUtils.matchesPassword(password, sysUser.getPassword())) {
            throw new UserPasswordNotMatchException();
        }
    }
}
