/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity.converter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Allows automatic conversion between {@link java.time.OffsetDateTime} and
 * {@link java.time.Timestamp}
 *
 * @author Kamleshkumar N. Patel
 * @version 0.31.0-2017-Jan-23
 */
@Converter(autoApply = true)
public class OffsetDateTimeAttributeConverter implements AttributeConverter<OffsetDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(OffsetDateTime offsetDateTime) {
		return (offsetDateTime == null ? null : Timestamp.valueOf(offsetDateTime.toLocalDateTime()));
	}

	@Override
	public OffsetDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
		return (sqlTimestamp == null
				? null
				: OffsetDateTime.ofInstant(sqlTimestamp.toInstant(), ZoneId.systemDefault()));
	}
}
