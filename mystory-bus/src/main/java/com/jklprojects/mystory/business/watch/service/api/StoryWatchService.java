/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.service.api;

import com.jklprojects.mystory.business.watch.entity.StoryWatch;
import com.jklprojects.mystory.business.watch.entity.view.StoryWatchView;
import com.jklprojects.mystory.common.TimePeriod;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.report.story.StoriesViewsReportBean;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Local;

/**
 * StoryWatch services API for CRUD, etc.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2017-11-23
 */
@Local
public interface StoryWatchService {

	/**
	 * Create story watch if the signed in user has not authored the story and the
	 * story watch does not yet exist.
	 *
	 * @param storyId
	 * @param clientIpAddress
	 * @param watcherUserId
	 * @param watchUserRoleType
	 * @param storyAuthorId
	 */
	void createStoryWatch(long storyId, String clientIpAddress, long watcherUserId, UserRoleType watchUserRoleType,
			long storyAuthorId);

	List<StoryWatch> findStoriesWatchesBetween(LocalDate startPeriod, LocalDate endPeriod);

	List<StoriesViewsReportBean> findStoriesViewsReportBeans(TimePeriod reportPeriod);

	List<StoryWatchView> findStoryWatchWithStory(long storyId);

	/**
	 * Determine if a story watch exists for the story with user UID and client IP
	 * address, and view date equal to today.
	 *
	 * @param storyId
	 * @param userId
	 * @param clientIpAddress
	 * @return
	 */
	boolean isStoryWatchExistsWithUser(long storyId, long userId, String clientIpAddress);
}
