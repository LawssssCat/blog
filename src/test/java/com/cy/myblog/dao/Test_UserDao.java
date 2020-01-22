package com.cy.myblog.dao;

import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.myblog.pojo.po.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class Test_UserDao {

	@Autowired
	private UserDao userDao ; 
	
	@Test
	public void test_insertObject() {
		//Universally Unique Identifier
		String salt = UUID.randomUUID().toString();
		String password = "123456" ;
		String username = "admin"; //org.springframework.dao.DuplicateKeyException
		
		//加密
		int hashIterations =1 ;//加密次数
		SimpleHash sh = new SimpleHash("MD5", password, salt, hashIterations);
		password = sh.toHex() ; 
		
		
		User user = 
				new User()
				.setAvatarUrl("https://avatar.csdnimg.cn/D/A/9/2_lawsssscat_1578788256.jpg")
				.setCreatedTime(null)
				.setEmail("1191693505@qq.com")
				.setId(null)
				.setMobile("13710399189")
				.setModifiedTime(null)
				.setNickname("lawsssscat")
				.setPassword(password)
				.setQq("1191693505")
				.setSalt(salt)
				.setUsername(username)
				.setValid(1);
		
		int rows = userDao.insertObject(user);
		log.info("insert ok ! rows={} , user={}" , rows , user.toString());
		
	}
	
	public void test_findObjectById() {
		
	}
	
	
}
