package com.cy.myblog.common.utils;

import org.springframework.util.StringUtils;

import com.cy.myblog.common.ex.ServiceException;
import com.cy.myblog.service.ex.ArgumentValidFaildException;
import com.cy.myblog.service.ex.DuplicationKeyException;
import com.cy.myblog.service.ex.DataUnfoundException;

public class Assert {
	
	/*----------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------*/

	/**
	 * 参数异常
	 * @param statement
	 * @param message
	 */
	public static void isArgumentValid(boolean statement , String message ) {
		if(statement) throw new ArgumentValidFaildException(message);
	}
	/**
	 * 参数异常:键重复
	 */
	public static void isDuplicationKey(boolean statement, String message) {
		if(statement) throw new DuplicationKeyException(message);
	}
	

	/*----------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------*/
	

	/**
	 * 服务异常
	 */
	public static void isServiceValid(boolean statement , String message) {
		if(statement) throw new ServiceException(message) ;
	}

	/**
	 * 服务异常:数据为空
	 * @param statement
	 * @param message
	 */
	public static void isNoFound(boolean statement, String message) {
		if(statement) throw new DataUnfoundException(message) ;
	}



}
