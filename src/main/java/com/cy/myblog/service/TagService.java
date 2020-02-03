package com.cy.myblog.service;

import com.cy.myblog.common.vo.PageObject;
import com.cy.myblog.pojo.po.Tag;
import com.cy.myblog.pojo.po.Type;

public interface TagService {

	int doInsertObject(Tag tag);
	boolean doIsExistName(String name)  ;
	PageObject<Tag> doFindPageObject(String name, Integer pageCurrent);
	int doDeleteObjects(Integer ... ids);
	int doUpdateObject(Type type);
	Tag doFindObjectById(Integer id); 
}
