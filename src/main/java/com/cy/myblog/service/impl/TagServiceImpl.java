package com.cy.myblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cy.myblog.common.config.PaginationProperties;
import com.cy.myblog.common.utils.Assert;
import com.cy.myblog.common.vo.PageVo;
import com.cy.myblog.dao.TagDao;
import com.cy.myblog.dao.TypeDao;
import com.cy.myblog.pojo.po.Tag;
import com.cy.myblog.pojo.po.Type;
import com.cy.myblog.service.TagService;
import com.cy.myblog.service.TypeService;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class TagServiceImpl implements TagService {
	
	@Autowired
	private TagDao tagDao ;
	
	@Autowired
	private PaginationProperties paginationProperties ; 
	
	@Override
	public int doInsertObject(Tag tag) {
		Assert.isArgumentValid(tag==null, "不能为空!");
		Assert.isArgumentValid(StringUtils.isEmpty(tag.getName()), "名字不能为空!");
		Assert.isDuplicationKey(doIsExistName(tag.getName()), "名字重复!");
		int rows = 
				tagDao.insertObject(tag) ;  
		Assert.isServiceValid(rows==0, "插入失败!") ;
		return rows;
	}


	@Override
	public boolean doIsExistName(String name) {
		int n = tagDao.countObjectByName(name);
		return n>0;
	}

	@Override
	public PageVo<Tag> doFindPageObject(String name, Integer pageCurrent) {
		Assert.isArgumentValid(pageCurrent<1, "页码值异常!");
		Integer startIndex = paginationProperties.getStartIndex(pageCurrent);
		Integer pageSize = paginationProperties.getPageSize();
		Integer totalDataCount = tagDao.countObjectLikeName(name);
		Assert.isServiceValid(totalDataCount==null||totalDataCount==0, "没有数据!");  
		List<Tag> datas = tagDao.findPageObject(name, startIndex, pageSize);
		Assert.isServiceValid(datas==null||datas.size()==0, "没有数据!");  
		return new PageVo<Tag>(datas, totalDataCount, pageCurrent, pageSize);
	}

	@Override
	public int doDeleteObjects(Integer ... ids) {
		Assert.isArgumentValid(ids==null||ids.length<1, "请选择一个!");
		for (Integer id : ids) {
			Assert.isArgumentValid(id<1, "id输入异常!");
		}
		int rows = tagDao.deleteObjects(ids) ;
		Assert.isServiceValid(rows==0, "数据可能不存在了!");
		return rows;
	}

	@Override
	public int doUpdateObject(Type type) {
		Assert.isArgumentValid(type==null||type.getName()==null, "请输入");
		Assert.isArgumentValid(type.getId()<1, "id异常");
		int rows = tagDao.updataObject(type);
		Assert.isServiceValid(rows==0, "数据可能不存在了!");
		return rows;
	}


	@Override
	public Tag doFindObjectById(Integer id) {
		Assert.isArgumentValid(id==null||id<1, "id异常");
		Tag tag = tagDao.findObjectById(id) ;
		Assert.isServiceValid(tag==null||StringUtils.isEmpty(tag.getName()), "数据可能不存在了!");
		return tag;
	}


	

}
