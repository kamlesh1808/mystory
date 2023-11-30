/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Time Utility.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-07-13
 * @version 2, 2017-04-23
 * @version 3, 2017-10-23
 * @version 4, 2017-11-23
 * @version 2, 2018-04-23
 */
public class TimeUtil {

	public static long agoDays(LocalDate localDate) {
		return Period.between(localDate, LocalDate.now()).getDays();
	}

	public static long agoDays(LocalDateTime localDateTime) {
		return Period.between(localDateTime.toLocalDate(), LocalDate.now()).getDays();
	}

	public static long agoHours(LocalDateTime localDateTime) {
		return Duration.between(localDateTime, LocalDateTime.now()).toHours();
	}

	public static long agoMinutes(LocalDateTime localDateTime) {
		return Duration.between(localDateTime, LocalDateTime.now()).toMinutes();
	}

	public static long agoMonths(LocalDate localDate) {
		return Period.between(localDate, LocalDate.now()).toTotalMonths();
	}

	public static long agoMonths(LocalDateTime localDateTime) {
		return Period.between(localDateTime.toLocalDate(), LocalDate.now()).toTotalMonths();
	}

	public static long agoYears(LocalDate localDate) {
		return Period.between(localDate, LocalDate.now()).getYears();
	}

	public static long agoYears(LocalDateTime localDateTime) {
		return Period.between(localDateTime.toLocalDate(), LocalDate.now()).getYears();
	}

	public static String format(Calendar cal, DateTimeFormatter dateTimeFormatter) {
		return cal != null ? dateTimeFormatter.format(TimeUtil.toOffsetDateTime(cal)) : "";
	}

	public static String format(OffsetDateTime offsetDateTime, DateTimeFormatter dateTimeFormatter) {
		return offsetDateTime != null ? dateTimeFormatter.format(offsetDateTime) : "";
	}

	/**
	 * Format the start and end period using the date time formatter and return it
	 * as a String.
	 *
	 * @param startPeriod
	 * @param endPeriod
	 * @param dateTimeFormatter
	 * @return period formatted using the date time formatter
	 */
	public static String formatPeriod(LocalDate startPeriod, LocalDate endPeriod, DateTimeFormatter dateTimeFormatter) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		return format(startPeriod.atStartOfDay(defaultZoneId).toOffsetDateTime(), dateTimeFormatter) + " - "
				+ format(endPeriod.atStartOfDay(defaultZoneId).toOffsetDateTime(), dateTimeFormatter);
	}

	/** @return period from epoch last week period using the date time formatter */
	public static String formatPeriodEpochToLastWeek() {
		Locale locale = Locale.getDefault();
		return formatPeriod(getStartOfEpoch(), getEndOfLastWeek(locale), DateTimeFormatter.ISO_LOCAL_DATE);
	}

	/**
	 * @param dateTimeFormatter
	 * @return period from epoch last week period using the date time formatter
	 */
	public static String formatPeriodEpochToLastWeek(DateTimeFormatter dateTimeFormatter) {
		Locale locale = Locale.getDefault();
		return formatPeriod(getStartOfEpoch(), getEndOfLastWeek(locale), dateTimeFormatter);
	}

	/** @return last week period using the date time formatter */
	public static String formatPeriodLastWeek() {
		Locale locale = Locale.getDefault();
		return formatPeriod(getStartOfLastWeek(locale), getEndOfLastWeek(locale), DateTimeFormatter.ISO_LOCAL_DATE);
	}

	/**
	 * @param dateTimeFormatter
	 * @return last week period using the date time formatter
	 */
	public static String formatPeriodLastWeek(DateTimeFormatter dateTimeFormatter) {
		Locale locale = Locale.getDefault();
		return formatPeriod(getStartOfLastWeek(locale), getEndOfLastWeek(locale), dateTimeFormatter);
	}

	/** @return yesterday period using the date time formatter */
	public static String formatPeriodYesterday() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		return formatPeriod(yesterday, yesterday, DateTimeFormatter.ISO_LOCAL_DATE);
	}

	/**
	 * @param dateTimeFormatter
	 * @return yesterday period using the date time formatter
	 */
	public static String formatPeriodYesterday(DateTimeFormatter dateTimeFormatter) {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		return formatPeriod(yesterday, yesterday, dateTimeFormatter);
	}

	/**
	 * @param locale
	 *            used to determine the first day of the week
	 * @return local date with end of current week
	 * @see WeekFields
	 */
	public static LocalDate getEndOfCurrentWeek(Locale locale) {
		return getStartOfCurrentWeek(locale).plusDays(7);
	}

	/**
	 * @param locale
	 *            used to determine the first day of the week
	 * @return local date with end of last week
	 * @see WeekFields
	 */
	public static LocalDate getEndOfLastWeek(Locale locale) {
		return getStartOfLastWeek(locale).plusDays(7);
	}

	/**
	 * @param locale
	 *            used to determine the first day of the week
	 * @return local date with start of current week
	 * @see WeekFields
	 */
	public static LocalDate getStartOfCurrentWeek(Locale locale) {
		return getStartOfNextWeek(locale).minusDays(7);
	}

	/** @return */
	public static LocalDate getStartOfEpoch() {
		return LocalDate.ofEpochDay(0);
	}

	/**
	 * @param locale
	 *            used to determine the first day of the week
	 * @return local date with start of last week
	 * @see WeekFields
	 */
	public static LocalDate getStartOfLastWeek(Locale locale) {
		return getStartOfNextWeek(locale).minusDays(14);
	}

	/**
	 * @param locale
	 *            used to determine the first day of the week
	 * @return local date with start of next week
	 * @see WeekFields
	 */
	public static LocalDate getStartOfNextWeek(Locale locale) {
		return LocalDate.now().with(WeekFields.of(locale).getFirstDayOfWeek());
	}

	/** @return unique file identifier based on the current local date time. */
	public static String getUniqueFileIdentifier() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-S-n"));
	}

	/**
	 * Get yesterday.
	 *
	 * @return
	 */
	public static LocalDate getYesterday() {
		return LocalDate.now().minusDays(1);
	}

	public static boolean isAfter(Calendar cal) {
		return LocalDateTime.now().isAfter(toLocalDateTime(cal));
	}

	public static boolean isBefore(Calendar createdTimeStamp, int expiryTime, TimeUnit timeUnit) {
		LocalDateTime createdLocalDateTime = TimeUtil.toLocalDateTime(createdTimeStamp);
		return isBefore(createdLocalDateTime, expiryTime, timeUnit);
	}

	public static boolean isBefore(LocalDateTime createdLocalDateTime, int expiryTime, TimeUnit timeUnit) {
		LocalDateTime createdLocalDateTimePlusExpiry = null;

		if (TimeUnit.HOURS.equals(timeUnit)) {
			createdLocalDateTimePlusExpiry = createdLocalDateTime.plusHours(expiryTime);
		} else if (TimeUnit.MINUTES.equals(timeUnit)) {
			createdLocalDateTimePlusExpiry = createdLocalDateTime.plusMinutes(expiryTime);
		} else if (TimeUnit.SECONDS.equals(timeUnit)) {
			createdLocalDateTimePlusExpiry = createdLocalDateTime.plusSeconds(expiryTime);
		}
		return LocalDateTime.now().isBefore(createdLocalDateTimePlusExpiry);
	}

	/**
	 * @param localDate
	 * @return
	 */
	public static Calendar toCalendar(LocalDate localDate) {
		Calendar cal = null;
		if (localDate != null) {
			cal = Calendar.getInstance();
			cal.setTime(Date.from(Instant.from(localDate)));
		}
		return cal;
	}

	/**
	 * @param localDateTime
	 * @return calendar of the local date time with the system's default time zone.
	 */
	public static Calendar toCalendar(LocalDateTime localDateTime) {
		return toCalendar(localDateTime, ZoneId.systemDefault());
	}

	/**
	 * @param localDateTime
	 * @param zoneId
	 * @return calendar of the local date time with the zone id.
	 */
	public static Calendar toCalendar(LocalDateTime localDateTime, ZoneId zoneId) {
		Calendar cal = null;
		if (localDateTime != null) {
			cal = Calendar.getInstance();
			cal.setTime(Date.from(Instant.from(localDateTime.atZone(zoneId))));
		}
		return cal;
	}

	/**
	 * @param localDate
	 * @return calendar end of the day of the local date with the system's default
	 *         time zone.
	 */
	public static Calendar toCalendarEndOfDay(LocalDate localDate) {
		return toCalendarEndOfDay(localDate, ZoneId.systemDefault());
	}

	/**
	 * @param localDate
	 * @param zoneId
	 * @return calendar end of the day of the local date with the time zone.
	 */
	public static Calendar toCalendarEndOfDay(LocalDate localDate, ZoneId zoneId) {
		Calendar cal = null;
		if (localDate != null) {
			cal = Calendar.getInstance();
			cal.setTime(Date.from(Instant.from(localDate.atStartOfDay(zoneId).plusDays(1).minusNanos(1000))));
		}
		return cal;
	}

	/**
	 * @param localDate
	 * @return calendar start of the day of the local date with system's default
	 *         time zone.
	 */
	public static Calendar toCalendarStartOfDay(LocalDate localDate) {
		return toCalendarStartOfDay(localDate, ZoneId.systemDefault());
	}

	/**
	 * @param localDate
	 * @param zoneId
	 * @return calendar start of the day of the local date with the time zone.
	 */
	public static Calendar toCalendarStartOfDay(LocalDate localDate, ZoneId zoneId) {
		Calendar cal = null;
		if (localDate != null) {
			cal = Calendar.getInstance();
			cal.setTime(Date.from(Instant.from(localDate.atStartOfDay(zoneId))));
		}
		return cal;
	}

	public static LocalDate toLocalDate(Calendar cal) {
		return cal != null ? LocalDate.ofYearDay(cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_YEAR)) : null;
	}

	public static LocalDateTime toLocalDateTime(Calendar cal) {
		return cal != null
				? LocalDateTime.ofInstant(Instant.ofEpochMilli(cal.getTimeInMillis()), cal.getTimeZone().toZoneId())
				: null;
	}

	public static LocalDateTime toLocalDateTime(Date date) {
		return date != null
				? LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneOffset.systemDefault())
				: null;
	}

	public static OffsetDateTime toOffsetDateTime(Calendar cal) {
		return cal != null
				? OffsetDateTime.ofInstant(Instant.ofEpochMilli(cal.getTimeInMillis()), cal.getTimeZone().toZoneId())
				: null;
	}

	public static OffsetDateTime toOffsetDateTime(LocalDate localDate) {
		return localDate.atStartOfDay().atOffset(OffsetDateTime.now().getOffset());
	}

	public static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
		return localDateTime.atOffset(OffsetDateTime.now().getOffset());
	}

	public static OffsetDateTime toOffsetDateTimeEndOfDay(LocalDate localDate) {
		return localDate.atStartOfDay().plusDays(1).minusNanos(1000).atOffset(OffsetDateTime.now().getOffset());
	}

	/**
	 * @param localDateTime
	 * @return
	 */
	public static OffsetDateTime toOffsetDateTimeEndOfDay(LocalDateTime localDateTime) {
		return localDateTime.toLocalDate().atStartOfDay().plusDays(1).minusNanos(1000)
				.atOffset(OffsetDateTime.now().getOffset());
	}

	/**
	 * @param localDate
	 * @return
	 */
	public static OffsetDateTime toOffsetDateTimeStartOfDay(LocalDate localDate) {
		return localDate.atStartOfDay().atOffset(OffsetDateTime.now().getOffset());
	}

	public static OffsetDateTime toOffsetDateTimeStartOfDay(LocalDateTime localDateTime) {
		return localDateTime.toLocalDate().atStartOfDay().atOffset(OffsetDateTime.now().getOffset());
	}
}
