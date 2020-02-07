package com.cy.myblog.service.ex;

import com.cy.myblog.common.ex.ServiceException;

public class DuplicationKeyException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5094533950913467634L;

	public DuplicationKeyException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DuplicationKeyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DuplicationKeyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DuplicationKeyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DuplicationKeyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
