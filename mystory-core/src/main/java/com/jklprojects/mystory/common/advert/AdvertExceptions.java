/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.advert;

import com.jklprojects.mystory.common.ex.AppCodeException;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-DEC-31
 */
public enum AdvertExceptions {
	ADVERT_001("ADVERT_001", "An Advert exists with name {0}"), ADVERT_002("ADVERT_002",
			"An Advert with id {0} was not found");

	private String errorCode;
	private final String defaultMsg;

	AdvertExceptions(String errorCode, String defaultMsg) {
		this.errorCode = errorCode;
		this.defaultMsg = defaultMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public AppCodeException newAppCodeExcepiton() {
		return new AppCodeException(this.errorCode, this.defaultMsg);
	}

	public AppCodeException newAppCodeExcepiton(Object... params) {
		return new AppCodeException(this.errorCode, this.defaultMsg).setMessageParams(params);
	}
}
