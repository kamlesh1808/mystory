/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.converter;

import com.jklprojects.mystory.common.user.AddressStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, July 4, 2015
 */
@Converter
public class AddressStatusAttrConverter implements AttributeConverter<AddressStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AddressStatus attribute) {
		return attribute.getId();
	}

	@Override
	public AddressStatus convertToEntityAttribute(Integer dbData) {
		return AddressStatus.toEnum(dbData);
	}
}
