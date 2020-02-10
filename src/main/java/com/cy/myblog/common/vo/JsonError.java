package com.cy.myblog.common.vo;

import java.io.Serializable;


import lombok.Data;

@Data
public class JsonError implements Serializable {

	private static final long serialVersionUID = 1551671878993427565L;
	/**错误名*/
	private String name ; 
	/**错误信息*/
	private String message ;
	
	public JsonError(String name , String message) {
		this.name = name ; 
		this.message = message ; 
	}
}
