package com.cy.myblog.pojo.po;


import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class Avatar extends BaseEntity {

	private static final long serialVersionUID = 3466316288803265041L;

	private Long id ; 
	private String name ; 
	private String url ; 
}
