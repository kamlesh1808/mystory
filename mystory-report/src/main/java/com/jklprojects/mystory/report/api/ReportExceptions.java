/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.api;

import com.jklprojects.mystory.common.ex.AppCodeException;

/**
 * Exceptions related to Report generation.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Sep 19, 2015
 */
public enum ReportExceptions {
	REPORT_001("REPORT_001", "Error generating report"), REPORT_002("REPORT_002",
			"Error Adding Report Resource Parameter");

	private String errorCode;
	private final String defaultMsg;

	ReportExceptions(String errorCode, String defaultMsg) {
		this.errorCode = errorCode;
		this.defaultMsg = defaultMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public AppCodeException newAppCodeException() {
		return new AppCodeException(this.errorCode, this.defaultMsg);
	}

	public AppCodeException newAppCodeException(Object... params) {
		return new AppCodeException(this.errorCode, this.defaultMsg).setMessageParams(params);
	}
}
