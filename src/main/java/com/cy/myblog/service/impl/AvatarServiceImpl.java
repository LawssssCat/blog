package com.cy.myblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.myblog.common.utils.Assert;
import com.cy.myblog.dao.AvatarDao;
import com.cy.myblog.pojo.po.Avatar;
import com.cy.myblog.service.AvatarService;

@Service
public class AvatarServiceImpl implements AvatarService {

	@Autowired
	private AvatarDao avatarDao ;
	
	@Override
	public Avatar doFindObjectById(Long id) {
		Assert.isArgumentValid(id==null||id<1, "id异常");
		Avatar avatar = avatarDao.selectById(id);
		Assert.isNoFound(avatar==null||StringUtils.isEmpty(avatar.getUrl()), "图片不存在");
		return avatar;
	}

	
	
}
