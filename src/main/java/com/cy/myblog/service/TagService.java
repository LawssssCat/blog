package com.cy.myblog.service;

import com.cy.myblog.common.vo.PageObject;
import com.cy.myblog.pojo.po.Tag;

public interface TagService {

	int doInsertObject(Tag tag);
	boolean doIsExistName(String name)  ;
	PageObject<Tag> doFindPageObject(String name, Integer pageCurrent); 
}
