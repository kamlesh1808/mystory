/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.api;

import com.jklprojects.mystory.common.ex.AppCodeException;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-JAN-23
 */
@DisplayName("App Jasper Report Test")
class AppJasperReportTest {

	@Test
	@DisplayName("Test Init")
	void testInit() {
		Assertions.assertTrue(!AppJasperReport.USERS_CREATED.getReportParams().isEmpty());
	}

	@Disabled
	@Test
	@DisplayName("Test Export To File List Of Qextends App Report Bean")
	void testExportToFileListOfQextendsAppReportBean() throws AppCodeException {
		File reportFile = AppJasperReport.USERS_CREATED.generateReportToFile();
		Assertions.assertTrue(reportFile != null);
	}
}
