package com.cy.myblog.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
		log.info("insert rows={}, userState={}" , rows, userState);
	}
	
	@Test
	public void test_BaseDao_update() {
		UserState entity = 
				(UserState) new UserState()
				.setState(404)
				.setRemark("已删除");
		Wrapper<UserState> wrapper = 
				new EntityWrapper<UserState>()
				.eq("state", entity.getState()) ; 
		Integer rows = userStateDao.update(entity, wrapper); 
		log.info("update rows={} , entity={} , wrapper={}" , rows , entity , wrapper);
	}
	
	@Test
	public void test_BaseDao_delete() {
		Integer rows = userStateDao.deleteById(404);
		log.info("delete rows={}" , rows);
	}
	
	@Test
	public void test_BaseDao_select() {
		List<UserState> list = userStateDao.selectList(null);
		list.forEach(l -> log.info("find {}" , l.toString())) ; 
	}
	
	
}
