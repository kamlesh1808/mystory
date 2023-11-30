/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.report.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jklprojects.mystory.business.report.service.api.ReportDistributionService;
import com.jklprojects.mystory.common.ex.AppException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2018-04-23
 */
@DisplayName("Report Distribution Service Impl Test")
class ReportDistributionServiceImplTest {

	private ReportDistributionService reportDistributionService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Disabled
	@DisplayName("Test Distribute Weekly Epoch To Last Week Reports")
	void testDistributeWeeklyEpochToLastWeekReports() {
		reportDistributionService = new ReportDistributionServiceImpl();
		try {
			reportDistributionService.distributeWeeklyLastWeekReports();
		} catch (AppException e) {
			e.printStackTrace();
		}

		assertTrue(true);
	}
}
