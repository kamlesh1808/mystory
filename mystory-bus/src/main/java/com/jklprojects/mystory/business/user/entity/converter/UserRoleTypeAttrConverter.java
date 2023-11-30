/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.converter;

import com.jklprojects.mystory.common.user.UserRoleType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 12, 2015
 */
@Converter
public class UserRoleTypeAttrConverter implements AttributeConverter<UserRoleType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(UserRoleType attribute) {
		return attribute.getId();
	}

	@Override
	public UserRoleType convertToEntityAttribute(Integer dbData) {
		return UserRoleType.toEnum(dbData);
	}
}
