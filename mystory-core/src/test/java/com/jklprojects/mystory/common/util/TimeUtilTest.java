/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Jul 13, 2015
 * @version 2, 2017-Nov-23
 */
@DisplayName("Time Util Test")
class TimeUtilTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test To Local Date Time")
	void testToLocalDateTime() {
		// System.out.println(TimeUtil.toLocalDateTime(Calendar.getInstance()));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1979);
		LocalDateTime localDateTime = TimeUtil.toLocalDateTime(cal);

		assertEquals(1979, localDateTime.getYear());

		Calendar cal2 = Calendar.getInstance();
		cal.set(1876, 7, 18, 10, 10, 10);
		LocalDateTime localDateTime2 = TimeUtil.toLocalDateTime(cal);
		assertEquals(1876, localDateTime2.getYear());
		assertEquals(8, localDateTime2.getMonthValue());
		assertEquals(18, localDateTime2.getDayOfMonth());

	}

	@Test
	@DisplayName("Test Is After")
	void testIsAfter() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 2);
		assertFalse(TimeUtil.isAfter(cal));
		cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -2);
		assertTrue(TimeUtil.isAfter(cal));
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowPlus48Hours = now.plusHours(48);
		// System.out.println(now.toString());
		assertFalse(now.isAfter(nowPlus48Hours));
		assertTrue(nowPlus48Hours.isAfter(now));
	}

	@Test
	@DisplayName("Test Is Before")
	void testIsBefore() {
		LocalDateTime now = LocalDateTime.now();
		assertTrue(TimeUtil.isBefore(now, 48, TimeUnit.HOURS));
		assertFalse(TimeUtil.isBefore(now.minusHours(48), 48, TimeUnit.HOURS));
		assertFalse(TimeUtil.isBefore(now.minusHours(49), 48, TimeUnit.HOURS));
		assertTrue(TimeUtil.isBefore(now.minusHours(47), 48, TimeUnit.HOURS));
		assertTrue(TimeUtil.isBefore(now, 48, TimeUnit.MINUTES));
		assertFalse(TimeUtil.isBefore(now.minusMinutes(48), 48, TimeUnit.MINUTES));
		assertFalse(TimeUtil.isBefore(now.minusMinutes(49), 48, TimeUnit.MINUTES));
		assertTrue(TimeUtil.isBefore(now.minusMinutes(47), 48, TimeUnit.MINUTES));
		assertTrue(TimeUtil.isBefore(now, 48, TimeUnit.SECONDS));
		assertFalse(TimeUtil.isBefore(now.minusSeconds(48), 48, TimeUnit.SECONDS));
		assertFalse(TimeUtil.isBefore(now.minusSeconds(49), 48, TimeUnit.SECONDS));
		assertTrue(TimeUtil.isBefore(now.minusSeconds(47), 48, TimeUnit.SECONDS));
	}

	@Test
	@DisplayName("Test To Offset Date Time Start Of Day _ Local Date Time")
	void testToOffsetDateTimeStartOfDay_LocalDateTime() {
		LocalDateTime localDateTime = LocalDateTime.parse("2017-11-09T20:44:42.778");
		assertTrue(TimeUtil.toOffsetDateTimeStartOfDay(localDateTime).toString().startsWith("2017-11-09T00:00"));
	}

	@Test
	@DisplayName("Test To Offset Date Time End Of Day _ Local Date Time")
	void testToOffsetDateTimeEndOfDay_LocalDateTime() {
		LocalDateTime localDateTime = LocalDateTime.parse("2017-11-09T20:44:42.778");
		assertTrue(TimeUtil.toOffsetDateTimeEndOfDay(localDateTime).toString().startsWith("2017-11-09T23:59:59"));
	}

	@Test
	@DisplayName("Test To Offset Date Time Start Of Day _ Local Date")
	void testToOffsetDateTimeStartOfDay_LocalDate() {
		LocalDate localDate = LocalDate.parse("2017-11-09");
		assertTrue(TimeUtil.toOffsetDateTimeStartOfDay(localDate).toString().startsWith("2017-11-09T00:00"));
	}

	@Test
	@DisplayName("Test To Offset Date Time End Of Day _ Local Date")
	void testToOffsetDateTimeEndOfDay_LocalDate() {
		LocalDate localDate = LocalDate.parse("2017-11-09");
		assertTrue(TimeUtil.toOffsetDateTimeEndOfDay(localDate).toString().startsWith("2017-11-09T23:59:59"));
	}
}
