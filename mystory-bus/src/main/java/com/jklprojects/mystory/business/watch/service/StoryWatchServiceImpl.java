/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.service;

import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.business.story.entity.Story;
import com.jklprojects.mystory.business.story.service.StoryServiceImpl;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.business.watch.entity.StoryWatch;
import com.jklprojects.mystory.business.watch.entity.view.StoryWatchView;
import com.jklprojects.mystory.business.watch.service.api.StoryWatchService;
import com.jklprojects.mystory.common.TimePeriod;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.util.TimeUtil;
import com.jklprojects.mystory.report.story.StoriesViewsReportBean;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.slf4j.profiler.Profiler;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2017-11-23
 */
@Stateless
public class StoryWatchServiceImpl extends AppEntityDAO<StoryWatch> implements StoryWatchService {

	private static final XLogger logger = XLoggerFactory.getXLogger(StoryServiceImpl.class);
	private static final Profiler profiler;

	static {
		profiler = new Profiler("StoryWatchServiceImpl");
		profiler.setLogger(logger);
	}

	public StoryWatchServiceImpl() {
		super(StoryWatch.class);
	}

	@Override
	public void createStoryWatch(long storyId, String watcherIPAddress, long watcherUserId,
			UserRoleType watchUserRoleType, long storyAuthorId) {

		logger.entry(storyId, watcherIPAddress, watcherUserId, watchUserRoleType, storyAuthorId);

		StoryWatch storyWatchE = null;

		if (watcherUserId > 0 && watcherUserId != storyAuthorId) {
			// if signed in user is not the story author

			if (!isStoryWatchExistsWithUser(storyId, watcherUserId, watcherIPAddress)) {
				storyWatchE = new StoryWatch(storyId, watcherUserId, watchUserRoleType, watcherIPAddress,
						LocalDate.now());
			}
		}

		if (storyWatchE != null) {
			createEntity(storyWatchE);

			int count = getEM().createNamedQuery("incrementStoryViews").setParameter("storyId", storyId)
					.executeUpdate();

			logger.info("Story Watch created: id: {}, story id: {} ", storyWatchE.getId(), storyId);
		}
	}

	@Override
	public List<StoryWatch> findStoriesWatchesBetween(LocalDate startPeriod, LocalDate endPeriod) {
		List<StoryWatch> storyWatches = getEM().createNamedQuery("findStoryWatchBetween", StoryWatch.class)
				.setParameter("startPeriod", TimeUtil.toOffsetDateTimeStartOfDay(startPeriod))
				.setParameter("endPeriod", TimeUtil.toOffsetDateTimeEndOfDay(endPeriod)).getResultList();
		return storyWatches;
	}

	@Override
	public List<StoriesViewsReportBean> findStoriesViewsReportBeans(TimePeriod reportPeriod) {
		logger.entry(reportPeriod);
		profiler.start("findStoriesViewsReportBeans");

		List<StoryWatch> storyWataches = Collections.emptyList();

		if (reportPeriod.isYesterday()) {
			LocalDate yesterday = TimeUtil.getYesterday();
			profiler.start("findStoriesWatchesBetween yesterday");
			storyWataches = findStoriesWatchesBetween(yesterday, yesterday);
		} else if (reportPeriod.isLastWeek()) {
			profiler.start("findStoriesWatchesBetween lastWeek");
			storyWataches = findStoriesWatchesBetween(TimeUtil.getStartOfLastWeek(Locale.getDefault()),
					TimeUtil.getEndOfLastWeek(Locale.getDefault()));
		} else if (reportPeriod.isEpochToLastWeek()) {
			Locale locale = Locale.getDefault();
			profiler.start("findStoriesWatchesBetween All");
			storyWataches = findStoriesWatchesBetween(TimeUtil.getStartOfEpoch(), TimeUtil.getEndOfLastWeek(locale));
		}

		profiler.start("toStoriesViewsReportBeans");
		List<StoriesViewsReportBean> storiesReportBeans = toStoriesViewsReportBeans(storyWataches);

		profiler.stop().log();

		logger.exit();

		return storiesReportBeans;
	}

	@Override
	public List<StoryWatchView> findStoryWatchWithStory(long storyId) {
		logger.entry(storyId);

		List<StoryWatch> entities = getEM().createNamedQuery("findStoryWatchWithStory", StoryWatch.class)
				.setParameter("storyId", storyId).getResultList();

		return StoryWatchView.toViews(entities.parallelStream().sorted().collect(Collectors.toList()));
	}

	@Override
	public boolean isStoryWatchExistsWithUser(long storyId, long userId, String clientIpAddress) {
		logger.entry(storyId, userId, clientIpAddress);
		boolean exists = false;

		long count = getEM().createNamedQuery("countStoryWatchExistsWithUser", Long.class)
				.setParameter("storyId", storyId).setParameter("userId", userId)
				.setParameter("clientIpAddress", clientIpAddress.trim()).getSingleResult();

		if (count > 0) {
			logger.info("A Story Watch exists storyId: {}, user_id or user_oauth2_id: {}, clientIpAddress: {}", storyId,
					userId, clientIpAddress);
			exists = true;
		}
		return exists;
	}

	/**
	 * Convenience method to transfer stories of {@link StoryWatchView} to
	 * {@link StoriesViewsReportBean}
	 *
	 * @param storiesWatches
	 * @return
	 */
	private List<StoriesViewsReportBean> toStoriesViewsReportBeans(List<StoryWatch> storiesWatches) {
		logger.entry();

		List<StoriesViewsReportBean> storiesViewsRBList = new ArrayList<>();

		for (StoryWatch storyWatch : storiesWatches) {

			String userUID = "";
			String userFirstName = "";
			String userLastName = "";

			// select the story watch user: app user or oauth2 user
			User user = storyWatch.getUser();
			UserOAuth2 userOAuth2 = storyWatch.getUserOAuth2();

			if (user != null && user.isIdSet()) {
				userUID = String.valueOf(user.getUid());
				userFirstName = user.getFirstName();
				userLastName = user.getLastName();
			} else if (userOAuth2 != null && userOAuth2.isIdSet()) {
				userUID = userOAuth2.getIdOAuth2();
				userFirstName = userOAuth2.getFirstName();
				userLastName = userOAuth2.getLastName();
			}

			StoriesViewsReportBean reportBean = new StoriesViewsReportBean(userUID, userFirstName, userLastName,
					storyWatch.getCreatedTimestampFormatted(), storyWatch.getUpdatedTimestampFormatted());

			Story story = storyWatch.getStory();
			reportBean.setStoryName(story.getStoryName().getI18N());
			reportBean.setStoryTitle(story.getTitle());
			reportBean.setStoryType(story.getStoryType().getI18N());
			reportBean.setStoryStatus(story.getStatus().getI18N());
			reportBean.setStoryAccessType(story.getAccessType().getI18N());
			reportBean.setClientIPAddress(storyWatch.getClientIpAddress());

			storiesViewsRBList.add(reportBean);
		}
		return storiesViewsRBList;
	}
}
