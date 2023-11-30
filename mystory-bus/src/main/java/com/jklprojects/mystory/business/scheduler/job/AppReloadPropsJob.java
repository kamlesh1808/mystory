/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.scheduler.job;

import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Nov-23
 */
@Stateless
@LocalBean
public class AppReloadPropsJob extends AbstractScheduleJob {
	private static final XLogger logger = XLoggerFactory.getXLogger(AppReloadPropsJob.class);

	public static final String JOB_NAME = AppReloadPropsJob.class.getSimpleName();

	@EJB
	private AppConfigService appConfigService;

	public AppReloadPropsJob() {
		super();
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			appConfigService.reloadProperties();
		} catch (IOException e) {
			logger.catching(e);
		}
	}
}
