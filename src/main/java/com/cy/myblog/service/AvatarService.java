package com.cy.myblog.service;

import com.cy.myblog.common.vo.Page;
import com.cy.myblog.pojo.po.Avatar;

public interface AvatarService {

	/**
	 * 查 根据id
	 * @param id
	 * @return 头像对象
	 */
	Avatar doFindObjectById(Long id);

	/**
	 * 查 分页
	 * @param pageCurrent 当前页
	 * @param name 名字 模糊查询 可 null
	 * @return 分页对象
	 */
	Page<Avatar> doFondPageObject(Integer pageCurrent, String name);

}
