package com.cy.myblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.myblog.pojo.po.Type;

@Mapper
public interface TypeDao {
	
	int insertObject(Type type);

	List<Type> findObjects();

	List<Type> findPageObject(
			@Param("name") String name ,
			@Param("startIndex") Integer startIndex , 
			@Param("pageSize") Integer pageSize);

	int countObjectLikeName(@Param("name")  String name) ;
	int countObjectByName(@Param("name") String name) ; 
}
