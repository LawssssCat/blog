package com.cy.myblog.pojo.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User implements Serializable {
	private static final long serialVersionUID = 3952133709839414786L;
	private Integer id ; 
	/**账号*/
	private String username ;
	/**密码*/
	private String password ; //md5
	/**盐值*/
	private String salt ; 
	/**昵称*/
	private String nickname ; 
	private String mobile ; 
	private String qq ; 
	private String email ; 
	/**头像-url*/
	private String avatarUrl; 
	/**1=启用,0=禁用*/
	private Integer valid=1; //状态 1=OK 0=禁用

	private Date createdTime  ; 
	private Date modifiedTime ; 
}
