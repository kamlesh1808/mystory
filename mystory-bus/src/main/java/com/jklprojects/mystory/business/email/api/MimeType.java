/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.email.api;

public enum MimeType {
	TEXT_PLAIN("text/plain"), TEXT_HTML("text/html"), MULTIPART_MIXED("multipart/mixed");

	private final String value;

	MimeType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}