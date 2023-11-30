/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Nov-23
 */
public class IAmJob extends AbstractScheduleJob {
	private static final XLogger logger = XLoggerFactory.getXLogger(IAmJob.class);

	public static final String JOB_NAME = IAmJob.class.getSimpleName();

	public IAmJob() {
		super();
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info(JOB_NAME + " Active");
	}
}
