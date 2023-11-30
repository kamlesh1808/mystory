/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.converter;

import com.jklprojects.mystory.common.user.UserCommStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Oct 24, 2015
 */
@Converter
public class UserCommStatusAttrConverter implements AttributeConverter<UserCommStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(UserCommStatus attribute) {
		return attribute.getId();
	}

	@Override
	public UserCommStatus convertToEntityAttribute(Integer dbData) {
		return UserCommStatus.toEnum(dbData);
	}
}
