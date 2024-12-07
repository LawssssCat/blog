package org.example.framework.web.service;

import javax.annotation.Resource;

import org.example.common.core.domain.entity.SysUser;
import org.example.system.service.ISysUserService;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.selectUserByUserName(username);
        return null;
    }
}
