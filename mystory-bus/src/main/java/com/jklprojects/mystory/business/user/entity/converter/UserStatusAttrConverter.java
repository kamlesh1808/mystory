/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.converter;

import com.jklprojects.mystory.common.user.UserStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 7, 2015
 */
@Converter
public class UserStatusAttrConverter implements AttributeConverter<UserStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(UserStatus attribute) {
		return attribute.getId();
	}

	@Override
	public UserStatus convertToEntityAttribute(Integer dbData) {
		return UserStatus.toEnum(dbData);
	}
}
