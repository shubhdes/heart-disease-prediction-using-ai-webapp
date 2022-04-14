package com.hdp.utils;

public class ApplicationError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6106424130000907214L;

	public ApplicationError(String msg) {
		super(msg);
	}
}
