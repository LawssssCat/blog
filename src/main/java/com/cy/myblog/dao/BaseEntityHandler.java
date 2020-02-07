package com.cy.myblog.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义填充字段:
 * createdTime
 * modifiedTime
 */
@Slf4j
@Component
public class BaseEntityHandler extends MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		setFieldValByName("createdTime", timestamp, metaObject) ; 
		setFieldValByName("modifiedTime", timestamp, metaObject) ; 
		log.debug("insertFull time={}" , timestamp);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		setFieldValByName("modifiedTime",timestamp , metaObject) ; 
		log.debug("updateFull time={}" , timestamp);
	}

}
