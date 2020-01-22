package com.cy.myblog.pojo.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Blog implements Serializable {
	private static final long serialVersionUID = -8759763340897328289L;

	private Integer id ; 
	private String title ; 
	private String content ;
	
	/**图片*/
	private String imgUrl ; 
	/**转载 or 原创 or 翻译*/
	private String flag ; 
	/**浏览次数*/
	private Integer views ; 
	/**true-开启赞赏*/
	private boolean isAppreciation ;  
	/**true-开启转载声明*/
	private boolean isStatementShare;
	/**true-开启评论*/
	private boolean isCommentable;
	/**true-开启推荐*/
	private boolean isRecommend;//发布

	private String createdUser ; 
	private Date createdTime ; 
	private String modifiedUser ; 
	private Date modifiedTime ; 
}
