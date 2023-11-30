/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.report.service;

import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.business.email.ejb.EMailerService;
import com.jklprojects.mystory.business.report.service.api.ReportDistributionService;
import com.jklprojects.mystory.business.story.service.api.StoryService;
import com.jklprojects.mystory.business.user.service.api.UserOAuth2Service;
import com.jklprojects.mystory.business.user.service.api.UserService;
import com.jklprojects.mystory.business.watch.service.api.StoryWatchService;
import com.jklprojects.mystory.common.TimePeriod;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.TimeUtil;
import com.jklprojects.mystory.report.api.AppJasperReport;
import com.jklprojects.mystory.report.api.IReportConsts;
import com.jklprojects.mystory.report.story.StoriesCreatedReportBean;
import com.jklprojects.mystory.report.story.StoriesViewsReportBean;
import com.jklprojects.mystory.report.user.UsersCreatedReportBean;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Prepare, and distribute report at a scheduled time.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2017-11-23
 */
@Singleton
@Startup
public class ReportDistributionServiceImpl implements ReportDistributionService, IReportConsts {
	private static final XLogger logger = XLoggerFactory.getXLogger(ReportDistributionServiceImpl.class);

	@Inject
	private EMailerService eMailService;

	@Inject
	private AppConfigService appConfigService;

	@Inject
	private UserService userService;

	@Inject
	private UserOAuth2Service userOAuth2Service;

	@Inject
	private StoryService storyService;

	@Inject
	private StoryWatchService storyWatchService;

	public ReportDistributionServiceImpl() {
		super();
	}

	public AppConfigService getAppConfigService() {
		return appConfigService;
	}

	public UserService getUserService() {
		return userService;
	}

	public UserOAuth2Service getUserOAuth2Service() {
		return userOAuth2Service;
	}

	public StoryService getStoryService() {
		return storyService;
	}

	public StoryWatchService getStoryWatchService() {
		return storyWatchService;
	}

	public EMailerService getEMailService() {
		return eMailService;
	}

	/**
	 * Prepare and distribute daily reports at a scheduled time.
	 *
	 * @throws AppException
	 */
	@Override
	@Schedule(second = "0", minute = "1", hour = "0", persistent = false)
	public void distributeDailyYesterdayReports() throws AppException {
		logger.info("START Executing distribution of Daily Yesterday reports");

		distributeUsersCreatedReport(TimePeriod.YESTERDAY);
		distributeStoriesCreatedReport(TimePeriod.YESTERDAY);
		distributeStoriesViewsReport(TimePeriod.YESTERDAY);

		logger.info("COMPLETED Executing distribution of Daily Yesterday reports");
	}

	/**
	 * Prepare and distribute weekly reports at a scheduled time.
	 *
	 * @throws AppException
	 */
	@Override
	@Schedule(second = "0", minute = "5", hour = "0", dayOfWeek = "Mon", persistent = false)
	public void distributeWeeklyEpochToLastWeekReports() throws AppException {
		logger.info("START Executing distribution of Epoch to last weekly reports");

		distributeUsersCreatedReport(TimePeriod.EPOCH_TO_LAST_WEEK);
		distributeStoriesCreatedReport(TimePeriod.EPOCH_TO_LAST_WEEK);
		distributeStoriesViewsReport(TimePeriod.EPOCH_TO_LAST_WEEK);

		logger.info("COMPLETED Executing distribution of Epoch to last weekly reports");
	}

	/**
	 * Prepare and distribute weekly reports at a scheduled time.
	 *
	 * @throws AppException
	 */
	@Override
	@Schedule(second = "0", minute = "3", hour = "0", dayOfWeek = "Mon", persistent = false)
	public void distributeWeeklyLastWeekReports() throws AppException {
		logger.info("START Executing distribution of Weekly Last Week reports");

		distributeUsersCreatedReport(TimePeriod.LAST_WEEK);
		distributeStoriesCreatedReport(TimePeriod.LAST_WEEK);
		distributeStoriesViewsReport(TimePeriod.LAST_WEEK);

		logger.info("COMPLETED Executing distribution of Weekly Last Week reports");
	}

	private void distributeStoriesCreatedReport(TimePeriod reportPeriod) throws AppException {
		try {
			ResourceBundle reportRB = I18N.REPORT.getRBUsingBaseName();
			String reportName = "";
			String reportPeriodStr = "";

			List<StoriesCreatedReportBean> storiesCreatedRBs = getStoryService()
					.findStoriesCreatedReportBeans(reportPeriod);
			Collections.sort(storiesCreatedRBs);

			// prepare report data
			if (reportPeriod.isYesterday()) {
				reportName = reportRB.getString("report_storiesCreated_yesterdayName");
				reportPeriodStr = TimeUtil.formatPeriodYesterday();
			} else if (reportPeriod.isLastWeek()) {
				reportName = reportRB.getString("report_storiesCreated_lastWeekName");
				reportPeriodStr = TimeUtil.formatPeriodLastWeek();
			} else if (reportPeriod.isEpochToLastWeek()) {
				reportName = reportRB.getString("report_storiesCreated_epochToLastWeekName");
				reportPeriodStr = TimeUtil.formatPeriodEpochToLastWeek();
			}

			// generate report
			AppJasperReport.STORIES_CREATED.bindJRBeanCollectionDataSource(STORIES_CREATED_DATA_SOURCE,
					storiesCreatedRBs);
			AppJasperReport.STORIES_CREATED.bindReportParam(STORIES_CREATED_TITLE, reportName);
			AppJasperReport.STORIES_CREATED.bindReportParam(STORIES_CREATED_NUM_STORIES, storiesCreatedRBs.size());
			AppJasperReport.STORIES_CREATED.bindReportParam(STORIES_CREATED_REPORT_PERIOD, reportPeriodStr);
			AppJasperReport.STORIES_CREATED
					.bindReportImageResourceParam(MYSTORY_LOGO, appConfigService.getReportMyStoryLogo())
					.bindReportImageResourceParam(MYSTORY_ICON, appConfigService.getReportMyStoryIcon());

			File reportFile = AppJasperReport.STORIES_CREATED.generateReportToFile();

			// send report
			if (CollUtil.isNotEmpty(storiesCreatedRBs)) {
				getEMailService().sendEmail(Arrays.asList(getAppConfigService().getEMailerBean().getMailAppAdminTo()),
						reportName, reportName, Arrays.asList(reportFile));
			}
			logger.info("Distribute Stories Created Report: {}, Stories Created: {}", reportPeriodStr,
					storiesCreatedRBs.size());

		} catch (AppCodeException e) {
			logger.error(e.getI18NMessage(), e);
		}
	}

	private void distributeStoriesViewsReport(TimePeriod reportPeriod) throws AppException {
		try {
			ResourceBundle reportRB = I18N.REPORT.getRBUsingBaseName();
			String reportName = "";
			String reportPeriodStr = "";

			// prepare report data
			if (reportPeriod.isYesterday()) {
				reportName = reportRB.getString("report_storiesViews_yesterdayName");
				reportPeriodStr = TimeUtil.formatPeriodYesterday();
			} else if (reportPeriod.isLastWeek()) {
				reportName = reportRB.getString("report_storiesViews_lastWeekName");
				reportPeriodStr = TimeUtil.formatPeriodLastWeek();
			} else if (reportPeriod.isEpochToLastWeek()) {
				reportName = reportRB.getString("report_storiesViews_epochToLastWeekName");
				reportPeriodStr = TimeUtil.formatPeriodEpochToLastWeek();
			}

			List<StoriesViewsReportBean> storiesViewsRBs = getStoryWatchService()
					.findStoriesViewsReportBeans(reportPeriod);
			Collections.sort(storiesViewsRBs);

			// generate report
			AppJasperReport.STORIES_VIEWS.bindJRBeanCollectionDataSource(STORIES_VIEWS_DATA_SOURCE, storiesViewsRBs);
			AppJasperReport.STORIES_VIEWS.bindReportParam(STORIES_VIEWS_TITLE, reportName);
			AppJasperReport.STORIES_VIEWS.bindReportParam(STORIES_VIEWS_NUM_VIEWS, storiesViewsRBs.size());
			AppJasperReport.STORIES_VIEWS.bindReportParam(STORIES_VIEWS_REPORT_PERIOD, reportPeriodStr);
			AppJasperReport.STORIES_VIEWS
					.bindReportImageResourceParam(MYSTORY_LOGO, appConfigService.getReportMyStoryLogo())
					.bindReportImageResourceParam(MYSTORY_ICON, appConfigService.getReportMyStoryIcon());

			File reportFile = AppJasperReport.STORIES_VIEWS.generateReportToFile();

			// send report
			if (CollUtil.isNotEmpty(storiesViewsRBs)) {
				getEMailService().sendEmail(Arrays.asList(getAppConfigService().getEMailerBean().getMailAppAdminTo()),
						reportName, reportName, Arrays.asList(reportFile));
			}
			logger.info("Distribute Story Views Report: {}, Story Views: {}", reportPeriodStr, storiesViewsRBs.size());

		} catch (AppCodeException e) {
			logger.error(e.getI18NMessage(), e);
		}
	}

	private void distributeUsersCreatedReport(TimePeriod reportPeriod) throws AppException {
		try {
			ResourceBundle reportRB = I18N.REPORT.getRBUsingBaseName();
			String reportName = "";
			String reportPeriodStr = "";

			// prepare report data
			if (reportPeriod.isYesterday()) {
				reportPeriodStr = TimeUtil.formatPeriodYesterday();
				reportName = reportRB.getString("report_usersCreated_yesterdayName");
			} else if (reportPeriod.isLastWeek()) {
				reportPeriodStr = TimeUtil.formatPeriodLastWeek();
				reportName = reportRB.getString("report_usersCreated_lastWeekName");
			} else if (reportPeriod.isEpochToLastWeek()) {
				reportPeriodStr = TimeUtil.formatPeriodEpochToLastWeek();
				reportName = reportRB.getString("report_usersCreated_epochToLastWeekName");
			}

			List<UsersCreatedReportBean> usersCreatedRBs = getUserService().findUsersCreatedReportBeans(reportPeriod);
			List<UsersCreatedReportBean> usersOAuth2CreatedRBs = getUserOAuth2Service()
					.findUsersOAuth2CreatedReportBeans(reportPeriod);
			usersCreatedRBs.addAll(usersOAuth2CreatedRBs);
			Collections.sort(usersCreatedRBs);

			// generate report
			AppJasperReport.USERS_CREATED.bindJRBeanCollectionDataSource(USERS_CREATED_DATA_SOURCE, usersCreatedRBs);
			AppJasperReport.USERS_CREATED.bindReportParam(USERS_CREATED_TITLE, reportName);
			AppJasperReport.USERS_CREATED.bindReportParam(USERS_CREATED_NUM_USERS, usersCreatedRBs.size());
			AppJasperReport.USERS_CREATED.bindReportParam(USERS_CREATED_REPORT_PERIOD, reportPeriodStr);
			AppJasperReport.USERS_CREATED
					.bindReportImageResourceParam(MYSTORY_LOGO, appConfigService.getReportMyStoryLogo())
					.bindReportImageResourceParam(MYSTORY_ICON, appConfigService.getReportMyStoryIcon());

			File reportFile = AppJasperReport.USERS_CREATED.generateReportToFile();

			// send report
			if (CollUtil.isNotEmpty(usersCreatedRBs)) {

				getEMailService().sendEmail(Arrays.asList(getAppConfigService().getEMailerBean().getMailAppAdminTo()),
						reportName, reportName, Arrays.asList(reportFile));
			}
			logger.info("Distribute Users Created Report: {}, Users Created: {}", reportPeriodStr,
					usersCreatedRBs.size());

		} catch (AppCodeException e) {
			logger.error(e.getI18NMessage(), e);
		}
	}
}
