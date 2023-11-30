/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import com.jklprojects.mystory.common.IStatus;
import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * Represents a list of status for the User Account.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-18
 * @version 2, 2018-06-23
 * @version 3, 2018-12-23
 * @version 4, 2019-03-23
 */
public enum UserStatus implements IStatus {
	NEW(1, "New"), PENDING_EMAIL_VERIFY(2, "Pending - E-Mail Verification"), ACTIVE(3, "Active"), INACTIVE(4,
			"Inactive"), PURGED(5, "Purged"), ACTIVE_TEMP_PWD(6,
					"Active - Temp Password"), LOCKED_MAX_INVALID_SIGNIN_ATTEMPTS(7,
							"Locked - Maximum invalid sign-in attempts ");

	private static final String SIMPLE_NAME = UserStatus.class.getSimpleName();

	private final int id;
	private final String statusName;

	UserStatus(int id, String statusName) {
		this.id = id;
		this.statusName = statusName;
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching object
	 */
	public static UserStatus toEnum(int id) {
		return Arrays.stream(UserStatus.values()).filter(e -> e.getId() == id).findFirst().get();
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

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getStatusName() {
		return statusName;
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

	public boolean isActive() {
		return this == ACTIVE;
	}

	public boolean isActiveTempPassword() {
		return this == ACTIVE_TEMP_PWD;
	}

	public boolean isInActive() {
		return this == INACTIVE;
	}

	public boolean isLockedMaxInvalidSigninAttempts() {
		return this == LOCKED_MAX_INVALID_SIGNIN_ATTEMPTS;
	}

	public boolean isNew() {
		return this == NEW;
	}

	public boolean isPendingEmailVerify() {
		return this == PENDING_EMAIL_VERIFY;
	}

	public boolean isPurged() {
		return this == PURGED;
	}
}
