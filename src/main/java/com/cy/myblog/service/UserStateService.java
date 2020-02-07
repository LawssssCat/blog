package com.cy.myblog.service;

import java.util.List;


import com.cy.myblog.pojo.po.UserState;


public interface UserStateService {

	/**
	 * 列表展示 userState 
	 */
	List<UserState> doFindObjects()  ;

	/**
	 * 根据 id 查询 userState 
	 */
	UserState doFindObjectById(Long id);

}
