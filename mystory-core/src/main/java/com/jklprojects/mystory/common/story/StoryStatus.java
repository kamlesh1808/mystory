/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

import com.jklprojects.mystory.common.IStatus;
import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a list of status for the Story.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-05-03
 * @version 1, 2018-06-23
 */
public enum StoryStatus implements IStatus {
	NEW(1, "New"), PENDING_REVIEW(2, "Pending Review"), ACTIVE(3, "Active"), INACTIVE(4, "Inactive"), PURGED(5,
			"Purged"), REJECTED(6, "Rejected");

	private static final String SIMPLE_NAME = StoryStatus.class.getSimpleName();

	private final int id;
	private final String name;

	StoryStatus(int id, String name) {
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
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching status
	 */
	public static StoryStatus toEnum(int id) {
		return Arrays.stream(StoryStatus.values()).filter(e -> e.getId() == id).findFirst().get();
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

	public static StoryStatus[] getUserStoryStatuses() {
		return new StoryStatus[]{StoryStatus.ACTIVE, StoryStatus.INACTIVE, StoryStatus.NEW, StoryStatus.PENDING_REVIEW,
				StoryStatus.REJECTED};
	}

	public static List<StoryStatus> getUserStoryExistsStatuses() {
		return Arrays.asList(StoryStatus.ACTIVE, StoryStatus.NEW, StoryStatus.PENDING_REVIEW);
	}

	public boolean isActive() {
		return this == StoryStatus.ACTIVE;
	}
}
