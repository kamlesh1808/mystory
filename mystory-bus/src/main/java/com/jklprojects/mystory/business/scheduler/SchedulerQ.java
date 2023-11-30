/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.scheduler;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.business.scheduler.job.AppReloadPropsJob;
import com.jklprojects.mystory.business.scheduler.job.AppSendUserCommsJob;
import com.jklprojects.mystory.business.scheduler.job.EpochToLastWeekReportsJob;
import com.jklprojects.mystory.business.scheduler.job.IAmJob;
import com.jklprojects.mystory.business.scheduler.job.LastWeekReportsJob;
import com.jklprojects.mystory.business.scheduler.job.YesterdayReportsJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Nov-23
 */
public class SchedulerQ {
	private static final String APP_STATUS_GROUP = "appStatus";
	private static final String APP_REPORTS_GROUP = "appReports";
	private static final String APP_SERVICES_GROUP = "appServices";

	private static final XLogger logger = XLoggerFactory.getXLogger(SchedulerQ.class);

	private final Scheduler scheduler;
	private final AppConfigService appConfigService;

	/**
	 * @param appConfigService
	 * @throws SchedulerException
	 */
	public SchedulerQ(AppConfigService appConfigService) throws SchedulerException {
		this.appConfigService = appConfigService;
		scheduler = StdSchedulerFactory.getDefaultScheduler();
	}

	/**
	 * Schedule application Quartz jobs
	 *
	 * @throws SchedulerException
	 */
	public void scheduleAppQuartzJobs() throws SchedulerException {
		Trigger iAMTrigger = TriggerBuilder.newTrigger().withIdentity("iAMTrigger", APP_STATUS_GROUP).startNow()
				.withSchedule(simpleSchedule().withIntervalInMinutes(10).repeatForever()).build();

		scheduler.scheduleJob(
				JobBuilder.newJob(IAmJob.class).withIdentity(IAmJob.class.getSimpleName(), APP_STATUS_GROUP).build(),
				iAMTrigger);

		// app reports
		JobDetail epochToLastWeekReportsJob = JobBuilder.newJob(EpochToLastWeekReportsJob.class)
				.withIdentity(EpochToLastWeekReportsJob.class.getSimpleName(), APP_REPORTS_GROUP).build();

		Trigger epochToLastWeekReportsTrigger = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(appConfigService.getScheduleEpochToLastWeekReport()))
				.build();
		scheduler.scheduleJob(epochToLastWeekReportsJob, epochToLastWeekReportsTrigger);

		JobDetail lastWeekReportsJob = JobBuilder.newJob(LastWeekReportsJob.class)
				.withIdentity(LastWeekReportsJob.class.getSimpleName(), APP_REPORTS_GROUP).build();
		Trigger lastWeekReportsJobTrigger = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(appConfigService.getScheduleLastWeekReport())).build();
		scheduler.scheduleJob(lastWeekReportsJob, lastWeekReportsJobTrigger);

		JobDetail yesterdayReportsJob = JobBuilder.newJob(YesterdayReportsJob.class)
				.withIdentity(YesterdayReportsJob.class.getSimpleName(), APP_REPORTS_GROUP).build();
		Trigger yesterdayReportsJobTrigger = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(appConfigService.getScheduleYesterdayReport())).build();
		scheduler.scheduleJob(yesterdayReportsJob, yesterdayReportsJobTrigger);

		// app services
		String appReloadPropsCron = appConfigService.getScheduleAppReloadProps();
		logger.info("{} cron {}", AppReloadPropsJob.JOB_NAME, appReloadPropsCron);
		JobDetail appReloadPropsJob = JobBuilder.newJob(AppReloadPropsJob.class)
				.withIdentity(AppReloadPropsJob.JOB_NAME, APP_SERVICES_GROUP).build();
		Trigger appReloadPropsTrigger = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(appReloadPropsCron)).build();
		scheduler.scheduleJob(appReloadPropsJob, appReloadPropsTrigger);

		String appSendUserCommsCron = appConfigService.getScheduleAppReloadProps();
		logger.info("{} cron {}", AppSendUserCommsJob.JOB_NAME, appSendUserCommsCron);
		JobDetail appSendUserCommsJob = JobBuilder.newJob(AppSendUserCommsJob.class)
				.withIdentity(AppSendUserCommsJob.JOB_NAME, APP_SERVICES_GROUP).build();
		Trigger appSendUserCommsTrigger = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(appSendUserCommsCron)).build();
		scheduler.scheduleJob(appSendUserCommsJob, appSendUserCommsTrigger);

		scheduler.start();
	}
}
