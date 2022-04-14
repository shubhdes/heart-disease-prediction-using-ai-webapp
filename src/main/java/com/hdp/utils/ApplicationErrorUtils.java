package com.hdp.utils;

public abstract class ApplicationErrorUtils {

	public static void addError(String msg) {
		throw new ApplicationError(msg);
	}
}
