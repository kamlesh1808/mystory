/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.entity;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Audit Event Type - Create, Retrieve, Update, Delete (CRUD)
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-11-06
 * @version 2, 2018-05-23
 */
public enum AuditEventType {
	CREATE(1), RETRIEVE(2), PRE_UPDATE(3), POST_UPDATE(4), DELETE(5);

	private final int id;

	AuditEventType(int id) {
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
	 * @throws NoSuchElementException
	 *             if match is not found
	 */
	public static AuditEventType toEnum(int id) {
		return Arrays.stream(AuditEventType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public boolean isCreate() {
		return CREATE == this;
	}

	public boolean isRetrieve() {
		return RETRIEVE == this;
	}

	public boolean isPreUpdate() {
		return PRE_UPDATE == this;
	}

	public boolean isPostUpdate() {
		return POST_UPDATE == this;
	}

	public boolean isDelete() {
		return DELETE == this;
	}

	public boolean in(AuditEventType... auditEventTypes) {
		return Arrays.asList(auditEventTypes).stream().anyMatch(e -> e.getId() == getId());
	}
}
