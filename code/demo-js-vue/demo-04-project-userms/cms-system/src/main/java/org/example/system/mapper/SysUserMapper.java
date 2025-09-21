package org.example.system.mapper;

import org.example.common.core.domain.entity.SysUser;

/**
 * 用户表 数据层
 */
public interface SysUserMapper {
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser selectUserByUserName(String userName);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserSelective(SysUser user);
}
