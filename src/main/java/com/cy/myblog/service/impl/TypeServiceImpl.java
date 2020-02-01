package com.cy.myblog.service.impl;

import java.rmi.ServerException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.myblog.common.config.PaginationProperties;
import com.cy.myblog.common.exception.ServiceException;
import com.cy.myblog.common.utils.Assert;
import com.cy.myblog.common.vo.PageObject;
import com.cy.myblog.dao.TypeDao;
import com.cy.myblog.pojo.po.Type;
import com.cy.myblog.service.TypeService;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class TypeServiceImpl implements TypeService {
	
	@Autowired
	private TypeDao typeDao ;
	
	@Autowired
	private PaginationProperties paginationProperties ; 
	
	@Override
	public int doInsertObject(Type type) {
		Assert.isArgumentValid(type==null, "不能为空!");
		Assert.isEmpty(type.getName(), "名字不能为空!");
		Assert.isArgumentValid(doIsExistName(type.getName()), "名字重复!");
		int rows = typeDao.insertObject(type) ;  
		Assert.isServiceValid(rows==0, "插入失败!") ;
		return rows;
	}

	@Override
	public boolean doIsExistName(String name) {
		int n = typeDao.countObjectByName(name);//准确
		return n>0;
	}

	@Override
	public PageObject<Type> doFindPageObject(String name, Integer pageCurrent) {
		Assert.isArgumentValid(pageCurrent<1, "页码值异常!");
		Integer startIndex = paginationProperties.getStartIndex(pageCurrent);
		Integer pageSize = paginationProperties.getPageSize();
		Integer totalDataCount = typeDao.countObjectLikeName(name);
		Assert.isServiceValid(totalDataCount==null||totalDataCount==0, "没有数据!");  
		List<Type> datas = typeDao.findPageObject(name, startIndex, pageSize);
		Assert.isServiceValid(datas==null||datas.size()==0, "没有数据!");  
		return new PageObject<Type>(datas, totalDataCount, pageCurrent, pageSize);
	}

	@Override
	public int doDeleteObject(Integer id) {
		Assert.isArgumentValid(id<1, "id输入异常!");
		int rows = typeDao.deleteObject(id) ;
		Assert.isServiceValid(rows==0, "数据可能不存在了!");
		return rows;
	}

	@Override
	public int doUpdateObject(Type type) {
		Assert.isArgumentValid(type==null||type.getName()==null, "请输入");
		Assert.isArgumentValid(type.getId()<1, "id异常");
		int rows =0 ; 
		try{
			rows = typeDao.updataObject(type);
		}catch(Exception e) {
			throw new ServiceException("操作异常") ; 
		}
		Assert.isServiceValid(rows==0, "数据可能不存在了!");
		return rows;
	}

	@Override
	public Type dofindObjectById(Integer id) {
		Assert.isArgumentValid(id==null||id<0, "id输入异常");
		Type type = typeDao.findObjectById(id) ;
		Assert.isServiceValid(type==null||type.getName()==null, "数据可能不存在!");
		return type;
	}

	

}
