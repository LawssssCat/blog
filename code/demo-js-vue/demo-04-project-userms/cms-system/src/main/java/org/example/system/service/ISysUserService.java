package org.example.system.service;

import org.example.common.core.domain.entity.SysUser;

/**
 * 用户业务层
 */
public interface ISysUserService {
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser selectUserByUserName(String userName);
}
