package com.cy.myblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.myblog.common.utils.Assert;
import com.cy.myblog.dao.UserDao;
import com.cy.myblog.pojo.po.User;
import com.cy.myblog.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	

	@Override
	public User findObjectByName(String username) {
		
		Assert.isArgumentValid(StringUtils.isEmpty(username), "没有用户名");
		
		//查询
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq("username", username) ;
		User user = userDao.selectOne(queryWrapper);
		
		Assert.isServiceValid(ObjectUtils.isEmpty(user), "没有用户");
		
		return user;
	}
	

}
