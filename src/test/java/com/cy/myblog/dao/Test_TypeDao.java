package com.cy.myblog.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.myblog.common.config.PaginationProperties;
import com.cy.myblog.pojo.po.Type;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
public class Test_TypeDao {

	@Autowired
	private TypeDao typeDao ;
	
	@Autowired
	private PaginationProperties paginationProperties ; 
	
	@Test
	public void test_insertObject() {
		Type type = new Type();
		type.setName("Java");
		int rows = typeDao.insertObject(type);
		log.info("insert ok ! rows={}",rows);
		log.info("type={}" , type);
	}
	
	@Test
	public void test_findObjects() {
		List<Type> ts = typeDao.findObjects() ;
		ts.forEach(t -> log.info("type={}" ,t));
	}
	
	@Test
	public void test_findPageObjects() {
		Integer pageCurrent = 1 ; 
		String name = "J" ;
		/**---------------------------------*/
		Integer pageSize= paginationProperties.getPageSize() ;  
		Integer startIndex= paginationProperties.getStartIndex(pageCurrent) ; 
		List<Type> pageObjects = typeDao.findPageObject(name, startIndex, pageSize);
		pageObjects.forEach(t -> log.info("type={}" ,t));
	}
	
}
