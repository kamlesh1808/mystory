/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.audit;

import java.util.Arrays;

/**
 * Audit Attribute Type
 *
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-05-23
 * @since 2.18.05.23
 */
public enum AuditAttrType {
	USER_AUTH_SUCCESS(101, "User Authentication Success"), USER_SIGN_OUT_SUCCESS(102,
			"User Sign Out Success"), EXCEPTION_MSG(103,
					"Exception Message"), USER_OAUTH2_SESSION_SUCCESS(104, "User OAuth2 Session Successs");

	private final String attrName;
	private final int id;

	AuditAttrType(int id, String attrName) {
		this.id = id;
		this.attrName = attrName;
	}

	/**
	 * Lookup and return matching object.
	 *
	 * @param id
	 * @return matching object
	 */
	public static AuditAttrType toEnum(int id) {
		return Arrays.stream(AuditAttrType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public String getAttrName() {
		return attrName;
	}

	public int getId() {
		return id;
	}

	public boolean in(AuditAttrType... auditAttrTypes) {
		return Arrays.asList(auditAttrTypes).stream().filter(e -> e.getId() == getId()).findFirst().isPresent();
	}

	public boolean isUserAuthSuccess() {
		return USER_AUTH_SUCCESS == this;
	}
}
