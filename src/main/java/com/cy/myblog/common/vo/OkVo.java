package com.cy.myblog.common.vo;

import java.io.Serializable;

import com.cy.myblog.common.constant.ServiceState;

import lombok.Data;

@Data
public class OkVo implements Serializable {
	private static final long serialVersionUID = 7801773947571843766L;
	
	/**
	 * 参考 BaseController
	 */
	private Integer state ;
	/**返回消息*/
	private Object data;
	/**封装的控制层返回数据*/
	private String message ; 

	public OkVo() {
		this.state = ServiceState.OK.getCode();
		this.message = ServiceState.OK.getRemark();
	}
	public OkVo(Object data) {
		this();
		this.data= data;
	}
	public OkVo(String message) {
		this();
		this.message = message ; 
	}
	public OkVo(Integer state , Object data) {
		this(data);
		this.state = state; 
	}
	public OkVo(Integer state , String message ,  Object data) {
		this(state, data);
		this.message = message ; 
	}
}
