package com.cy.myblog.pojo.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = -6832436200191286228L;
	private Long createdUserId ; 
	private Long modifiedUserId ; 
	private Date createdTime ; 
	private Date modifiedTime ;
	public BaseEntity() {
		Date d = new  Date() ; 
		createdTime = d ; 
		modifiedTime = d ; 
	}
}
