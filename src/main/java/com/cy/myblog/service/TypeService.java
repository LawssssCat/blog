package com.cy.myblog.service;

import com.cy.myblog.common.vo.PageObject;
import com.cy.myblog.pojo.po.Type;

public interface TypeService {

	int doInsertObject(Type type);

	boolean doIsExistName(String name ) ;

	/**查分页*/
	PageObject<Type> doFindPageObject(String name, Integer pageCurrent);

	int doDeleteObject(Integer id);

	int doUpdateObject(Type type);

	Type dofindObjectById(Integer id);
}
