package com.cy.myblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.myblog.common.vo.PageVo;
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
	PageVo<Avatar> doFondPageObject(Integer pageCurrent, String name);

}
