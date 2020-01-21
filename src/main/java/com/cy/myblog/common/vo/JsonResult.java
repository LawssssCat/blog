package com.cy.myblog.common.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class JsonResult implements Serializable {
	private static final long serialVersionUID = 7801773947571843766L;
	
	/**0:异常   1:正常*/
	private Integer status = 1 ;
	/**返回消息*/
	private Object data ;
	/**封装的控制层返回数据*/
	private String message ; 
	
	public JsonResult(Object data) {
		this.data= data ;
	}
	public JsonResult(String message) {
		this.message = message ; 
	}
	public JsonResult(Throwable e) {
		message = e.getMessage() ; 
		status = 0 ; //切换成异常状态
	}
}
