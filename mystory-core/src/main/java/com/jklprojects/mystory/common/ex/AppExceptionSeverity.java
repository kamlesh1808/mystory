/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.ex;

import com.jklprojects.mystory.common.i18n.I18N;

/**
 * Application Exception Severity.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2018-MAR-23
 * @since 2.18.03.23
 * @see app_enum.properties
 */
public enum AppExceptionSeverity {

	/** Please do not change severity name, id without a valid justification. */
	INFO(1, "Information", "Information"), WARN(2, "Warning", "Warning"), ERROR(3, "Error", "Error"), FATAL(4, "Fatal",
			"Fatal");

	private static final String simpleName = AppExceptionSeverity.class.getSimpleName();

	private final int id;
	private final String severityName;
	private String severityDescription;

	AppExceptionSeverity(int id, String severityName, String severityDescription) {
		this.id = id;
		this.severityName = severityName;
		this.severityDescription = severityDescription;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name();
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(simpleName, id, name());
	}

	public String getSeverityName() {
		return severityName;
	}

	public String getSeverityDescription() {
		return severityDescription;
	}

	/**
	 * Lookup and return matching object or null if match is not found.
	 *
	 * @param id
	 * @return matching status or null if match is not found.
	 */
	public static AppExceptionSeverity toEnum(int id) {
		for (AppExceptionSeverity en : AppExceptionSeverity.values()) {
			if (en.getId() == id) {
				return en;
			}
		}

		return null;
	}

	/**
	 * Return true if status id is equal, false otherwise.
	 *
	 * @param id
	 * @return true if status id is equal, false otherwise.
	 */
	public boolean isIdEqual(int id) {
		return id == getId();
	}
}
