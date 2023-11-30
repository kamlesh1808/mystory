/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.ex;

import com.jklprojects.mystory.common.i18n.I18N;

/**
 * Typically represents a server side application exception with an error code
 * which client side can look up to display an i18n message.
 *
 * @author Kamleshkumar Patel
 * @version 1, 2015-04-17
 * @version 2, 2018-MAR-23
 */
public class AppCodeException extends AppException {
	private static final long serialVersionUID = 4505799015976208622L;

	private static final AppExceptionSeverity defaultAppExceptionSeverity = AppExceptionSeverity.ERROR;

	private String errorCode;
	private Object[] messageParams;
	private final I18N i18n = I18N.APP_MSGS; // default, if one not set

	public AppCodeException(String errorCode, String defaultMqsg) {
		this(errorCode, defaultMqsg, null);
	}

	/**
	 * @param errorCode
	 * @param defaultMsg
	 * @param t
	 */
	public AppCodeException(String errorCode, String defaultMsg, Throwable t) {
		super(defaultMsg, t, defaultAppExceptionSeverity);

		this.setErrorCode(errorCode);
	}

	/**
	 * @param errorCode
	 * @param defaultMsg
	 * @param t
	 * @param appExceptionSeverity
	 */
	public AppCodeException(String errorCode, String defaultMsg, Throwable t,
			AppExceptionSeverity appExceptionSeverity) {

		super(defaultMsg, t, appExceptionSeverity);

		this.setErrorCode(errorCode);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getI18NMessage() {
		return i18n.getI18N(getErrorCode(), getMessageParams());
	}

	public Object[] getMessageParams() {
		return messageParams;
	}

	/**
	 * Convenience method.
	 *
	 * @return true if error code is not null and is not empty, false otherwise.
	 */
	public boolean isErrorCodeValid() {
		return errorCode != null && !errorCode.isEmpty();
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public AppCodeException setMessageParams(Object... params) {
		this.messageParams = params;
		return this;
	}
}
