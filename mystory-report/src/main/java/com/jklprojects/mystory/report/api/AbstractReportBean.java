/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.api;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Oct-23
 */
public abstract class AbstractReportBean implements AppReportBean {
	private static final long serialVersionUID = -1980600575790263260L;

	private final String createdDateTime;
	private final String updatedDateTime;

	/**
	 * @param createdDateTime
	 * @param updatedDateTime
	 */
	public AbstractReportBean(String createdDateTime, String updatedDateTime) {
		super();

		this.createdDateTime = createdDateTime;
		this.updatedDateTime = updatedDateTime;
	}

	public final String getCreatedDateTime() {
		return createdDateTime;
	}

	public final String getUpdatedDateTime() {
		return updatedDateTime;
	}
}
