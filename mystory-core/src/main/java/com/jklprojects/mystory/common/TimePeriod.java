/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common;

import com.jklprojects.mystory.common.util.TimeUtil;

/**
 * A Time Period defines a predefined time period between two dates based on
 * current date and time. {@link TimeUtil} provides methods to obtain the actual
 * start and end of a time period.
 *
 * <pre>
* Examples:
* 		YESTERDAY LocalDate.now().minusDays(1)
 * </pre>
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Nov-23
 */
public enum TimePeriod {
	EPOCH_TO_LAST_WEEK, LAST_WEEK, YESTERDAY;

	public boolean isEpochToLastWeek() {
		return this == EPOCH_TO_LAST_WEEK;
	}

	public boolean isLastWeek() {
		return this == LAST_WEEK;
	}

	public boolean isYesterday() {
		return this == YESTERDAY;
	}
}
