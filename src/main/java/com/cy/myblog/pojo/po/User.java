package com.cy.myblog.pojo.po;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("user")
public class User extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 3952133709839414786L;
	@TableId(type = IdType.AUTO)
	private Long id ; 
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
	private String remark ; 
	private String address ; 
	private Date birthday ; 
	/**头像-url*/
	private Long avatarId;
	/**200正常*/
	private Integer userState ;  
}
