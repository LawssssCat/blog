package com.cy.myblog.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.myblog.common.config.PaginationProperties;
import com.cy.myblog.common.utils.Assert;
import com.cy.myblog.common.vo.PageVo;
import com.cy.myblog.dao.AvatarDao;
import com.cy.myblog.pojo.po.Avatar;
import com.cy.myblog.service.AvatarService;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class AvatarServiceImpl implements AvatarService {

	@Autowired
	private AvatarDao avatarDao ;
	
	@Autowired
	private PaginationProperties paginationProperties ; 
	
	@Override
	public Avatar doFindObjectById(Long id) {
		log.debug("doFindObjectById id={}",id);
		Assert.isArgumentValid(id==null||id<1, "id异常");
		Avatar avatar = 
				avatarDao.selectById(id);
		Assert.isNoFound(avatar==null||StringUtils.isEmpty(avatar.getUrl()), "图片不存在");
		return avatar;
	}

	@Override
	public PageVo<Avatar> doFondPageObject(Integer pageCurrent, String name) {
		log.debug("doFondPageObject pageCurrent={} name={}",pageCurrent,name);
		Assert.isArgumentValid(pageCurrent==null||pageCurrent<1, "页码异常");
		
		/**查询:[分页前]总数据量*/
		QueryWrapper<Avatar> wrapper = //条件 name
				new QueryWrapper<Avatar>()
				.like(name!=null&&!"".equals(name),"name", name);
		Integer totalDataCount = //[分页前]总数据量
				avatarDao.selectCount(wrapper);
		
		Assert.isNoFound(totalDataCount==null||totalDataCount==0, "没有数据");
		
		/**查询分页*/
		Integer pageSize = 		//以后再定制化
				paginationProperties.getPageSize();
//		int offset = 			//开始位置
//				paginationProperties.getStartIndex(pageCurrent);
		PageHelper.startPage(pageCurrent, pageSize ,false) ;
		List<Avatar> list = 
				avatarDao.selectList(wrapper); 
		
		Assert.isNoFound(list==null||list.size()==0, "没有数据!");
		return new PageVo<Avatar>(list, totalDataCount, pageCurrent, pageSize);
	}

	
	
}
