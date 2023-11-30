/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-06-09
 * @version 1, 2016-12-08
 * @version 2, 2018-06-23
 */
public enum StoryAccessType {
	UNDEFINED(0, "Select One", "Selection One"), PUBLIC(1, "Public", "Public"), SHARE_WITH_STORY_NAME(2,
			"Share with Story Name", "Share with Story Name");

	private static final String simpleName = StoryAccessType.class.getSimpleName();

	private final int id;
	private final String name;
	private final String description;

	StoryAccessType(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(simpleName, id, name());
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching object
	 */
	public static StoryAccessType toEnum(int id) {
		return Arrays.stream(StoryAccessType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public String getDescription() {
		return description;
	}

	public boolean isAccessTypePublic() {
		return this == StoryAccessType.PUBLIC;
	}

	public boolean isStoryAccessTypeSharedWithStory() {
		return this == StoryAccessType.SHARE_WITH_STORY_NAME;
	}
}
