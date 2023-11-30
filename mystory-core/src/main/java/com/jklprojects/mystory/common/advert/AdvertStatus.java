/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.advert;

import com.jklprojects.mystory.common.IStatus;
import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * Advert's AdvertStatus.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-12-31
 * @version 2, 2018-06-23
 */
public enum AdvertStatus implements IStatus {
	NEW(1, "New"), PENDING_REVIEW(2, "Pending Review"), ACTIVE(3, "Active"), INACTIVE(4, "Inactive"), PURGED(5,
			"Purged"), REJECTED(6, "Rejected");

	private static final String SIMPLE_NAME = AdvertStatus.class.getSimpleName();

	private final int id;
	private final String name;

	/**
	 * @param id
	 * @param name
	 */
	AdvertStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getStatusName() {
		return name;
	}

	/**
	 * Lookup and return matching object or null if match is not found.
	 *
	 * @param id
	 * @return matching status or null if match is not found.
	 */
	public static AdvertStatus toEnum(int id) {
		return Arrays.stream(AdvertStatus.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	/**
	 * Return true if status id is equal, false otherwise.
	 *
	 * @param id
	 * @return true if status id is equal, false otherwise.
	 */
	@Override
	public boolean isIdEqual(int id) {
		return id == getId();
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(SIMPLE_NAME, id, name());
	}

	/**
	 * Return true if status equals one of the given statuses, false otherwise.
	 *
	 * @param statuses
	 * @return true if status equals one of the given statuses, false otherwise.
	 */
	@Override
	public boolean in(IStatus... statuses) {
		for (IStatus status : statuses) {
			if (status.isIdEqual(this.getId())) {
				return true;
			}
		}
		return false;
	}

	public boolean isActive() {
		return this == ACTIVE;
	}
}
