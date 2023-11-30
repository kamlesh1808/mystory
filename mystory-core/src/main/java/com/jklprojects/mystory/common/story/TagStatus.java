/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

import com.jklprojects.mystory.common.IStatus;
import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-05-16
 * @version 2, 2018-06-23
 */
public enum TagStatus implements IStatus {
	ACTIVE(1, "Active"), IN_ACTIVE(2, "Inactive");

	private static final String SIMPLE_NAME = TagStatus.class.getSimpleName();

	private final int id;
	private final String statusName;

	TagStatus(int id, String statusName) {
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
		for (IStatus ss : statuses) {
			if (ss.isIdEqual(this.getId())) {
				return true;
			}
		}
		return false;
	}

	public static TagStatus toEnum(int id) {
		return Arrays.stream(TagStatus.values()).filter(e -> e.getId() == id).findFirst().get();
	}
}
