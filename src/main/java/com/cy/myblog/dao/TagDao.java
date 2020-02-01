package com.cy.myblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.myblog.pojo.po.Tag;

@Mapper
public interface TagDao {

	int insertObject(Tag tag);

	int countObjectByName(@Param("name") String name);

	Integer countObjectLikeName(@Param("name") String name);

	List<Tag> findPageObject(
			@Param("name") String name, 
			@Param("startIndex") Integer startIndex, 
			@Param("pageSize") Integer pageSize);

}
