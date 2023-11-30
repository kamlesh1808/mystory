/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service;

import com.jklprojects.mystory.business.audit.api.AuditService;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.business.story.service.api.StoryService;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.business.user.service.api.UserOAuth2Service;
import com.jklprojects.mystory.business.user.service.api.UserProfileService;
import com.jklprojects.mystory.business.user.service.api.UserService;
import com.jklprojects.mystory.business.watch.service.api.UserWatchService;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.user.UserExceptions;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.dto.UserProfileDTO;
import com.jklprojects.mystory.common.util.NumUtil;
import com.jklprojects.mystory.common.util.StringUtil;
import java.time.OffsetDateTime;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * User Profile service.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
@Stateless
public class UserProfileServiceImpl implements UserProfileService {

	private static final XLogger logger = XLoggerFactory.getXLogger(UserProfileServiceImpl.class);

	private User sysGuestUser;

	@Inject
	private UserService userService;

	@Inject
	private UserOAuth2Service userOAuth2Service;

	@Inject
	private AuditService auditService;

	@Inject
	private UserWatchService userWatchService;

	@Inject
	private StoryService storyService;

	public UserProfileServiceImpl() {
		super();
	}

	/**
	 * Find user or user OAuth2 with UID and create and return
	 * {@link UserProfileDTO}
	 *
	 * @param userUID
	 * @param userRoleType
	 * @return userProfileBean
	 */
	@Override
	public UserProfileDTO findUserProfileOfUserUID(String userUID, UserRoleType userRoleType) throws AppCodeException {
		logger.entry(userUID, userRoleType);

		UserProfileDTO userProfileBean = new UserProfileDTO();
		OffsetDateTime lastSeen = null;

		if (StringUtil.isNotEmpty(userUID)) {
			if (userRoleType.getUserType().isAppInternal()) {
				long userUIDLong = 0;
				try {
					userUIDLong = Long.parseLong(userUID);
				} catch (NumberFormatException e) {
					throw UserExceptions.USER_ACCOUNT_011.newAppCodeException(userUID);
				}

				UserView user = userService.findUserWithUserUID(userUIDLong, UserView.class);

				if (user != null && user.isIdSet()) {
					lastSeen = auditService.findUserLastSeen(userUID);
					long profileViews = userWatchService.countUserViews(userUIDLong);

					long storiesCount = storyService.countStoriesWithUserUID(user.getUid(), StoryStatus.ACTIVE);

					userProfileBean = new UserProfileDTO(user.getId(), userUID, user.getUserName(), user.getFirstName(),
							user.getLastName(), user.getUserRoleType(), user.getAboutMe(), user.getCreatedTimestamp(),
							storiesCount, lastSeen, profileViews);
				}
			} else if (userRoleType.getUserType().isOAuth2External()) {
				UserOAuth2View userOAuth2 = userOAuth2Service.findUserOAuthViewWithOAuthUID(userUID);

				if (userOAuth2 != null && userOAuth2.isIdSet()) {
					lastSeen = auditService.findUserLastSeen(userUID);
					long profileViews = userWatchService.countUserOAuthViews(userUID);

					long storiesCount = storyService.countStoriesWithUserOAuthUID(userOAuth2.getIdOAuth2(),
							StoryStatus.ACTIVE);

					userProfileBean = new UserProfileDTO(userOAuth2.getId(), userOAuth2.getIdOAuth2(),
							userOAuth2.getUserName(), userOAuth2.getFirstName(), userOAuth2.getLastName(),
							userOAuth2.getUserRoleType(), userOAuth2.getAboutMe(), userOAuth2.getCreatedTimestamp(),
							storiesCount, lastSeen, profileViews);
				}
			}
		}

		return userProfileBean;
	}

	/**
	 * Construct and return story's author user profile from user or OAuth2 user.
	 *
	 * @param storyId
	 * @return userProfileBean
	 */
	@Override
	public UserProfileDTO findUserProfileOfStory(long storyId) throws AppCodeException {
		logger.entry(storyId);

		UserProfileDTO userProfileBean = null;

		StoryView story = storyService.findStory(storyId);

		if (story != null && story.isIdSet()) {

			UserView user = story.getUser();
			UserOAuth2View userOAuth2 = story.getUserOAuth2();

			long userStoriesCount = countStoriesOfStoryAuthor(story, StoryStatus.ACTIVE);

			if (user != null && user.isIdSet()) {
				String userUID = String.valueOf(user.getUid());

				OffsetDateTime lastSeen = auditService.findUserLastSeen(userUID);

				long countUserViews = userWatchService.countUserViews(user.getUid());

				userProfileBean = new UserProfileDTO(user.getId(), userUID, user.getUserName(), user.getFirstName(),
						user.getLastName(), user.getUserRoleType(), user.getAboutMe(), user.getCreatedTimestamp(),
						userStoriesCount, lastSeen, countUserViews);

			} else if (userOAuth2 != null && userOAuth2.isIdSet()) {
				OffsetDateTime lastSeen = auditService.findUserLastSeen(userOAuth2.getIdOAuth2());

				long countUserOAuthViews = userWatchService.countUserOAuthViews(userOAuth2.getIdOAuth2());

				userProfileBean = new UserProfileDTO(userOAuth2.getId(), userOAuth2.getIdOAuth2(),
						userOAuth2.getUserName(), userOAuth2.getFirstName(), userOAuth2.getLastName(),
						userOAuth2.getUserRoleType(), userOAuth2.getAboutMe(), userOAuth2.getCreatedTimestamp(),
						userStoriesCount, lastSeen, countUserOAuthViews);
			}
		}
		return userProfileBean;
	}

	@Override
	public boolean isStoryViewableByUser(StoryView story, String userUID, UserRoleType userRoleType) {
		logger.entry(story.getId(), userUID, userRoleType);
		return countStoriesOfUserWithStoryName(story, userUID, userRoleType) > 0;
	}

	@Override
	public long countStoriesOfUserWithStoryName(StoryView story, String userUID, UserRoleType userRoleType) {
		logger.entry(story.getId(), userUID, userRoleType);

		long storiesCount = 0;
		if (userRoleType.getUserType().isAppInternal()) {
			storiesCount = storyService.countStoriesWithUserUIDStoryName(NumUtil.parseLong(userUID),
					story.getStoryName(), StoryStatus.ACTIVE);
		}
		if (userRoleType.getUserType().isOAuth2External()) {
			storiesCount = storyService.countStoriesWithUserOAuthUIDStoryName(userUID, story.getStoryName(),
					StoryStatus.ACTIVE);
		}
		return storiesCount;
	}

	@Override
	public long countStoriesOfStoryAuthor(StoryView story, StoryStatus... storyStatuses) {
		logger.entry(story.getId(), story.getTitle(), storyStatuses);

		long count = 0;
		if (story != null) {
			if (story.isStoryAuthorAppUser()) {
				count = storyService.countStoriesWithUserUID(story.getUser().getUid(), storyStatuses);
			} else if (story.isStoryAuthorOAuthUser()) {
				count = storyService.countStoriesWithUserOAuthUID(story.getUserOAuth2().getIdOAuth2(), storyStatuses);
			} else {
				logger.error("story author was null");
			}
		}

		return count;
	}
}
