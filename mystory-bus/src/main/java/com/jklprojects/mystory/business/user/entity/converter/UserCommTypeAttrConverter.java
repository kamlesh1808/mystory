/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.converter;

import com.jklprojects.mystory.common.user.UserCommType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Oct. 24, 2015
 */
@Converter
public class UserCommTypeAttrConverter implements AttributeConverter<UserCommType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(UserCommType attribute) {
		return attribute.getId();
	}

	@Override
	public UserCommType convertToEntityAttribute(Integer dbData) {
		return UserCommType.toEnum(dbData);
	}
}
