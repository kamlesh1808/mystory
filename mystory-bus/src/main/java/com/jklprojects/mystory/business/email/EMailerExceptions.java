/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.email;

import com.jklprojects.mystory.common.ex.AppCodeException;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Oct-23
 */
public enum EMailerExceptions {
	EMAILER_001("EMAILER_001", "Unable to convert attachment file to Base64 string"), EMAILER_002("EMAILER_002",
			"from is required"), EMAILER_003("EMAILER_003", "subject is required"), EMAILER_004("EMAILER_004",
					"to is required"), EMAILER_005("EMAILER_005", "API key is required"), EMAILER_006("EMAILER_006",
							"Either html or text is required"), EMAILER_007("EMAILER_007", "Session was null");

	private String errorCode;
	private final String defaultMsg;

	EMailerExceptions(String errorCode, String defaultMsg) {
		this.errorCode = errorCode;
		this.defaultMsg = defaultMsg;
	}

	public String getDefaultMsg() {
		return defaultMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public AppCodeException newAppCodeException() {
		return new AppCodeException(getErrorCode(), getDefaultMsg());
	}

	public AppCodeException newAppCodeException(Object... params) {
		return new AppCodeException(getErrorCode(), getDefaultMsg()).setMessageParams(params);
	}

	public static AppCodeException newAppCodeExceptionCustom(String errorCode, String message) {
		return new AppCodeException(errorCode, message);
	}

}
