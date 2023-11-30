/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.converter;

import com.jklprojects.mystory.common.user.UserCommName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Oct. 24, 2015
 */
@Converter
public class UserCommNameAttrConverter implements AttributeConverter<UserCommName, Integer> {

	@Override
	public Integer convertToDatabaseColumn(UserCommName attribute) {
		return attribute.getId();
	}

	@Override
	public UserCommName convertToEntityAttribute(Integer dbData) {
		return UserCommName.toEnum(dbData);
	}
}
