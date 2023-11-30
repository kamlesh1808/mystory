/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-29
 * @version 1, 2016-12-08
 * @version 2, 2018-04-23
 */
public enum StoryPostType {
	UNDEFINED(0, "Select One", "Select One"), INTRO(1, "Introduction Story", "Introduction Story"), GENERAL_POST(2,
			"General Post", "General Post"), QUESTION(3, "Question", "Question");

	private static final String simpleName = StoryPostType.class.getSimpleName();

	private final int id;
	private final String name;
	private final String description;

	StoryPostType(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(simpleName, id, name());
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching status
	 */
	public static StoryPostType toEnum(int id) {
		return Arrays.stream(StoryPostType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	/**
	 * Return true if the object equals one in the given list, false otherwise.
	 *
	 * @param list
	 * @return true if object equals one in the given list, false otherwise.
	 */
	public boolean in(StoryPostType... list) {
		for (StoryPostType obj : list) {
			if (this == obj) {
				return true;
			}
		}
		return false;
	}

	/** @return pending Review List. */
	public static StoryPostType[] pendingReviewList() {
		return StoryPostType.values();
	}

	public boolean isIntro() {
		return this == StoryPostType.INTRO;
	}
}
