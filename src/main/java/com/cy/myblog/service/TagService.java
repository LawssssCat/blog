package com.cy.myblog.service;

import com.cy.myblog.common.vo.PageVo;
import com.cy.myblog.pojo.po.Tag;
import com.cy.myblog.pojo.po.Type;

public interface TagService {

	int doInsertObject(Tag tag);
	boolean doIsExistName(String name)  ;
	PageVo<Tag> doFindPageObject(String name, Integer pageCurrent);
	int doDeleteObjects(Integer ... ids);
	int doUpdateObject(Type type);
	Tag doFindObjectById(Integer id); 
}
