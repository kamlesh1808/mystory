/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.report.service.api;

import com.jklprojects.mystory.common.ex.AppException;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2017-11-23
 */
public interface ReportDistributionService {

	void distributeDailyYesterdayReports() throws AppException;

	void distributeWeeklyEpochToLastWeekReports() throws AppException;

	void distributeWeeklyLastWeekReports() throws AppException;
}
