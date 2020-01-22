package com.cy.myblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.myblog.dao.UserDao;
import com.cy.myblog.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao ; 
	
	@Override
	public void doLogin(String email, String password, boolean isRememberMe) {
		log.debug("doLogin email={} , password={} , isRememberMe={}",email , password , isRememberMe);
	}



}
