package com.cy.myblog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.myblog.pojo.po.User;

@Mapper
public interface UserDao {

	int insertObject(User user) ; 
	
	User findObjectById(
			@Param("id") Integer id) ;
	
	User findObjectByUsername(
			@Param("username") String username) ;
	
}
