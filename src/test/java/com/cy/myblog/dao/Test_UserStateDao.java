package com.cy.myblog.dao;

import java.util.List;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cy.myblog.pojo.po.UserState;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class Test_UserStateDao {

	@Autowired
	private UserStateDao userStateDao ; 
	
	
	@Test
	public void test_BaseDao_insert() {
		UserState userState = 
				(UserState) new UserState()
				.setState(404)
				.setRemark("不存在"); 
		int rows = userStateDao.insert(userState) ; 
		log.info("rows={}, userState={}" , rows, userState);
	}
	
	@Test
	public void test_BaseDao_select() {
		List<UserState> list = userStateDao.selectList(null);
		list.forEach(l -> log.info(l.toString())) ; 
	}
	
}
