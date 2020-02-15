package com.cy.myblog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cy.myblog.pojo.po.User;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
