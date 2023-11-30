/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.service.api;

import com.jklprojects.mystory.business.story.entity.Story;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.common.TimePeriod;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.story.StoryAccessType;
import com.jklprojects.mystory.common.story.StoryAuthor;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.StoryPostType;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.story.StoryTag;
import com.jklprojects.mystory.common.story.Tag;
import com.jklprojects.mystory.common.story.dto.StoryLinkDTO;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.report.story.StoriesCreatedReportBean;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Local;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 */
@Local
public interface StoryService {
	StoryView createStoryReply(long userUID, long storyId, String reply, long repliedToId) throws AppCodeException;

	StoryView createStoryReply(long userUID, StoryView story, String reply, long repliedToId) throws AppCodeException;

	StoryView createStoryWithUserOAuth2(StoryView storyCreate, String oAuth2UID, UserRoleType userRoleType)
			throws AppCodeException;

	/**
	 * Create story for given user.
	 *
	 * @param story
	 * @param userUID
	 * @throws AppCodeException
	 */
	StoryView createStoryWithUserUID(StoryView story, long userUID) throws AppCodeException;

	long countStoriesWithUserUID(Long userUID, StoryStatus... storyStatuses);

	long countStoriesWithUserOAuthUID(String userOAuthUID, StoryStatus... storyStatuses);

	long countStoriesWithUserUIDStoryName(Long userUID, StoryName storyName, StoryStatus... storyStatuses);

	long countStoriesWithUserOAuthUIDStoryName(String userOAuthUID, StoryName storyName, StoryStatus... storyStatuses);

	StoryView editStoryReply(long userUID, StoryView story, String reply, long replyId) throws AppCodeException;

	List<Story> findStoriesCreated(TimePeriod reportPeriod);

	List<Story> findStoriesCreatedBetween(LocalDate startPeriod, LocalDate endPeriod);

	List<StoriesCreatedReportBean> findStoriesCreatedReportBeans(TimePeriod reportPeriod);

	/**
	 * Find Stories associated with the user UID with the {@link StoryName}s
	 *
	 * @param userUID
	 * @param storyNames
	 * @return
	 */
	List<StoryView> findStoriesUserWithUIDStoryNames(long userUID, StoryName... storyNames);

	/**
	 * Find Stories associated with the user UID with the {@link Tag}s
	 *
	 * @param userUID
	 * @param tags
	 * @return
	 */
	List<StoryView> findStoriesUserWithTags(long userUID, String... tags);

	List<StoryView> findStoriesUserOAuthWithTags(String userOAuth2UID, String... tags);

	List<Story> findStoriesUserWithUserNameOrEmailStoryNames(String userNameOrEmail, StoryName... storyNames);

	List<StoryView> findStoryViewsWithAccessType(StoryAccessType... storyAccessTypes);

	List<Story> findStoriesWithAccessType(StoryAccessType... storyAccessTypes);

	List<StoryLinkDTO> findStoryLinksWithAccessType(StoryAccessType... storyAccessTypes);

	List<Story> findStoriesWithStatus(StoryStatus... storyStatus);

	List<StoryLinkDTO> findStoryLinkDTOsWithStatus(StoryStatus... storyStatus);

	List<Story> findStoriesWithStoryNameAndTags(StoryName storyName, String... tags);

	/**
	 * Find {@link StoryAuthor}s
	 *
	 * @return
	 */
	List<StoryAuthor> findStoryAuthors();

	List<Story> findStoriesWithStoryNames(StoryName... storyNames) throws AppCodeException;

	List<StoryLinkDTO> findStoryLinkDTOsWithStoryNames(StoryName... storyNames) throws AppCodeException;

	List<StoryLinkDTO> findStoryLinkDTOsWithTagIds(Long... tagIds) throws AppCodeException;

	List<Story> findStoriesWithTagIds(Long... tagIds) throws AppCodeException;

	List<Story> findStoriesWithTags(String... tags);

	List<StoryLinkDTO> findStoryLinkDTOsWithTags(String... tags);

	List<Story> findStoriesWithUserOAuthUID(String userOAuth2UID, StoryStatus... storyStatuses);

	List<StoryLinkDTO> findStoryLinkDTOsWithUserOAuthUID(String userOAuth2UID, StoryStatus... storyStatuses);

	/**
	 * Find Stories associated with the user UID with the story statuses.
	 *
	 * @param userUID
	 * @param storyStatuses
	 * @return
	 */
	List<Story> findStoriesWithUserUID(long userUID, StoryStatus... storyStatuses);

	List<StoryLinkDTO> findStoryLinkDTOsWithUserUID(long userUID, StoryStatus... storyStatuses);

	StoryView findStory(long storyId) throws AppCodeException;

	/**
	 * Find {@link StoryTag}s
	 *
	 * @return
	 */
	List<StoryTag> findStoryTags();

	List<Story> findStoryWithUserNameOrEmail(String userNameOrEmail, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus);

	List<Story> findStoryWithUserUID(long userUID, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus);

	/**
	 * @param userNameOrEmail
	 *            - user name or user email
	 * @param storyName
	 * @param storyType
	 * @param storyStatus
	 * @return
	 */
	boolean isStoryExistsWithUserID(String userNameOrEmail, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus);

	boolean isStoryExistsWithUserOAuth2UID(String oAuth2UID, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus);

	boolean isStoryExistsWithUserUID(long userUID, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus);

	boolean isUserStoryAuthor(String signedInUserNameOrEmail, Story story);

	StoryView reviewStory(StoryView story) throws AppCodeException;

	StoryView updateStory(StoryView story) throws AppCodeException;

	StoryView updateStoryStatus(long storyId, StoryStatus status);

	/**
	 * Return related stories of the user uid.
	 *
	 * @param userUID
	 *            of {@link com.jklprojects.mystory.business.user.entity.User} or
	 *            {@link com.jklprojects.mystory.business.user.entity.UserOAuth2}
	 * @param userRoleType
	 * @return
	 */
	List<StoryLinkDTO> findStoriesRelated(String userUID, UserRoleType userRoleType);

	List<StoryLinkDTO> findStoriesRelated(String userUID, long storyId, UserRoleType userRoleType);

	List<Story> findStoriesOfUser(String userUID, UserRoleType userRoleType);

	List<StoryLinkDTO> findStoryLinkDTOsOfUser(String userUID, UserRoleType userRoleType);
}
