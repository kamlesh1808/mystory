/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.scheduler.job;

import com.jklprojects.mystory.business.user.service.api.UserCommService;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Nov-23
 */
@Stateless
@LocalBean
public class AppSendUserCommsJob extends AbstractScheduleJob {

	public static final String JOB_NAME = AppSendUserCommsJob.class.getSimpleName();

	@EJB
	private UserCommService userCommService;

	public AppSendUserCommsJob() {
		super();
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		userCommService.sendUserComms();
	}
}
