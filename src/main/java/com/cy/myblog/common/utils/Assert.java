package com.cy.myblog.common.utils;

import org.springframework.util.StringUtils;

import com.cy.myblog.common.exception.ServiceException;

public class Assert {
	
	/**
	 * if(statement)throw e ; 
	 * @param message 错误信息
	 * @throws IllegalArgumentException
	 */
	public static void isArgumentValid(boolean statement , String message ) {
		if(statement) throw new IllegalArgumentException(message);
	}
	
	public static void isEmpty(String str , String message) {
		if(StringUtils.isEmpty(str)) throw new IllegalArgumentException(message);
	}

	/**obj==null*/
	public static void isNull(Object obj , RuntimeException e) {
		if(obj==null) {
			throw e ; 
		}
	}

	/**statement=ture*/
	public static void isTrue(boolean statement, RuntimeException e) {
		if(statement) {
			throw e ; 
		}
	}

	public static void isServiceValid(boolean statement , String message) {
		if(statement) throw new ServiceException(message) ;
	}
}
