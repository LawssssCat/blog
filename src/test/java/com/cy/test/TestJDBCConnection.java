package com.cy.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TestJDBCConnection {

	@Autowired
	private SqlSession sqlSession ; 
	
	@Test
	public void test_getConnection() {
		sqlSession.getConnection() ; 
		log.info("connection success!@");
	}
	
}
