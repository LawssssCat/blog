package com.cy.myblog.pojo.po;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Tag implements Serializable {
	private static final long serialVersionUID = 8705186748335833770L;
	private Integer id ; 
	private String name ; 

}
