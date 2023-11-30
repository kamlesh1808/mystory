/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.ex;

/**
 * @author Kamleshkumar Patel
 * @version 1, 2017-Dec-23
 */
public class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 3275381940941499258L;

	/**
	 * @param message
	 */
	public AppRuntimeException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param t
	 */
	public AppRuntimeException(String message, Throwable t) {
		super(message, t);
	}
}
