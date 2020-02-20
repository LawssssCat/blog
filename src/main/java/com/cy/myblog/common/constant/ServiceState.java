package com.cy.myblog.common.constant;

/**
 * 服务状态
 * 
 * <p>code反映服务情况,如:OK,ERROR,...</p> 
 * 
 * @author uu
 *
 */
public enum ServiceState {

	/**
	 * 服务正常完成 200
	 */
	OK(200, "OK"),
	/**
	 * 服务异常 500
	 */
	ERROR(500, "error");
	
	private final int code ;
	
	private final String remark ;
	
	private ServiceState(int code, String remark) {
		this.code = code;
		this.remark = remark;
	} 
	
	public int getCode() {
		return code;
	}
	
	public String getRemark() {
		return remark;
	}
	
	@Override
	public String toString() {
		return this.code+":"+this.remark;
	}
}
