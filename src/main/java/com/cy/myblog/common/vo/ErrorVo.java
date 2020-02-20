package com.cy.myblog.common.vo;

import com.cy.myblog.common.constant.ServiceState;

import lombok.Data;

@Data
public class ErrorVo extends OkVo{

	private static final long serialVersionUID = -4866295965561925812L;
	/**
	 * 错误标识
	 */
	private String token ; 
	public ErrorVo() {
		super();
		setState(ServiceState.ERROR.getCode());
	}
}
