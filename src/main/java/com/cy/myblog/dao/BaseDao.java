package com.cy.myblog.dao;

import java.util.List;

public interface BaseDao<T> {

	List<T> findObjects();
}
