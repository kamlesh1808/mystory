/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.audit;

import java.util.Arrays;
import java.util.List;

/**
 * Audit Type
 *
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-05-23
 * @since 2.18.05.23
 */
public enum AuditType {
	ENTITY_CRUD(1), USER_AUTHENTICATION(2), USER_SIGN_OUT(3), USER_OAUTH2_AUTHENTICATION(4);

	private final int id;

	AuditType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	/**
	 * Lookup and return matching object.
	 *
	 * @param id
	 * @return matching object
	 */
	public static AuditType toEnum(int id) {
		return Arrays.stream(AuditType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public boolean isEntityCrud() {
		return ENTITY_CRUD == this;
	}

	public boolean isUserSignIn() {
		return USER_AUTHENTICATION == this;
	}

	public boolean isUserSignOut() {
		return USER_SIGN_OUT == this;
	}

	public boolean in(AuditType... auditTypes) {
		return Arrays.asList(auditTypes).stream().filter(e -> e.getId() == getId()).findFirst().isPresent();
	}

	public static List<AuditType> getUserLastSeenAuditTypes() {
		return Arrays.asList(USER_AUTHENTICATION, USER_SIGN_OUT, USER_OAUTH2_AUTHENTICATION);
	}
}
