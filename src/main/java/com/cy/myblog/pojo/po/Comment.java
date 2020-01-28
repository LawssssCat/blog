package com.cy.myblog.pojo.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Comment implements Serializable {
	private static final long serialVersionUID = -6360401291243978852L;

	private Integer id ; 
	/**上一级评论*/
	private Integer parentId ; 
	/**根据userId,获取avatar头像、username、email*/
	private Integer createUserId ;
	private Date createdTime ;
	private String content ; 
}
