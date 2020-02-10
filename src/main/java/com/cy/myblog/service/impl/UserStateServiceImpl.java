package com.cy.myblog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cy.myblog.common.utils.Assert;
import com.cy.myblog.common.vo.JsonError;
import com.cy.myblog.dao.UserStateDao;
import com.cy.myblog.pojo.po.UserState;
import com.cy.myblog.service.UserStateService;
import com.cy.myblog.service.ex.PropertyValidFaildException;

@Transactional
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
		Assert.isArgumentValid(id==null||id<1, "state字段异常");
		UserState us = 
				userStateDao.selectById(id);
		Assert.isNoFound(us==null||us.getState()==null, "不存在此用户状态");
		return us;
	}

	@Override
	public void doValidObject(Integer id , Integer state) {
		/**参数校验*/
		Assert.isArgumentValid(state==null||state<1, "state传入异常");
		/**功能实现*/
		ArrayList<JsonError> errors = new ArrayList<JsonError>();
		//检查state是否重复
		UserState us = userStateDao.selectById(state);
		if(us!=null&&us.getState()!=id) errors.add(new JsonError("state", "state字段重复")) ;
		/**异常结果抛出*/
		if(errors!=null&&errors.size()!=0) throw new PropertyValidFaildException(errors) ;
	}

	/**
	 * 一次校验
	 */
	private void assertArgument(UserState userState) {
		/**主键校验*/
		Assert.isArgumentValid(userState==null||userState.getState()==null, "state字段异常");
		/**参数校验*/
		Assert.isArgumentValid(userState.getRemark()==null||"".equals(userState.getRemark()), "描述不能为空");
	}
	
	@Override
	public int doSaveObject(UserState userState) {
		/**一次校验*/
		assertArgument(userState);
		/**二次校验*/
		doValidObject(null,userState.getState());
		/**执行*/
		int rows = userStateDao.insert(userState) ; 
		/**结果校验*/
		Assert.isServiceValid(rows==0, "保存失败");
		return rows;
	}

	@Override
	public int doUpdateObject(UserState userState) {
		/**一次校验*/
		assertArgument(userState);
		/**二次校验*/
		doValidObject(userState.getState(),userState.getState());
		/**执行*/
		int rows = userStateDao.updateAllColumnById(userState);
		/**结果校验*/
		Assert.isServiceValid(rows==0, "数据可能不存在了");
		return rows;
	}

}
