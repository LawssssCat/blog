package com.cy.myblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.myblog.common.utils.Assert;
import com.cy.myblog.dao.UserStateDao;
import com.cy.myblog.pojo.po.UserState;
import com.cy.myblog.service.UserStateService;

@Service
public class UserStateServiceImpl implements UserStateService{
	
	@Autowired
	private UserStateDao userStateDao ; 

	@Override
	public List<UserState> doFindObjects() {
		List<UserState> list = 
				userStateDao.selectList(null); 
		Assert.isNoFound(list==null||list.size()==0, "没有数据");
		return list; 
	}

	@Override
	public UserState doFindObjectById(Long id) {
		Assert.isArgumentValid(id==null||id<1, "id异常");
		UserState us = 
				userStateDao.selectById(id);
		Assert.isNoFound(us==null||us.getState()==null, "数据不存在");
		return us;
	}

}
