/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.story.dto.StoryLinkDTO;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.dto.SignedInUserBean;
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
@Named("userMBean")
@RequestScoped
public class UserMBean extends AbstractAppManagedBean {

	private static final XLogger logger = XLoggerFactory.getXLogger(UserMBean.class);

	private List<StoryLinkDTO> storiesOfSignedInUser = new ArrayList<>();
	private List<StoryLinkDTO> storiesWithStatusPendingReview = new ArrayList<>();

	@PostConstruct
	public void init() {
		logger.entry();

		Profiler profiler = new Profiler("UserMBean");
		profiler.setLogger(logger);

		if (isUserSignedIn()) {
			profiler.start("isUserSignedIn");

			SignedInUserBean signedInUserBean = getSignedInUserBean();
			UserRoleType signedInUserRoleType = signedInUserBean.getUserRoleType();

			if (signedInUserRoleType.getUserType().isAppInternal()) {
				storiesOfSignedInUser = getStoryService().findStoryLinkDTOsWithUserUID(getSignedInUserUIDLong(),
						StoryStatus.getUserStoryStatuses());
			} else if (signedInUserRoleType.getUserType().isOAuth2External()) {
				storiesOfSignedInUser = getStoryService().findStoryLinkDTOsWithUserOAuthUID(getSignedInUserUID(),
						StoryStatus.getUserStoryStatuses());
			}

			if (signedInUserRoleType.isAppAdmin()) {
				storiesWithStatusPendingReview = getStoryService()
						.findStoryLinkDTOsWithStatus(StoryStatus.PENDING_REVIEW);
			}
		}

		profiler.stop().log();
	}

	public List<StoryLinkDTO> getStoriesOfSignedInUser() {
		return storiesOfSignedInUser;
	}

	public List<StoryLinkDTO> getStoriesWithStatusPendingReview() {
		return storiesWithStatusPendingReview;
	}
}
