/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.entity.converter;

import com.jklprojects.mystory.common.RecordStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-06-23
 */
@Converter
public class RecordStatusConverter implements AttributeConverter<RecordStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(RecordStatus attribute) {
		return attribute.getId();
	}

	@Override
	public RecordStatus convertToEntityAttribute(Integer dbData) {
		return RecordStatus.toEnum(dbData);
	}
}
