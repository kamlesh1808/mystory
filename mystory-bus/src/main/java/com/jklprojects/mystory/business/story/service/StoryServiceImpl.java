/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.service;

import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.business.story.entity.Story;
import com.jklprojects.mystory.business.story.entity.StoryReply;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.business.story.service.api.StoryService;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserComm;
import com.jklprojects.mystory.business.user.entity.UserCommAttr;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.business.user.service.api.UserOAuth2Service;
import com.jklprojects.mystory.business.user.service.api.UserService;

import com.jklprojects.mystory.common.TimePeriod;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.story.StoryAccessType;
import com.jklprojects.mystory.common.story.StoryAuthor;
import com.jklprojects.mystory.common.story.StoryExceptions;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.StoryPostType;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.story.StoryTag;
import com.jklprojects.mystory.common.story.dto.StoryLinkDTO;
import com.jklprojects.mystory.common.user.UserCommAttrName;
import com.jklprojects.mystory.common.user.UserCommName;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.NumUtil;
import com.jklprojects.mystory.common.util.StringUtil;
import com.jklprojects.mystory.common.util.TimeUtil;

import com.jklprojects.mystory.report.story.StoriesCreatedReportBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.slf4j.profiler.Profiler;

/**
 * Story services API for CRUD, etc.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-05-23
 */
@Stateless
public class StoryServiceImpl extends AppEntityDAO<Story> implements StoryService {

	private static final XLogger logger = XLoggerFactory.getXLogger(StoryServiceImpl.class);

	@Inject
	private UserService userService;

	@Inject
	private UserOAuth2Service userOAuth2Service;

	public StoryServiceImpl() {
		super(Story.class);
	}

	@Override
	public StoryView createStoryReply(long userUID, long storyId, String reply, long repliedToId)
			throws AppCodeException {
		return createStoryReply(userUID, findStory(storyId), reply, repliedToId);
	}

	@Override
	public StoryView createStoryReply(long userUID, StoryView story, String reply, long repliedToId)
			throws AppCodeException {

		logger.entry(userUID, story, reply, repliedToId);

		StoryReply storyReply = new StoryReply();
		storyReply.setReply(reply);
		storyReply.setUser(getUserService().findUserWithUserUID(userUID));
		storyReply.setStory(find(story.getId(), Story.class));

		storyReply.setReplyNum(story.countReplies() + 1);

		if (repliedToId > 0) {
			storyReply.setParent(find(repliedToId, StoryReply.class));
		}

		createEntity(storyReply);

		return findStory(story.getId());
	}

	/**
	 * Create story for OAuth2 user.
	 *
	 * @param storyCreate
	 * @param oAuth2UID
	 * @throws AppCodeException
	 */
	@Override
	public StoryView createStoryWithUserOAuth2(StoryView storyCreate, String oAuth2UID, UserRoleType userRoleType)
			throws AppCodeException {

		logger.entry(oAuth2UID, userRoleType, storyCreate.getTitle());

		if (storyCreate.getStoryType().isIntro()) {
			if (isStoryExistsWithUserOAuth2UID(oAuth2UID, storyCreate.getStoryName(), storyCreate.getStoryType(),
					StoryStatus.getUserStoryExistsStatuses())) {
				throw StoryExceptions.STORY_001.newAppCodeException(storyCreate.getStoryName().getI18N(),
						storyCreate.getStoryType().getI18N());
			}
		}

		StoryView storyV = new StoryView();

		UserOAuth2 userOAuth2 = getUserOAuth2Service().findUserOAuthWithOAuthUID(oAuth2UID);
		if (userOAuth2 != null) {

			Story story = storyCreate.setEntityFromView(new Story(), true);
			story.setUserOAuth2(userOAuth2);

			// add story created user communication
			List<UserCommAttr> userCommAttrs = buildUserCommAttrOfAddStory(story, userOAuth2.getUserName(),
					userOAuth2.getEmail());

			UserComm userCommStoryCreated = new UserComm(userOAuth2.getId(), userOAuth2.getUserRoleType(),
					UserCommName.STORY_CREATED, userCommAttrs);
			storyV = saveStoryAndUserComm(story, userCommStoryCreated, userOAuth2.getUserName());
		}
		return storyV;
	}

	@Override
	public StoryView createStoryWithUserUID(StoryView storyCreate, long userUID) throws AppCodeException {
		logger.entry(userUID, storyCreate.getTitle());

		if (storyCreate.getStoryType().isIntro()) {
			if (isStoryExistsWithUserUID(userUID, storyCreate.getStoryName(), storyCreate.getStoryType(),
					StoryStatus.getUserStoryExistsStatuses())) {
				throw StoryExceptions.STORY_001.newAppCodeException(storyCreate.getStoryName().getI18N(),
						storyCreate.getStoryType().getI18N());
			}
		}

		StoryView storyV = new StoryView();

		User user = getUserService().findUserWithUserUID(userUID);
		if (user != null) {
			Story story = storyCreate.setEntityFromView(new Story(), true);
			story.setUser(user);

			// add story created user communication
			List<UserCommAttr> userCommAttrs = buildUserCommAttrOfAddStory(story, user.getUserName(), user.getEmail());

			UserComm userCommStoryCreated = new UserComm(user.getId(), user.getUserRoleType(),
					UserCommName.STORY_CREATED, userCommAttrs);

			storyV = saveStoryAndUserComm(story, userCommStoryCreated, user.getUserName());
		}
		return storyV;
	}

	@Override
	public long countStoriesWithUserUID(Long userUID, StoryStatus... storyStatuses) {
		logger.entry(userUID, storyStatuses);

		return getEM().createNamedQuery("countStoriesWithUserUID", Long.class).setParameter("userUID", userUID)
				.setParameter("status", Arrays.asList(storyStatuses)).getSingleResult();
	}

	@Override
	public long countStoriesWithUserOAuthUID(String userOAuthUID, StoryStatus... storyStatuses) {
		logger.entry(userOAuthUID, storyStatuses);

		return getEM().createNamedQuery("countStoriesWithUserOAuthUID", Long.class)
				.setParameter("userOAuthUID", userOAuthUID).setParameter("status", Arrays.asList(storyStatuses))
				.getSingleResult();
	}

	@Override
	public long countStoriesWithUserUIDStoryName(Long userUID, StoryName storyName, StoryStatus... storyStatuses) {
		logger.entry(userUID, storyStatuses);

		return getEM().createNamedQuery("countStoriesWithUserUIDStoryName", Long.class).setParameter("userUID", userUID)
				.setParameter("storyName", storyName.getId()).setParameter("status", Arrays.asList(storyStatuses))
				.getSingleResult();
	}

	@Override
	public long countStoriesWithUserOAuthUIDStoryName(String userOAuthUID, StoryName storyName,
			StoryStatus... storyStatuses) {
		logger.entry(userOAuthUID, storyName, storyStatuses);

		return getEM().createNamedQuery("countStoriesWithUserOAuthUIDStoryName", Long.class)
				.setParameter("userOAuthUID", userOAuthUID).setParameter("storyName", storyName)
				.setParameter("status", Arrays.asList(storyStatuses)).getSingleResult();
	}

	@Override
	public StoryView editStoryReply(long userUID, StoryView story, String reply, long replyId) throws AppCodeException {

		logger.entry(userUID, story, reply, replyId);

		StoryReply findStoryReply = find(replyId, StoryReply.class);
		if (findStoryReply != null) {
			findStoryReply.setReply(reply);
			findStoryReply.setUser(getUserService().findUserWithUserUID(userUID));
			findStoryReply.setStory(find(story.getId(), Story.class));

			updateEntity(findStoryReply);
		}

		return findStory(story.getId());
	}

	@Override
	public List<Story> findStoriesCreated(TimePeriod reportPeriod) {
		logger.entry(reportPeriod);

		List<Story> storiesCreated = Collections.emptyList();

		if (reportPeriod.isYesterday()) {
			LocalDate yesterday = TimeUtil.getYesterday();
			storiesCreated = findStoriesCreatedBetween(yesterday, yesterday);
		} else if (reportPeriod.isLastWeek()) {
			storiesCreated = findStoriesCreatedBetween(TimeUtil.getStartOfLastWeek(Locale.getDefault()),
					TimeUtil.getEndOfLastWeek(Locale.getDefault()));
		} else if (reportPeriod.isEpochToLastWeek()) {
			Locale locale = Locale.getDefault();
			storiesCreated = findStoriesCreatedBetween(TimeUtil.getStartOfEpoch(), TimeUtil.getEndOfLastWeek(locale));
		}
		return storiesCreated;
	}

	@Override
	public List<Story> findStoriesCreatedBetween(LocalDate startPeriod, LocalDate endPeriod) {
		logger.entry(startPeriod, endPeriod);

		List<Story> stories = getEM().createNamedQuery("findStoriesCreatedBetween", Story.class)
				.setParameter("startPeriod", TimeUtil.toOffsetDateTimeStartOfDay(startPeriod))
				.setParameter("endPeriod", TimeUtil.toOffsetDateTimeEndOfDay(endPeriod)).getResultList();
		return stories;
	}

	@Override
	public List<StoriesCreatedReportBean> findStoriesCreatedReportBeans(TimePeriod reportPeriod) {
		return toStoriesCreatedReportBeans(findStoriesCreated(reportPeriod));
	}

	@Override
	public List<StoryView> findStoriesUserWithUIDStoryNames(long userUID, StoryName... storyNames) {
		logger.entry(userUID, storyNames);

		List<Story> storiesE = getEM().createNamedQuery("findStoriesUserWithUIDStoryNames", Story.class)
				.setParameter("userUID", userUID).setParameter("status", StoryStatus.ACTIVE)
				.setParameter("storyNames", Arrays.asList(storyNames)).getResultList();

		return StoryView.toViews(storiesE);
	}

	@Override
	public List<StoryView> findStoriesUserWithTags(long userUID, String... tags) {
		logger.entry(userUID, tags);

		List<Story> storiesE = getEM().createNamedQuery("findStoriesUserWithTags", Story.class)
				.setParameter("userUID", userUID).setParameter("status", StoryStatus.ACTIVE)
				.setParameter("tags", Arrays.asList(tags)).getResultList();

		return StoryView.toViews(storiesE);
	}

	@Override
	public List<StoryView> findStoriesUserOAuthWithTags(String userOAuth2UID, String... tags) {
		logger.entry(userOAuth2UID, tags);

		List<Story> storiesE = getEM().createNamedQuery("findStoriesUserOAuthWithTags", Story.class)
				.setParameter("userOAuth2UID", userOAuth2UID).setParameter("status", StoryStatus.ACTIVE)
				.setParameter("tags", Arrays.asList(tags)).getResultList();

		return StoryView.toViews(storiesE);
	}

	@Override
	public List<Story> findStoriesUserWithUserNameOrEmailStoryNames(String userNameOrEmail, StoryName... storyNames) {
		logger.entry(userNameOrEmail, storyNames);

		List<Story> storiesE = getEM().createNamedQuery("findStoriesUserWithUserNameOrEmailStoryNames", Story.class)
				.setParameter("userID", userNameOrEmail).setParameter("email", userNameOrEmail)
				.setParameter("status", StoryStatus.ACTIVE).setParameter("storyNames", Arrays.asList(storyNames))
				.getResultList();

		return storiesE;
	}

	@Override
	public List<StoryView> findStoryViewsWithAccessType(StoryAccessType... storyAccessTypes) {
		return StoryView.toViews(findStoriesWithAccessType(storyAccessTypes));
	}

	@Override
	public List<Story> findStoriesWithAccessType(StoryAccessType... storyAccessTypes) {
		List<StoryAccessType> storyAccessTypesList = Arrays.asList(storyAccessTypes);
		logger.entry(storyAccessTypesList);
		Profiler profiler = new Profiler("findStoriesWithAccessType");
		profiler.setLogger(logger);

		List<Story> storiesE = getEM().createNamedQuery("findStoriesWithAccessType", Story.class)
				.setParameter("status", StoryStatus.ACTIVE).setParameter("accessType", storyAccessTypesList)
				.getResultList();

		profiler.stop().log();

		return storiesE;
	}

	@Override
	public List<StoryLinkDTO> findStoryLinksWithAccessType(StoryAccessType... storyAccessTypes) {
		List<Story> storiesE = findStoriesWithAccessType(storyAccessTypes);
		return toStoryLinkDTOs(storiesE);
	}

	@Override
	public List<Story> findStoriesWithStatus(StoryStatus... storyStatus) {
		List<StoryStatus> storyStatusList = Arrays.asList(storyStatus);
		logger.entry(storyStatusList);

		List<Story> storiesE = getEM().createNamedQuery("findStoriesWithStatus", Story.class)
				.setParameter("status", storyStatusList).getResultList();

		return storiesE;
	}

	@Override
	public List<StoryLinkDTO> findStoryLinkDTOsWithStatus(StoryStatus... storyStatus) {
		return toStoryLinkDTOs(findStoriesWithStatus(storyStatus));
	}

	@Override
	public List<Story> findStoriesWithStoryNameAndTags(StoryName storyName, String... tags) {
		logger.entry(storyName, tags);

		List<Story> storiesE = Collections.emptyList();
		if (storyName != null && tags != null) {
			storiesE = getEM().createNamedQuery("findStoriesByStoryNameAndTags", Story.class)
					.setParameter("status", StoryStatus.ACTIVE).setParameter("tags", Arrays.asList(tags))
					.setParameter("storyName", storyName).getResultList();
		}
		return storiesE;
	}

	@Override
	public List<StoryAuthor> findStoryAuthors() {
		logger.entry();
		List<StoryAuthor> storyAuthors = new ArrayList<>();

		List<StoryAuthor> storyAuthorsApp = getEM().createNamedQuery("findStoryAuthorsApp", StoryAuthor.class)
				.getResultList();

		List<StoryAuthor> storyAuthorsOAuth = getEM().createNamedQuery("findStoryAuthorsOAuth", StoryAuthor.class)
				.getResultList();

		storyAuthors.addAll(storyAuthorsApp);
		storyAuthors.addAll(storyAuthorsOAuth);

		storyAuthors.sort(Comparator.comparing(StoryAuthor::getStoriesCount).reversed());

		return Collections.checkedList(storyAuthors, StoryAuthor.class);
	}

	@Override
	public List<Story> findStoriesWithStoryNames(StoryName... storyNames) throws AppCodeException {
		List<StoryName> storyNameList = Arrays.asList(storyNames);
		logger.entry(storyNameList);

		List<Story> storiesE = getEM().createNamedQuery("findStoriesWithStoryNames", Story.class)
				.setParameter("status", StoryStatus.ACTIVE).setParameter("storyNames", storyNameList).getResultList();

		if (CollUtil.isEmpty(storiesE)) {
			throw StoryExceptions.STORY_005.newAppCodeException(
					storyNameList.stream().map(sn -> sn.getI18N()).collect(Collectors.joining(",")));
		}

		return storiesE;
	}

	@Override
	public List<StoryLinkDTO> findStoryLinkDTOsWithStoryNames(StoryName... storyNames) throws AppCodeException {
		return toStoryLinkDTOs(findStoriesWithStoryNames(storyNames));
	}

	@Override
	public List<StoryLinkDTO> findStoryLinkDTOsWithTagIds(Long... tagIds) throws AppCodeException {
		return toStoryLinkDTOs(findStoriesWithTagIds(tagIds));
	}

	@Override
	public List<Story> findStoriesWithTagIds(Long... tagIds) throws AppCodeException {
		List<Long> tagIdsList = Arrays.asList(tagIds);
		logger.entry(tagIdsList);
		List<Story> storiesE = Collections.emptyList();

		if (CollUtil.isNotEmpty(tagIdsList)) {
			storiesE = getEM().createNamedQuery("findStoriesWithTagIds", Story.class)
					.setParameter("status", StoryStatus.ACTIVE).setParameter("tagIds", tagIdsList).getResultList();
		}

		if (CollUtil.isEmpty(storiesE)) {
			throw StoryExceptions.STORY_004.newAppCodeException(
					tagIdsList.stream().map(tagId -> tagId.toString()).collect(Collectors.joining(",")));
		}

		return storiesE;
	}

	@Override
	public List<Story> findStoriesWithTags(String... tags) {
		List<String> tagList = Arrays.asList(tags);
		logger.entry(tagList);

		List<Story> storiesE = new ArrayList<>();

		if (tags != null) {
			storiesE = getEM().createNamedQuery("findStoriesWithTags", Story.class)
					.setParameter("status", StoryStatus.ACTIVE).setParameter("tags", tagList).getResultList();
		}
		return storiesE;
	}

	@Override
	public List<StoryLinkDTO> findStoryLinkDTOsWithTags(String... tags) {
		return toStoryLinkDTOs(findStoriesWithTags(tags));
	}

	@Override
	public List<Story> findStoriesWithUserOAuthUID(String userOAuth2UID, StoryStatus... storyStatuses) {
		logger.entry(userOAuth2UID, storyStatuses);

		List<Story> storiesE = getEM().createNamedQuery("findStoriesWithUserOAuthUID", Story.class)
				.setParameter("userOAuth2UID", userOAuth2UID).setParameter("status", Arrays.asList(storyStatuses))
				.getResultList();

		return storiesE;
	}

	@Override
	public List<StoryLinkDTO> findStoryLinkDTOsWithUserOAuthUID(String userOAuth2UID, StoryStatus... storyStatuses) {
		return toStoryLinkDTOs(findStoriesWithUserOAuthUID(userOAuth2UID, storyStatuses));
	}

	@Override
	public List<Story> findStoriesWithUserUID(long userUID, StoryStatus... storyStatuses) {
		logger.entry(userUID, storyStatuses);

		List<Story> storiesE = getEM().createNamedQuery("findStoriesWithUserUID", Story.class)
				.setParameter("userUID", userUID).setParameter("status", Arrays.asList(storyStatuses)).getResultList();

		return storiesE;
	}

	@Override
	public List<StoryLinkDTO> findStoryLinkDTOsWithUserUID(long userUID, StoryStatus... storyStatuses) {
		return toStoryLinkDTOs(findStoriesWithUserUID(userUID, storyStatuses));
	}

	@Override
	public StoryView findStory(long storyId) throws AppCodeException {
		logger.entry(storyId);

		Story storyFound = find(storyId);
		if (storyFound == null) {
			throw StoryExceptions.STORY_002.newAppCodeException(String.valueOf(storyId));
		}

		return new StoryView().setViewFromEntity(storyFound, true);
	}

	@Override
	public List<StoryTag> findStoryTags() {
		logger.entry();

		List<StoryTag> storyTags = getEM().createNamedQuery("findStoryTags", StoryTag.class).getResultList();

		return Collections.checkedList(storyTags, StoryTag.class);
	}

	@Override
	public List<Story> findStoryWithUserNameOrEmail(String userNameOrEmail, StoryName storyName,
			StoryPostType storyType, List<StoryStatus> storyStatus) {

		return getEM().createNamedQuery("findStoryWithUserID", Story.class).setParameter("userID", userNameOrEmail)
				.setParameter("email", userNameOrEmail).setParameter("storyName", storyName)
				.setParameter("storyType", storyType).setParameter("status", storyStatus).getResultList();
	}

	@Override
	public List<Story> findStoryWithUserUID(long userUID, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus) {

		return getEM().createNamedQuery("findStoryWithUserUID", Story.class).setParameter("userUID", userUID)
				.setParameter("storyName", storyName).setParameter("storyType", storyType)
				.setParameter("status", storyStatus).getResultList();
	}

	@Override
	public boolean isStoryExistsWithUserID(String userNameOrEmail, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus) {

		return CollUtil.isNotEmpty(findStoryWithUserNameOrEmail(userNameOrEmail, storyName, storyType, storyStatus));
	}

	@Override
	public boolean isStoryExistsWithUserOAuth2UID(String oAuth2UID, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus) {

		List<Story> entities = findStoryEqualsUserOAuth2(oAuth2UID, storyName, storyType, storyStatus);

		return CollUtil.isNotEmpty(entities);
	}

	@Override
	public boolean isStoryExistsWithUserUID(long userUID, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus) {

		return CollUtil.isNotEmpty(findStoryWithUserUID(userUID, storyName, storyType, storyStatus));
	}

	@Override
	public boolean isUserStoryAuthor(String signedInUserNameOrEmail, Story story) {
		if (StringUtil.isEmpty(signedInUserNameOrEmail) || story == null) {
			return false;
		}

		User storyAuthor = story.getUser();
		return signedInUserNameOrEmail.equalsIgnoreCase(storyAuthor.getEmail())
				|| signedInUserNameOrEmail.equalsIgnoreCase(storyAuthor.getUserName());
	}

	/**
	 * Update the reviewed story and create user communications.
	 *
	 * @param story
	 * @throws AppCodeException
	 */
	@Override
	public StoryView reviewStory(StoryView story) throws AppCodeException {
		logger.entry(story);

		StoryView storyV = new StoryView();

		Story storyFound = find(story.getId());

		StoryStatus statusNew = story.getStatus();
		if (storyFound != null && statusNew.in(StoryStatus.ACTIVE, StoryStatus.REJECTED)) {
			// add story reviewed user communication
			User user = storyFound.getUser();
			UserOAuth2 userOAuth2 = storyFound.getUserOAuth2();
			long userId = 0;
			String userName = "";
			String userEmail = "";
			UserRoleType userRoleType = UserRoleType.APP_USER;

			if (user != null && user.isIdSet()) {
				userName = user.getUserName();
				userEmail = user.getEmail();
				userId = user.getId();
				userRoleType = user.getUserRoleType();
			} else if (userOAuth2 != null && userOAuth2.isIdSet()) {
				userName = userOAuth2.getUserName();
				userEmail = userOAuth2.getEmail();
				userId = userOAuth2.getId();
				userRoleType = userOAuth2.getUserRoleType();
			}

			storyFound.setStatus(statusNew);
			Story storyUpdated = update(storyFound);

			UserComm userCommStoryReviewed = new UserComm(userId, userRoleType, UserCommName.STORY_REVIEWED,
					new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT,
							getReportSubject("report_storyReviewed_emailSubject")),
					new UserCommAttr(UserCommAttrName.EMAIL_TO, userEmail),
					new UserCommAttr(UserCommAttrName.USERNAME, userName),
					new UserCommAttr(UserCommAttrName.STORY_NAME, story.getStoryName().getI18N()),
					new UserCommAttr(UserCommAttrName.STORY_TITLE, story.getTitle()),
					new UserCommAttr(UserCommAttrName.STORY_TYPE, story.getStoryType().getI18N()),
					new UserCommAttr(UserCommAttrName.STORY_STATUS, story.getStatus().getI18N()),
					new UserCommAttr(UserCommAttrName.STORY_ACCESS_TYPE, story.getAccessType().getI18N()));

			createEntity(userCommStoryReviewed);

			storyV.setViewFromEntity(storyUpdated, true);
		}

		return storyV;
	}

	@Override
	public StoryView updateStory(StoryView story) throws AppCodeException {
		logger.entry(story);

		Story storyE = updateWithView(story);
		return new StoryView().setViewFromEntity(storyE, true);
	}

	/**
	 * @param storyId
	 * @param status
	 *            story's new status
	 */
	@Override
	public StoryView updateStoryStatus(long storyId, StoryStatus status) {
		logger.entry(storyId, status);

		StoryView storyUpdatedView = new StoryView();

		Story storyFind = find(storyId);
		if (storyFind != null) {
			storyFind.setStatus(status);
			Story storyUpdated = update(storyFind);

			storyUpdatedView.setViewFromEntity(storyUpdated, true);
		}

		return storyUpdatedView;
	}

	@Override
	public List<StoryLinkDTO> findStoriesRelated(String userUID, UserRoleType userRoleType) {
		logger.entry(userUID, userRoleType);

		List<Story> storiesRelated = Collections.emptyList();

		if (userRoleType.getUserType().isAppInternal()) {
			storiesRelated = findStoriesWithUserUID(NumUtil.parseLong(userUID), StoryStatus.ACTIVE);
		} else if (userRoleType.getUserType().isOAuth2External()) {
			storiesRelated = findStoriesWithUserOAuthUID(userUID, StoryStatus.ACTIVE);
		}

		return toStoryLinkDTOs(storiesRelated);
	}

	@Override
	public List<StoryLinkDTO> findStoriesRelated(String userUID, long storyId, UserRoleType userRoleType) {
		return findStoriesRelated(userUID, userRoleType).stream().filter(s -> s.getStoryId() != storyId)
				.collect(Collectors.toList());
	}

	@Override
	public List<Story> findStoriesOfUser(String userUID, UserRoleType userRoleType) {
		logger.entry(userUID, userRoleType);
		List<Story> stories = new ArrayList<>();

		if (userRoleType.getUserType().isAppInternal()) {
			stories = findStoriesWithUserUID(Long.parseLong(userUID), StoryStatus.ACTIVE);
		} else if (userRoleType.getUserType().isOAuth2External()) {
			stories = findStoriesWithUserOAuthUID(userUID, StoryStatus.ACTIVE);
		}
		return stories;
	}

	@Override
	public List<StoryLinkDTO> findStoryLinkDTOsOfUser(String userUID, UserRoleType userRoleType) {
		return toStoryLinkDTOs(findStoriesOfUser(userUID, userRoleType));
	}

	public StoryAuthor toStoryAuthor(Story story) {
		String userUID = "";
		String firstName = "";
		String lastName = "";
		UserRoleType userRoleType = UserRoleType.SYS_GUEST_USER;
		User user = story.getUser();
		UserOAuth2 userOAuth2 = story.getUserOAuth2();

		if (user != null && user.isIdSet()) {
			userUID = String.valueOf(user.getUid());
			firstName = user.getFirstName();
			lastName = user.getLastName();
			userRoleType = user.getUserRoleType();
		} else if (userOAuth2 != null && userOAuth2.isIdSet()) {
			userUID = userOAuth2.getIdOAuth2();
			firstName = userOAuth2.getFirstName();
			lastName = userOAuth2.getLastName();
			userRoleType = userOAuth2.getUserRoleType();
		}

		StoryAuthor storyAuthor = new StoryAuthor(userUID, story.getStatus().getId(), firstName, lastName,
				userRoleType.getId(), 0);

		return storyAuthor;
	}

	public List<StoryLinkDTO> toStoryLinkDTOs(List<Story> stories) {
		logger.entry();
		Profiler profiler = new Profiler("toStoryLinkDTOs");
		profiler.setLogger(logger);

		List<StoryLinkDTO> storyLinkDTOs = new ArrayList<>();

		for (Story story : stories) {
			StoryLinkDTO storyLinkDTO = new StoryLinkDTO(story.getId(), story.getStoryName(), story.getStoryType(),
					story.getAccessType(), story.getTitle(), story.getStatus(),
					story.getCreatedUpdated().getCreatedTimestamp(), story.getCreatedUpdated().getUpdatedTimestamp(),
					story.getStoryTextUpdatedTimestamp(), story.getViews(), toStoryAuthor(story), story.getStoryTags());

			storyLinkDTOs.add(storyLinkDTO);
		}

		profiler.stop().log();

		return storyLinkDTOs;
	}

	public UserOAuth2Service getUserOAuth2Service() {
		return userOAuth2Service;
	}

	public UserService getUserService() {
		return userService;
	}

	private void buildCreateReviewStoryUserCommsForEachAdminUser(Story story, String userName) {
		// add review story user communication for each admin user
		List<User> usersAdmin = getUserService().findUsersWithUserRoleTypes(UserRoleType.APP_ADMIN);
		for (User userAdmin : usersAdmin) {
			UserComm userCommStoryReview = new UserComm(userAdmin.getId(), userAdmin.getUserRoleType(),
					UserCommName.STORY_REVIEW,
					new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT,
							getReportSubject("report_storyReview_emailSubject")),
					new UserCommAttr(UserCommAttrName.EMAIL_TO,
							getAppConfigService().getEMailerBean().getEMailToWithAppEnvName(userAdmin.getEmail())),
					new UserCommAttr(UserCommAttrName.USERNAME, userName),
					new UserCommAttr(UserCommAttrName.STORY_NAME, story.getStoryName().getI18N()),
					new UserCommAttr(UserCommAttrName.STORY_TITLE, story.getTitle()),
					new UserCommAttr(UserCommAttrName.STORY_TYPE, story.getStoryType().getI18N()),
					new UserCommAttr(UserCommAttrName.STORY_STATUS, story.getStatus().getI18N()),
					new UserCommAttr(UserCommAttrName.STORY_ACCESS_TYPE, story.getAccessType().getI18N()));

			createEntity(userCommStoryReview);
		}
	}

	private List<UserCommAttr> buildUserCommAttrOfAddStory(Story story, String userName, String userEmail) {
		List<UserCommAttr> userCommAttrs = new ArrayList<>();
		userCommAttrs.add(
				new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT, getReportSubject("report_storyCreated_emailSubject")));
		userCommAttrs.add(new UserCommAttr(UserCommAttrName.EMAIL_TO,
				getAppConfigService().getEMailerBean().getEMailToWithAppEnvName(userEmail)));
		userCommAttrs.add(new UserCommAttr(UserCommAttrName.USERNAME, userName));
		userCommAttrs.add(new UserCommAttr(UserCommAttrName.STORY_NAME, story.getStoryName().getI18N()));
		userCommAttrs.add(new UserCommAttr(UserCommAttrName.STORY_TITLE, story.getTitle()));
		userCommAttrs.add(new UserCommAttr(UserCommAttrName.STORY_TYPE, story.getStoryType().getI18N()));
		userCommAttrs.add(new UserCommAttr(UserCommAttrName.STORY_ACCESS_TYPE, story.getAccessType().getI18N()));

		return userCommAttrs;
	}

	private List<Story> findStoryEqualsUserOAuth2(String oAuth2UID, StoryName storyName, StoryPostType storyType,
			List<StoryStatus> storyStatus) {

		return getEM().createNamedQuery("findStoryEqualsUserOAuth2", Story.class).setParameter("oAuth2UID", oAuth2UID)
				.setParameter("storyName", storyName).setParameter("storyType", storyType)
				.setParameter("status", storyStatus).getResultList();
	}

	private String getReportSubject(String key) {
		return new StringBuilder(getAppConfigService().getAppName()).append(" ").append(I18N.REPORT.getI18N(key))
				.toString();
	}

	private StoryView saveStoryAndUserComm(Story story, UserComm userCommStoryCreated, String userName) {
		Story storySaved = null;
		StoryView storyView = null;

		if (story.getStoryType().in(StoryPostType.pendingReviewList())) {
			story.setStatus(StoryStatus.PENDING_REVIEW);

			userCommStoryCreated.addAttr(new UserCommAttr(UserCommAttrName.STORY_PENDING_REVIEW_MESSAGE,
					getReportSubject("report_storyCreated_pendingReview")));
			userCommStoryCreated.addAttr(new UserCommAttr(UserCommAttrName.STORY_STATUS, story.getStatus().getI18N()));

			storySaved = create(story);
			createEntity(userCommStoryCreated);

			// add review story user communication for each admin user
			buildCreateReviewStoryUserCommsForEachAdminUser(story, userName);
		} else {
			story.setStatus(StoryStatus.ACTIVE);
			userCommStoryCreated.addAttr(new UserCommAttr(UserCommAttrName.STORY_STATUS, story.getStatus().getI18N()));

			// persist story and user comm
			storySaved = create(story);
			createEntity(userCommStoryCreated);
		}

		if (storySaved != null && storySaved.isIdSet()) {
			logger.info("Story created: {}", story.toString());
			storyView = new StoryView().setViewFromEntity(storySaved, true);
		}

		return storyView;
	}

	/**
	 * Convenience method to transfer stories of {@link Story} to
	 * {@link StoriesCreatedReportBean}
	 *
	 * @param stories
	 * @return
	 */
	private List<StoriesCreatedReportBean> toStoriesCreatedReportBeans(List<Story> stories) {
		List<StoriesCreatedReportBean> storiesCreatedReportBeanList = new ArrayList<>();

		for (Story story : stories) {
			String userName = "";
			String userFirstName = "";
			String userLastName = "";

			// select the story user: app user or oauth2 user
			User user = story.getUser();
			UserOAuth2 userOAuth2 = story.getUserOAuth2();

			if (user != null && user.isIdSet()) {
				userName = user.getUserName();
				userFirstName = user.getFirstName();
				userLastName = user.getLastName();
			} else if (userOAuth2 != null && userOAuth2.isIdSet()) {
				userName = userOAuth2.getUserName();
				userFirstName = userOAuth2.getFirstName();
				userLastName = userOAuth2.getLastName();
			}

			StoriesCreatedReportBean bean = new StoriesCreatedReportBean(userName, userFirstName, userLastName,
					story.getCreatedTimestampFormatted(), story.getUpdatedTimestampFormatted());

			bean.setStoryName(story.getStoryName().getI18N());
			bean.setStoryTitle(story.getTitle());
			bean.setStoryType(story.getStoryType().getI18N());
			bean.setStoryStatus(story.getStatus().getI18N());
			bean.setStoryAccessType(story.getAccessType().getI18N());
			bean.setStoryViews(story.getViews());

			storiesCreatedReportBeanList.add(bean);
		}
		return storiesCreatedReportBeanList;
	}
}
