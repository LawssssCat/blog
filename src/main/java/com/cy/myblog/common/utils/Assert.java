package com.cy.myblog.common.utils;


public class Assert {

	public static void isNull(Object obj , RuntimeException e) {
		if(obj==null) {
			throw e ; 
		}
	}

	public static void isTrue(boolean statement, RuntimeException e) {
		if(statement) {
			throw e ; 
		}
	}
}
