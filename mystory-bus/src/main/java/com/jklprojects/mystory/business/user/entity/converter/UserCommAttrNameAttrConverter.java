/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.converter;

import com.jklprojects.mystory.common.user.UserCommAttrName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Oct 27, 2015
 */
@Converter
public class UserCommAttrNameAttrConverter implements AttributeConverter<UserCommAttrName, Integer> {

	@Override
	public Integer convertToDatabaseColumn(UserCommAttrName attribute) {
		return attribute.getId();
	}

	@Override
	public UserCommAttrName convertToEntityAttribute(Integer dbData) {
		return UserCommAttrName.toEnum(dbData);
	}
}
