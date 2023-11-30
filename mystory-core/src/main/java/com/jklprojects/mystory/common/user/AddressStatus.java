/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import com.jklprojects.mystory.common.IStatus;
import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-07-04
 * @version 2, 2018-06-23
 * @version 4, 2019-03-23
 */
public enum AddressStatus implements IStatus {
	ACTIVE(3, "Active"), INACTIVE(4, "Inactive"), PURGED(5, "Purged");

	private static final String SIMPLE_NAME = AddressStatus.class.getSimpleName();

	private final int id;
	private final String statusName;

	AddressStatus(int id, String statusName) {
		this.id = id;
		this.statusName = statusName;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getStatusName() {
		return statusName;
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching object
	 */
	public static AddressStatus toEnum(int id) {
		return Arrays.stream(AddressStatus.values()).filter(e -> e.getId() == id).findFirst().get();
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
		return Arrays.stream(statuses).anyMatch(e -> e.getId() == id);
	}
}
