package org.example.thrift.user.service;

import org.apache.thrift.TException;
import org.example.thrift.user.UserInfo;
import org.example.thrift.user.UserService;
import org.example.thrift.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService.Iface {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {
        return userMapper.getUserInfoById(id);
    }

    @Override
    public UserInfo getUserByUsername(String username) throws TException {
        return userMapper.getUserInfoByUsername(username);
    }

    @Override
    public void registerUser(UserInfo userInfo) throws TException {
        userMapper.insertUserInfo(userInfo);
    }
}
