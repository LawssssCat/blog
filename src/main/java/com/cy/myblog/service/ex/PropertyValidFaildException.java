package com.cy.myblog.service.ex;

import java.util.List;

import com.cy.myblog.common.ex.ServiceException;
import com.cy.myblog.common.vo.JsonError;

/**
 * 对象属性校验异常
 * @author uu
 *
 */
public class PropertyValidFaildException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2333640951764066884L;
	private List<JsonError> errors;

	public PropertyValidFaildException(List<JsonError> errors) {
		this.errors=errors ;
	}
	
	public List<JsonError> getErrors() {
		return errors;
	}
}
