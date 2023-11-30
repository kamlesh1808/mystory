/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story.dto;

import com.jklprojects.mystory.common.api.IAppDTO;
import com.jklprojects.mystory.common.story.StoryAccessType;
import com.jklprojects.mystory.common.story.StoryAuthor;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.StoryPostType;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.story.StoryTag;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents light weight view for Story entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
public final class StoryLinkDTO implements IAppDTO {

	private static final long serialVersionUID = 333315488741900L;

	private long storyId;
	private StoryName storyName;
	private StoryPostType storyType;
	private StoryAccessType accessType;
	private String title;
	private StoryStatus status;
	private int views;

	private OffsetDateTime storyTextUpdatedDateTime;
	private OffsetDateTime updatedDateTime;
	private OffsetDateTime createdDateTime;

	private StoryAuthor storyAuthor;

	private List<StoryTag> storyTags;

	public StoryLinkDTO() {
		super();
	}

	public StoryLinkDTO(final long storyId, final StoryName storyName, final StoryPostType storyType,
			final StoryAccessType accessType, final String title, final StoryStatus status,
			final OffsetDateTime createdDateTime, final OffsetDateTime updatedDateTime,
			final OffsetDateTime storyTextUpdatedDateTime, final int views, final StoryAuthor storyAuthor,
			List<StoryTag> storyTags) {

		this();

		this.storyId = storyId;
		this.storyName = storyName;
		this.storyType = storyType;
		this.accessType = accessType;
		this.title = title;
		this.status = status;
		this.views = views;

		this.createdDateTime = createdDateTime;
		this.updatedDateTime = updatedDateTime;
		this.storyTextUpdatedDateTime = storyTextUpdatedDateTime;

		this.storyAuthor = storyAuthor;
		this.storyTags = storyTags;
	}

	public long getStoryId() {
		return storyId;
	}

	public StoryName getStoryName() {
		return storyName;
	}

	public StoryPostType getStoryType() {
		return storyType;
	}

	public StoryAccessType getAccessType() {
		return accessType;
	}

	public String getTitle() {
		return title;
	}

	public StoryStatus getStatus() {
		return status;
	}

	public int getViews() {
		return views;
	}

	public OffsetDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public OffsetDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public OffsetDateTime getStoryTextUpdatedDateTime() {
		return storyTextUpdatedDateTime;
	}

	public StoryAuthor getStoryAuthor() {
		return storyAuthor;
	}

	public List<StoryTag> getStoryTags() {
		return storyTags;
	}

	public String getCreatedDateTimeFormatted() {
		return createdDateTime != null ? DateTimeFormatter.RFC_1123_DATE_TIME.format(createdDateTime) : "";
	}

}
