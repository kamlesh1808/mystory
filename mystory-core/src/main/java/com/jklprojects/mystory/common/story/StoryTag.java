/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

import com.jklprojects.mystory.common.api.IAppDTO;

/**
 * StoryTag SqlResultSetMapping entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-04-23
 */
public final class StoryTag implements IAppDTO {
	private final Long storyTagId;
	private final String storyTagName;
	private final int storiesCount;
	private final TagStatus tagStatus;

	/**
	 * @param storyTagId
	 * @param storyTagName
	 * @param tagStatus
	 * @param storiesCount
	 */
	public StoryTag(final Long storyTagId, final String storyTagName, final int tagStatus, final int storiesCount) {
		this.storyTagId = storyTagId;
		this.storyTagName = storyTagName;
		this.tagStatus = TagStatus.toEnum(tagStatus);
		this.storiesCount = storiesCount;
	}

	/**
	 * @param storyTagId
	 * @param storyTagName
	 * @param tagStatus
	 */
	public StoryTag(final long storyTagId, final String storyTagName, final TagStatus tagStatus) {
		this.storyTagId = storyTagId;
		this.storyTagName = storyTagName;
		this.tagStatus = tagStatus;
		this.storiesCount = 0;
	}

	public final Long getStoryTagId() {
		return storyTagId;
	}

	public final String getStoryTagName() {
		return storyTagName;
	}

	public final int getStoriesCount() {
		return storiesCount;
	}

	public final TagStatus getTagStatus() {
		return tagStatus;
	}

	public final String getStoryTagNameStoriesCount() {
		return storyTagName + "   " + storiesCount;
	}
}
