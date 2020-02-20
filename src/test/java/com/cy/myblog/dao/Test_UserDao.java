package com.cy.myblog.dao;

import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.myblog.common.constant.UserStateMenu;
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
		
		
		//.setAvatarUrl("https://i.loli.net/2020/02/04/UF9dp6MvGAoaCDS.jpg") //https://avatar.csdnimg.cn/D/A/9/2_lawsssscat_1578788256.jpg
		//.setValid(1);
		User user = 
				new User()
				.setEmail("1191693505@qq.com")
				.setMobile("13710399189")
				.setNickname("lawsssscat")
				.setPassword(password)
				.setQq("1191693505")
				.setSalt(salt)
				.setUsername(username)
				.setUserState(UserStateMenu.ROOT.getCode());
		log.info("before insert user={}" ,user);
		int rows = userDao.insert(user);
		log.info("insert ok ! rows={} , user={}" , rows , user);
		
	}
	
	@Test
	public void test_findObjectById() {
		Integer id = 2 ; //admin
		User user1 = userDao.selectById(id);
		User user2 = userDao.selectById(id);
		log.info("find Object by Id ok!  true={} ,  user={} ," , user1==user2 ,user1.toString());
	}
	
	
}
