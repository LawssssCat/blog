package com.cy.myblog.common.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class JsonResult implements Serializable {
	private static final long serialVersionUID = 7801773947571843766L;
	
	/**
	 * 参考 BaseController
	 * 400:异常   
	 * 200:正常
	 */
	private Integer state = 200 ;
	/**返回消息*/
	private Object data ;
	/**封装的控制层返回数据*/
	private String message ; 

	public JsonResult() {}
	public JsonResult(Object data) {
		this.data= data ;
	}
	public JsonResult(Integer state , Object data) {
		this.state = state ; 
		this.data= data ;
	}
	public JsonResult(Integer state , String message ,  Object data) {
		this.state = state ; 
		this.message = message ; 
		this.data= data ;
	}
	public JsonResult(String message) {
		this.message = message ; 
	}
	public JsonResult(Throwable e) {
		message = e.getMessage() ; 
		state = 400 ; //切换成异常状态
	}
}
