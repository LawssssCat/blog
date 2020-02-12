package com.cy.myblog.service;

import com.cy.myblog.common.vo.Page;
import com.cy.myblog.pojo.po.Type;

public interface TypeService {

	int doInsertObject(Type type);

	/**id以外的名字,是否重复*/
	boolean doIsExistName(String name, Integer id ) ;

	/**查分页*/
	Page<Type> doFindPageObject(String name, Integer pageCurrent);

	int doDeleteObject(Integer id);

	int doUpdateObject(Type type);

	Type dofindObjectById(Integer id);
}
