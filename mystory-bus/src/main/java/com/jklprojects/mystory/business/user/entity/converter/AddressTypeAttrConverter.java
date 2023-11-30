/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.converter;

import com.jklprojects.mystory.common.user.AddressType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, July 4, 2015
 */
@Converter
public class AddressTypeAttrConverter implements AttributeConverter<AddressType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AddressType attribute) {
		return attribute.getId();
	}

	@Override
	public AddressType convertToEntityAttribute(Integer dbData) {
		return AddressType.toEnum(dbData);
	}
}
