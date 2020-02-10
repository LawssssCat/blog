package com.cy.myblog.service;

import java.util.List;

import com.cy.myblog.common.vo.JsonError;
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

	/**
	 * @param id 修改时候传入,用于排除原有值
	 * @param state 状态(查询信息)
	 * @throws (Json形式)错误信息 集合  
	 */
	void doValidObject(Integer id ,Integer state);

	/**
	 * 插入
	 * @param userState
	 * @return
	 */
	int doSaveObject(UserState userState);

	/**
	 * 修改
	 * @param userState
	 * @return
	 */
	int doUpdateObject(UserState userState);

}
