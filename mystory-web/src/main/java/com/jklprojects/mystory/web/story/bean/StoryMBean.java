/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

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

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
@Named("storyMBean")
@RequestScoped
public class StoryMBean extends AbstractAppManagedBean {

	private static final XLogger logger = XLoggerFactory.getXLogger(StoryMBean.class);

	private List<StoryLinkDTO> storiesRelated = new ArrayList<>();
	private UserProfileDTO userProfileOfStory;

	@PostConstruct
	public void init() {
		logger.entry();

		try {
			String userUID = getRequestParamUserUID();

			String storyIdStr = getRequestParamStoryId();
			if (StringUtil.isNotEmpty(storyIdStr)) {
				long storyId = Long.parseLong(storyIdStr);

				String userRoleTypeIdStr = getRequestParamUserRoleTypeId();
				int userRoleTypeId = Integer.parseInt(userRoleTypeIdStr);
				UserRoleType userRoleType = UserRoleType.toEnum(userRoleTypeId);

				storiesRelated = getStoryService().findStoriesRelated(userUID, storyId, userRoleType);
				userProfileOfStory = getUserProfileService().findUserProfileOfStory(storyId);
			}

		} catch (NumberFormatException e) {
			logger.warn(e.getMessage(), e);
		} catch (AppCodeException e) {
			logger.warn(e.getI18NMessage());

			processAppCodeException(e);
		}
	}

	public List<StoryLinkDTO> getStoriesRelated() {
		return storiesRelated;
	}

	public boolean renderStoriesRelated() {
		return CollUtil.isNotEmpty(storiesRelated);
	}

	public UserProfileDTO getUserProfileOfStory() {
		return userProfileOfStory;
	}
}
