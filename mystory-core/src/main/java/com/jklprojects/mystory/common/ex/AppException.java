/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.ex;

/**
 * @author Kamleshkumar Patel
 * @version 1, 2015-04-01
 * @version 2, 2018-MAR-23
 */
public class AppException extends Exception {
	private static final long serialVersionUID = 4505799015976208622L;

	private static final AppExceptionSeverity defaultAppExceptionSeverity = AppExceptionSeverity.ERROR;

	private AppExceptionSeverity appExceptionSeverity;

	public AppException(String message) {
		super(message);

		this.appExceptionSeverity = defaultAppExceptionSeverity;
	}

	public AppException(String message, Throwable t) {
		super(message, t);

		this.appExceptionSeverity = defaultAppExceptionSeverity;
	}

	/**
	 * @param message
	 * @param t
	 * @param appExceptionSeverity
	 */
	public AppException(String message, Throwable t, AppExceptionSeverity appExceptionSeverity) {
		super(message, t);

		this.appExceptionSeverity = appExceptionSeverity;
	}

	public AppExceptionSeverity getAppExceptionSeverity() {
		return appExceptionSeverity;
	}

	public void setAppExceptionSeverity(AppExceptionSeverity appExceptionSeverity) {
		this.appExceptionSeverity = appExceptionSeverity;
	}
}
