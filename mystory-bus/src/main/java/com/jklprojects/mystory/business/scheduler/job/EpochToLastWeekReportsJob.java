/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.scheduler.job;

import com.jklprojects.mystory.business.report.service.api.ReportDistributionService;
import com.jklprojects.mystory.common.ex.AppException;
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
public class EpochToLastWeekReportsJob extends AbstractScheduleJob {
	private static final XLogger logger = XLoggerFactory.getXLogger(EpochToLastWeekReportsJob.class);

	@EJB
	private ReportDistributionService reportDistributionService;

	public EpochToLastWeekReportsJob() {
		super();
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			reportDistributionService.distributeWeeklyEpochToLastWeekReports();
		} catch (AppException e) {
			logger.catching(e);
		}
	}
}
