package com.cy.myblog.common.constant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

/**
 * 用户的状态。
 * 
 * <p>code反映用户的权限,code越低权限越高</p>
 * 
 * @author uu
 *
 */
public enum UserStateMenu {
	
	/**
	 * 删库到跑路 100
	 */
	ROOT(100, "最高权限"),
	/**
	 * 部分后台管理 200
	 */
	USER(200, "普通用户"),
	/**
	 * 无法进入后台 300
	 */
	GUEST(300, "游客"),
	/**
	 * 禁用-一切 400
	 */
	LIMITATION(400, "禁用");
	
	private final int code ;
	
	private final String remark ; 
	
	private UserStateMenu(int code, String remark) {
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
		return this.code+":"+remark ;
	}

	/**
	 * 根据 stateCode 获取相依的 枚举对象
	 * @Nullable
	 */
	@Nullable
	public static UserStateMenu resolve(int stateCode) {
		for (UserStateMenu userState : UserStateMenu.values()) {
			if(userState.code == stateCode) {
				return userState ;
			}
		}
		return null ; 
	}

	/**
	 * 获取全部 UserState 对象
	 * 封装所有 枚举信息
	 * @return 内置对象 UserState 的数组
	 */
	public static UserState[] getUserStateList() {
		UserStateMenu[] values = UserStateMenu.values();
		UserState[] us = new UserState[values.length] ;
		for (int i=0; i<us.length; i++) {
			us[i] = new UserState(values[i].code,values[i].remark);
		}
		return us ;
	}
	
	public static class UserState{
		private Integer code ; 
		private String remark ;
		
		public UserState(Integer code, String remark) {
			super();
			this.code = code;
			this.remark = remark;
		}
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		} 
		
	}
}
