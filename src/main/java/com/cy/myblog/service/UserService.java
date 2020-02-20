package com.cy.myblog.service;

import com.cy.myblog.pojo.po.User;

public interface UserService {

	User findObjectByName(String username);
	
}
