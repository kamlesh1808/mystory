/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.entity.converter;

import com.jklprojects.mystory.common.TableStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, July 5, 2015
 */
@Converter
public class TableStatusAttrConverter implements AttributeConverter<TableStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(TableStatus attribute) {
		return attribute.getId();
	}

	@Override
	public TableStatus convertToEntityAttribute(Integer dbData) {
		return TableStatus.toEnum(dbData);
	}
}
