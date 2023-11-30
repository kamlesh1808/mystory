/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.api.beans;

import com.jklprojects.mystory.business.advert.api.AdvertService;
import com.jklprojects.mystory.business.audit.api.AuditService;
import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.business.common.service.api.BlackListService;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.business.story.service.api.StoryService;
import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.business.user.service.api.UserOAuth2Service;
import com.jklprojects.mystory.business.user.service.api.UserProfileService;
import com.jklprojects.mystory.business.user.service.api.UserService;
import com.jklprojects.mystory.business.watch.service.api.PageWatchService;
import com.jklprojects.mystory.business.watch.service.api.StoryWatchService;
import com.jklprojects.mystory.business.watch.service.api.UserWatchService;
import com.jklprojects.mystory.common.AppPage;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.UserType;
import com.jklprojects.mystory.common.user.dto.SignedInUserBean;
import com.jklprojects.mystory.common.util.NumUtil;
import com.jklprojects.mystory.common.util.StringUtil;
import com.jklprojects.mystory.web.SessionAttributes;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.inject.Inject;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents common/reusable Application Managed Bean functionalities.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2020-05-23
 */
public abstract class AbstractAppManagedBean extends AbstractManagedBean {

	private static final XLogger logger = XLoggerFactory.getXLogger(AbstractAppManagedBean.class);

	@Inject
	private UserOAuth2Service userOAuth2Service;

	@Inject
	private AdvertService advertService;

	@Inject
	private AppConfigService appConfigService;

	@Inject
	private BlackListService blackListService;

	@Inject
	private StoryWatchService storyWatchService;

	@Inject
	private UserService userService;

	@Inject
	private UserProfileService userProfileService;

	@Inject
	private StoryService storyService;

	@Inject
	private UserWatchService userWatchService;

	@Inject
	private PageWatchService pageWatchService;

	@Inject
	private AuditService auditService;

	public AbstractAppManagedBean() {
		super();
	}

	public AdvertService getAdvertService() {
		return advertService;
	}

	public AppConfigService getAppConfigService() {
		return appConfigService;
	}

	public BlackListService getBlackListService() {
		return blackListService;
	}

	public StoryWatchService getStoryWatchService() {
		return storyWatchService;
	}

	public UserWatchService getUserWatchService() {
		return userWatchService;
	}

	public AuditService getAuditService() {
		return auditService;
	}

	public StoryService getStoryService() {
		return storyService;
	}

	public UserOAuth2Service getUserOAuth2Service() {
		return userOAuth2Service;
	}

	public PageWatchService getPageWatchService() {
		return pageWatchService;
	}

	public UserService getUserService() {
		return userService;
	}

	public UserProfileService getUserProfileService() {
		return userProfileService;
	}

	public void doCreatePageWatch(AppPage appPage) {
		logger.entry(appPage);

		String watcherIPAddress = getClientIPAddress();
		long watcherUserId = getWatcherUserId();
		UserRoleType watcherUserRoleType = getWatcherRoleType();

		getPageWatchService().createPageWatch(appPage, watcherUserId, watcherUserRoleType, watcherIPAddress);
	}

	/** @return signed in user bean or null otherwise. */
	public SignedInUserBean getSignedInUserBean() {
		return (SignedInUserBean) getSessionAttribute(SessionAttributes.SIGNED_IN_USER_BEAN);
	}

	/**
	 * @return signed in first and last name, or user name, or empty String
	 *         otherwise.
	 */
	public String getSignedInUserFirstLastName() {
		String signedInUserDisplayName = "";
		SignedInUserBean signedInUserBean = getSignedInUserBean();

		if (signedInUserBean != null) {
			signedInUserDisplayName = StringUtil.isNotEmpty(signedInUserBean.getFirstLastName())
					? signedInUserBean.getFirstLastName()
					: signedInUserBean.getUserName();
		}
		return signedInUserDisplayName;
	}

	public String getSignedInUserFirstLastNamePadded() {
		return StringUtil.append(getSignedInUserFirstLastName(), 20, '*');
	}

	/** @return signed in userName or empty String otherwise. */
	public String getSignedInUserName() {
		SignedInUserBean signedInUserBean = getSignedInUserBean();
		return signedInUserBean != null ? signedInUserBean.getUserName() : "";
	}

	public UserRoleType getSignedInUserRoleType() {
		return isUserSignedIn() ? getSignedInUserBean().getUserRoleType() : null;
	}

	/** @return signed in user UID or null otherwise. */
	public String getSignedInUserUID() {
		SignedInUserBean signedInUserBean = getSignedInUserBean();
		return signedInUserBean != null ? signedInUserBean.getUserUID() : "";
	}

	/** @return signed in user UID or null otherwise. */
	public long getSignedInUserUIDLong() {
		SignedInUserBean signedInUserBean = getSignedInUserBean();
		return signedInUserBean != null ? NumUtil.parseLong(signedInUserBean.getUserUID()) : 0;
	}

	/**
	 * @return true or false if the signed in user is able to share a story based on
	 *         the user's role.
	 * @see {@link UserRoleType}
	 */
	public boolean isSignedInUserAbleToShareAStory() {
		UserRoleType userRoleType = getSignedInUserRoleType();
		return userRoleType != null && (userRoleType.isAppUser() || userRoleType.isGoogle());
	}

	/**
	 * @return true or false if the user is able to update the account details based
	 *         on the user's role.
	 * @see {@link UserRoleType}
	 */
	public boolean isSignedInUserAbleToUpdateAccount() {
		UserRoleType userRoleType = getSignedInUserRoleType();
		return userRoleType != null
				&& (userRoleType.getUserType().isAppInternal() || userRoleType.getUserType().isOAuth2External());
	}

	public boolean isSignedInUserRoleTypeAppAdmin() {
		UserRoleType userRoleType = getSignedInUserRoleType();
		return userRoleType != null && userRoleType.isAppAdmin();
	}

	public boolean isSignedInUserRoleTypeAppUser() {
		UserRoleType userRoleType = getSignedInUserRoleType();
		return userRoleType != null && userRoleType.isAppUser();
	}

	public boolean isSignedInUserRoleTypeGoogle() {
		UserRoleType userRoleType = getSignedInUserRoleType();
		return userRoleType != null && userRoleType.isGoogle();
	}

	/**
	 * Return true if the signed in user is the author of the given story user name,
	 * false otherwise.
	 *
	 * @param story
	 * @return
	 */
	public boolean isSignedInUserStoryAuthor(StoryView story) {
		boolean isSignedInUserStoryAuthor = false;
		UserView storyUser = story.getUser();
		UserOAuth2View storyUserOAuth2 = story.getUserOAuth2();

		if (storyUser != null && storyUser.isIdSet()) {
			String storyUserUID = String.valueOf(storyUser.getUid());
			isSignedInUserStoryAuthor = storyUserUID.equalsIgnoreCase(getSignedInUserUID());
		} else if (storyUserOAuth2 != null && storyUserOAuth2.isIdSet()) {
			isSignedInUserStoryAuthor = storyUserOAuth2.getIdOAuth2().equalsIgnoreCase(getSignedInUserUID());
		}

		return isSignedInUserStoryAuthor;
	}

	/**
	 * @return true if the signed in user type is {@link UserType#APP_INTERNAL},
	 *         false otherwise.
	 */
	public boolean isSignedInUserTypeAppInternal() {
		UserRoleType userRoleType = getSignedInUserRoleType();
		return userRoleType != null && userRoleType.getUserType().isAppInternal();
	}

	/**
	 * @return true if the signed in user type is {@link UserType#OAUTH2_EXTERNAL},
	 *         false otherwise.
	 */
	public boolean isSignedInUserTypeOAuth2External() {
		SignedInUserBean signedInUserBean = getSignedInUserBean();
		return signedInUserBean != null && signedInUserBean.getUserRoleType().getUserType().isOAuth2External();
	}

	public boolean isUserSignedIn() {
		SignedInUserBean signedInUserBean = getSignedInUserBean();
		return signedInUserBean != null && StringUtil.isNotEmpty(signedInUserBean.getUserUID());
	}

	public long getWatcherUserId() {
		return isUserSignedIn() ? getSignedInUserBean().getId() : userService.getSystemGuestUser().getId();
	}

	public String getWatcherUserUID() {
		return isUserSignedIn()
				? getSignedInUserBean().getUserUID()
				: userService.getSystemGuestUser().getUIDAsString();
	}

	public String getWatchedUserUID() {
		return getRequestParamUserUID();
	}

	public UserRoleType getWatcherRoleType() {
		return isUserSignedIn() ? getSignedInUserRoleType() : getUserService().getSystemGuestUser().getUserRoleType();
	}

	public UserRoleType getWatchedRoleType() {
		return UserRoleType.toEnum(getRequestParamUserRoleTypeId());
	}

	public String getStoryAuthorUID(StoryView story) {
		String storyAuthorUID = "";

		if (story != null) {
			if (story.getUser() != null && story.getUser().isIdSet()) {
				storyAuthorUID = String.valueOf(story.getUser().getUid());
			} else if (story.getUserOAuth2() != null && story.getUserOAuth2().isIdSet()) {
				storyAuthorUID = story.getUserOAuth2().getIdOAuth2();
			}
		}
		return storyAuthorUID;
	}

	public long getStoryAuthorId(StoryView story) {
		long storyAuthorId = 0;

		if (story != null) {
			if (story.getUser() != null && story.getUser().isIdSet()) {
				storyAuthorId = story.getUser().getId();
			} else if (story.getUserOAuth2() != null && story.getUserOAuth2().isIdSet()) {
				storyAuthorId = story.getUserOAuth2().getId();
			}
		}
		return storyAuthorId;
	}

	public String getStoryName(StoryView story) {
		return story.getStoryName().getI18N();
	}

	public String getStoryNameURI(StoryView story) {
		return getURI(story.getStoryName().getStoryName());
	}

	/**
	 * Get story title.
	 *
	 * @param storyView
	 * @return
	 */
	public String getStoryTitle(StoryView storyView) {
		return storyView.getTitle().trim();
	}

	/**
	 * Get string with space replaced with dash.
	 *
	 * @param str
	 * @return
	 */
	public String getURI(String str) {
		return getStringWithSpacesReplacedWithDash(str);
	}

	/**
	 * @param str
	 * @return string with all spaces replaced with dash -
	 */
	public String getStringWithSpacesReplacedWithDash(final String str) {
		return str.replaceAll(SPACE, DASH).toLowerCase();
	}

	public String getStoryName(int id) {
		return StoryName.toEnum(id).getI18N();
	}

	public String getRequestParam(String reqParam) {
		return getHttpServletRequest().getParameter(reqParam);
	}

	public String getRequestParamAuthorURL() {
		return getHttpServletRequest().getParameter(REQ_PARAM_AUTHOR_URL);
	}

	public String getRequestParamContactUsId() {
		return getHttpServletRequest().getParameter(REQ_PARAM_CONTACTUS_ID);
	}

	public String getRequestParamStoryId() {
		return getHttpServletRequest().getParameter(REQ_PARAM_STORY_ID);
	}

	public String getRequestParamStoryName() {
		return getHttpServletRequest().getParameter(REQ_PARAM_STORY_NAME);
	}

	public String getRequestParamStoryNameId() {
		return getHttpServletRequest().getParameter(REQ_PARAM_STORY_NAME_ID);
	}

	public String getRequestParamStoryTitle() {
		return getHttpServletRequest().getParameter(REQ_PARAM_STORY_TITLE);
	}

	public String getRequestParamTagId() {
		return getHttpServletRequest().getParameter(REQ_PARAM_TAG_ID);
	}

	public String getRequestParamTagName() {
		return getHttpServletRequest().getParameter(REQ_PARAM_TAG_NAME);
	}

	public String getRequestParamUserName() {
		return getHttpServletRequest().getParameter(REQ_PARAM_USER_NAME);
	}

	public String getRequestParamUserUID() {
		return getHttpServletRequest().getParameter(REQ_PARAM_USER_UID);
	}

	public String getRequestParamUserRoleTypeId() {
		return getHttpServletRequest().getParameter(REQ_PARAM_USER_ROLE_TYPE_ID);
	}

	public String getOffsetDateTimeFormattedISO_LOCAL_DATE(OffsetDateTime offsetDateTime) {
		return offsetDateTime != null ? DateTimeFormatter.ISO_LOCAL_DATE.format(offsetDateTime) : "";
	}

	public String getOffsetDateTimeFormattedISO_LOCAL_DATE_TIME(OffsetDateTime offsetDateTime) {
		return offsetDateTime != null ? DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(offsetDateTime) : "";
	}
}
