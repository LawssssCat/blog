package com.cy.myblog.pojo.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(callSuper = true)
public class State extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -4883444469319931339L;
	/**状态,常用:200-正常,400-禁用*/
	@TableId(value = "state" )
	private Integer state ; 
	private String remark ; 
}
