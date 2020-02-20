package com.cy.myblog.pojo.po;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = -6832436200191286228L;
	@TableField(fill = FieldFill.INSERT)
	private Long createdUserId ; 
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long modifiedUserId ; 
	@TableField(fill = FieldFill.INSERT)
	private Date createdTime ;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date modifiedTime ;
}
