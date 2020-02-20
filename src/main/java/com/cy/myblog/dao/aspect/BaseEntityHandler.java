package com.cy.myblog.dao.aspect;

import java.sql.Timestamp;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cy.myblog.common.utils.SubjectUtils;
import com.cy.myblog.pojo.po.User;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义填充字段:
 * createdTime
 * modifiedTime
 */
@Slf4j
@Component
public class BaseEntityHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		User user = SubjectUtils.getUser();
		Long id = user!=null? user.getId() : null ;  
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		setFieldValByName("createdTime", timestamp, metaObject) ; 
		setFieldValByName("createdUserId", id, metaObject);
		setFieldValByName("modifiedTime", timestamp, metaObject) ; 
		setFieldValByName("modifiedUserId", id, metaObject);
		log.debug("insertFull time={}" , timestamp);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		User user = SubjectUtils.getUser();
		Long id = user!=null? user.getId() : null ; 
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		setFieldValByName("modifiedTime",timestamp , metaObject) ;
		setFieldValByName("modifiedUserId", id, metaObject);
		log.debug("updateFull time={}" , timestamp);
	}

}
