package org.example.system.service.impl;

import javax.annotation.Resource;

import org.example.common.core.domain.entity.SysUser;
import org.example.system.mapper.SysUserMapper;
import org.example.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户 业务层处理
 */
@Slf4j
@Service
public class SysUserServiceImpl implements ISysUserService {
    @Resource
    private SysUserMapper userMapper;

    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUserSelective(user);
    }
}
