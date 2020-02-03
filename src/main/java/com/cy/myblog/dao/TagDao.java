package com.cy.myblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.myblog.pojo.po.Tag;
import com.cy.myblog.pojo.po.Type;

@Mapper
public interface TagDao {

	int insertObject(Tag tag);

	int countObjectByName(@Param("name") String name);

	Integer countObjectLikeName(@Param("name") String name);

	List<Tag> findPageObject(
			@Param("name") String name, 
			@Param("startIndex") Integer startIndex, 
			@Param("pageSize") Integer pageSize);

	int deleteObjects(@Param("ids") Integer ... ids);

	int updataObject(Type type);

	Tag findObjectById(@Param("id") Integer id);

}
