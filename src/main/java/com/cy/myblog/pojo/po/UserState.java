package com.cy.myblog.pojo.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.experimental.Accessors;



@TableName("user_state")
public class UserState extends State implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9058807834687831832L;

	@Override
	public State setRemark(String remark) {
		// TODO Auto-generated method stub
		return super.setRemark(remark);
	}
	
}
