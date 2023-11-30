/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.entity.converter;

import com.jklprojects.mystory.common.blacklist.BlackListType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-06-23
 */
@Converter
public class BlackListTypeAttrConverter implements AttributeConverter<BlackListType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(BlackListType attribute) {
		return attribute.getId();
	}

	@Override
	public BlackListType convertToEntityAttribute(Integer dbData) {
		return BlackListType.toEnum(dbData);
	}
}
