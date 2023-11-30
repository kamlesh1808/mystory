/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service.api;

import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.dto.UserProfileDTO;

/**
 * User Profile service.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
public interface UserProfileService {
	/**
	 * Find user profile data of user.
	 *
	 * @param userUID
	 * @param userRoleType
	 * @return
	 */
	UserProfileDTO findUserProfileOfUserUID(String userUID, UserRoleType userRoleType) throws AppCodeException;

	/**
	 * Find user profile data of the story.
	 *
	 * @param storyId
	 * @return
	 * @throws AppCodeException
	 */
	UserProfileDTO findUserProfileOfStory(long storyId) throws AppCodeException;

	/**
	 * Determine if the {@link StoryName} is accessible by the user.
	 *
	 * @param story
	 * @param userUID
	 * @param userRoleType
	 * @return
	 */
	boolean isStoryViewableByUser(StoryView story, String userUID, UserRoleType userRoleType);

	long countStoriesOfUserWithStoryName(StoryView story, String userUID, UserRoleType userRoleType);

	/**
	 * Count number of stories of story's author with story statuses.
	 *
	 * @param story
	 * @param storyStatuses
	 * @return
	 */
	long countStoriesOfStoryAuthor(StoryView story, StoryStatus... storyStatuses);
}
