/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.common.blacklist.BlackListType;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.story.dto.StoryLinkDTO;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.dto.UserProfileDTO;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.StringUtil;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.slf4j.profiler.Profiler;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
@Named("userProfileMBean")
@RequestScoped
public class UserProfileMBean extends AbstractAppManagedBean {

	private static final XLogger logger = XLoggerFactory.getXLogger(UserProfileMBean.class);

	private UserProfileDTO userProfileOfUserUID;

	private List<StoryLinkDTO> storyLinkDTOsOfUser = new ArrayList<>();

	@PostConstruct
	public void init() {
		logger.entry();

		Profiler profiler = new Profiler("UserProfileMBean#init");
		profiler.setLogger(logger);

		String userUIDStr = getRequestParamUserUID();

		if (StringUtil.isNotEmpty(userUIDStr)) {
			try {
				UserRoleType userRoleType = UserRoleType.toEnum(getRequestParamUserRoleTypeId());
				userProfileOfUserUID = getUserProfileService().findUserProfileOfUserUID(userUIDStr, userRoleType);

				storyLinkDTOsOfUser = getStoryService().findStoryLinkDTOsOfUser(userUIDStr, userRoleType);

			} catch (AppCodeException e) {
				processAppCodeException(e);
			}
		}

		profiler.stop().log();
	}

	/** Create User or User OAuth2 watch. */
	public void doCreateUserWatch() {
		logger.entry();

		Profiler profiler = new Profiler("UserProfileMBean#init");
		profiler.setLogger(logger);

		String watcherIPAddress = getClientIPAddress();
		if (!getBlackListService().isBlackListed(watcherIPAddress, BlackListType.USER_WATCH_IP_ADDRESS)) {

			long watcherUserId = getWatcherUserId();
			long watchedUserId = getUserProfileOfUserUID().getUserId();

			if (watchedUserId != watcherUserId) {
				UserRoleType watchedUserRoleType = getWatchedRoleType();
				UserRoleType watcherUserRoleType = getWatcherRoleType();

				getUserWatchService().createUserWatch(watchedUserId, watchedUserRoleType, watcherUserId,
						watcherUserRoleType, watcherIPAddress);
			}
		}

		profiler.stop().log();
	}

	public UserProfileDTO getUserProfileOfUserUID() {
		return userProfileOfUserUID;
	}

	public boolean renderUserProfileOfUserUID() {
		return userProfileOfUserUID != null;
	}

	public List<StoryLinkDTO> getStoryLinkDTOsOfUser() {
		return storyLinkDTOsOfUser;
	}

	public boolean renderStoryLinkDTOsOfUser() {
		return CollUtil.isNotEmpty(storyLinkDTOsOfUser);
	}
}
