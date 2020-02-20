package com.cy.myblog.service.ex;

import java.util.List;

import com.cy.myblog.common.ex.ServiceException;
import com.cy.myblog.common.vo.ErrorVo;

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
	private List<ErrorVo> errors;

	public PropertyValidFaildException(List<ErrorVo> errors) {
		this.errors=errors ;
	}
	
	public List<ErrorVo> getErrors() {
		return errors;
	}
}
